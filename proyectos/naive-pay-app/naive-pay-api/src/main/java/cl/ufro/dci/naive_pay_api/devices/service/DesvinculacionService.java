package cl.ufro.dci.naive_pay_api.devices.service;

import cl.ufro.dci.naive_pay_api.devices.domain.Devices;
import cl.ufro.dci.naive_pay_api.devices.repository.DeviceRepository;
import cl.ufro.dci.naive_pay_api.registration.domain.AccountHolder;
import cl.ufro.dci.naive_pay_api.registration.repository.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

/**
 * Servicio para la lógica de la desvinculación de dispositivos.
 */
@Service
public class DesvinculacionService {

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceEmailService deviceEmailService;

    /**
     * Envía un código de desvinculación al correo electrónico proporcionado.
     *
     * @param email El correo electrónico del usuario.
     * @return Un mapa con detalles del dispositivo vinculado o null si no se encontró.
     */
    public Map<String, Object> enviarCodigoDesvinculacion(String email) {
        Optional<AccountHolder> accountHolderOpt = accountHolderRepository.findByEmail(email);

        if (accountHolderOpt.isPresent()) {
            AccountHolder accountHolder = accountHolderOpt.get();
            List<Devices> devices = deviceRepository.findByAccountHolder(accountHolder);

            for (Devices device : devices) {
                if ("Vinculado".equals(device.getStatus())) {
                    Integer codigo = new Random().nextInt(1000000); // Generar un código aleatorio

                    try {
                        // Enviar correo con el código de desvinculación
                        deviceEmailService.enviarCorreoDesvinculacion(email, codigo);

                        // Asignar el código de desvinculación al dispositivo
                        device.setVerificationCode(codigo);
                        deviceRepository.save(device);

                        // Retornar detalles del dispositivo
                        Map<String, Object> response = new HashMap<>();
                        response.put("message", "Código de desvinculación enviado a " + email);
                        response.put("deviceName", device.getName());
                        response.put("deviceModel", device.getModelo());

                        return response;
                    } catch (MessagingException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Desvincula el dispositivo asociado al correo electrónico y código proporcionados.
     *
     * @param email El correo electrónico del usuario.
     * @param codigo El código de desvinculación.
     * @return true si el dispositivo fue desvinculado, false en caso contrario.
     */
    public boolean desvincularDispositivo(String email, Integer codigo) {
        Optional<AccountHolder> accountHolderOpt = accountHolderRepository.findByEmail(email);

        if (accountHolderOpt.isPresent()) {
            AccountHolder accountHolder = accountHolderOpt.get();
            List<Devices> devices = deviceRepository.findByAccountHolder(accountHolder);

            for (Devices device : devices) {
                Integer storedCode = device.getVerificationCode();

                if (storedCode != null && storedCode.equals(codigo)) {
                    // Actualizar el estado del dispositivo
                    device.setStatus("Desvinculado");
                    deviceRepository.save(device);

                    try {
                        // Enviar correo de confirmación
                        deviceEmailService.enviarMensajeDesvinculacion(email);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }

                    return true;
                }
            }
        }
        return false;
    }
}
