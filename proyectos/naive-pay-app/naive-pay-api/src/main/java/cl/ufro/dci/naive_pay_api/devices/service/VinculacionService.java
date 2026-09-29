package cl.ufro.dci.naive_pay_api.devices.service;

import cl.ufro.dci.naive_pay_api.devices.domain.Devices;
import cl.ufro.dci.naive_pay_api.devices.repository.DeviceRepository;
import cl.ufro.dci.naive_pay_api.registration.domain.AccountHolder;
import cl.ufro.dci.naive_pay_api.registration.repository.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;

import java.util.*;

/**
 * Servicio que contiene la lógica para la vinculación de dispositivos.
 * Este servicio maneja las operaciones de agregar dispositivos, enviar códigos de verificación y verificar códigos.
 */
@Service
public class VinculacionService {

    /**
     * Repositorio para manejar las operaciones relacionadas con los titulares de cuentas.
     */
    @Autowired
    private AccountHolderRepository accountHolderRepository;

    /**
     * Repositorio para manejar las operaciones relacionadas con los dispositivos.
     */
    @Autowired
    private DeviceRepository deviceRepository;

    /**
     * Servicio para enviar correos electrónicos relacionados con la vinculación de dispositivos.
     */
    @Autowired
    private DeviceEmailService deviceEmailService;

    /**
     * Agrega un dispositivo al correo electrónico proporcionado.
     *
     * @param email El correo electrónico del usuario.
     * @param deviceName El nombre del dispositivo.
     * @param deviceModel El modelo del dispositivo.
     * @return true si el dispositivo fue agregado con éxito, false en caso contrario.
     */
    public boolean agregarDispositivo(String email, String deviceName, String deviceModel) {
        Optional<AccountHolder> accountHolderOpt = accountHolderRepository.findByEmail(email);

        if (accountHolderOpt.isPresent()) {
            AccountHolder accountHolder = accountHolderOpt.get();

            // Verificar si ya existe un dispositivo con el mismo nombre y modelo
            Optional<Devices> existingDeviceOpt = deviceRepository.findByAccountHolderAndNameAndModelo(accountHolder, deviceName, deviceModel);

            if (existingDeviceOpt.isPresent()) {
                Devices existingDevice = existingDeviceOpt.get();

                // Si el dispositivo ya existe y está bloqueado, cambiar su estado a "Pendiente de confirmación"
                if ("Bloqueado".equals(existingDevice.getStatus())) {
                    existingDevice.setStatus("Pendiente de confirmación");
                    deviceRepository.save(existingDevice);

                    // Bloquear todos los dispositivos vinculados actuales
                    List<Devices> existingDevices = deviceRepository.findByAccountHolder(accountHolder);
                    for (Devices device : existingDevices) {
                        if ("Vinculado".equals(device.getStatus())) {
                            device.setStatus("Bloqueado");
                            deviceRepository.save(device);

                            try {
                                deviceEmailService.enviarMensajeDesvinculacion(email);
                            } catch (MessagingException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    return true;
                }

                if ("Desvinculado".equals(existingDevice.getStatus())) {
                    existingDevice.setStatus("Pendiente de confirmación");
                    deviceRepository.save(existingDevice);
                    return true;
                } else {
                    // Si el dispositivo ya está vinculado o pendiente, no es necesario hacer nada
                    return false;
                }
            }

            // Bloquear todos los dispositivos vinculados actuales antes de agregar uno nuevo
            List<Devices> existingDevices = deviceRepository.findByAccountHolder(accountHolder);
            for (Devices device : existingDevices) {
                if ("Vinculado".equals(device.getStatus())) {
                    device.setStatus("Bloqueado");
                    deviceRepository.save(device);

                    try {
                        deviceEmailService.enviarMensajeDesvinculacion(email);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            }

            // Crear el nuevo dispositivo en estado "Pendiente de confirmación"
            Devices newDevice = new Devices(deviceName, "Pendiente de confirmación", deviceModel);
            newDevice.setAccountHolder(accountHolder);
            deviceRepository.save(newDevice);

            return true;
        } else {
            return false;
        }
    }

    /**
     * Envía un código de verificación al correo electrónico proporcionado.
     *
     * @param email El correo electrónico del usuario.
     * @return true si el código de verificación fue enviado con éxito, false en caso contrario.
     */
    public Map<String, Object> enviarCodigoVinculacion(String email) {
        Optional<AccountHolder> accountHolderOpt = accountHolderRepository.findByEmail(email);

        if (accountHolderOpt.isPresent()) {
            AccountHolder accountHolder = accountHolderOpt.get();
            List<Devices> devices = deviceRepository.findByAccountHolder(accountHolder);

            for (Devices device : devices) {
                if ("Pendiente de confirmación".equals(device.getStatus())) {
                    Integer codigo = generateVerificationCode();
                    try {
                        deviceEmailService.enviarCorreo(email, codigo);
                        device.setVerificationCode(codigo);
                        deviceRepository.save(device);

                        // Retornar detalles del dispositivo
                        Map<String, Object> response = new HashMap<>();
                        response.put("message", "Código de vinculación enviado a " + email);
                        response.put("deviceName", device.getName());
                        response.put("deviceModel", device.getModelo());

                        return response;

                    } catch (MessagingException e) {
                        e.printStackTrace();
                        Map<String, Object> errorResponse = new HashMap<>();
                        errorResponse.put("message", "Error al enviar el correo de verificación.");
                        return errorResponse;
                    }
                }
            }
        }

        // Retornar un mensaje de error si no se encuentra ningún dispositivo en estado "Pendiente de confirmación"
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", "No se encontró ningún dispositivo en estado 'Pendiente de confirmación' para el correo electrónico proporcionado.");
        return errorResponse;
    }


    /**
     * Verifica el código de verificación proporcionado para completar la vinculación del dispositivo.
     *
     * @param email El correo electrónico del usuario.
     * @param codigo El código de verificación.
     * @return true si el código de verificación es válido y el dispositivo fue vinculado con éxito, false en caso contrario.
     */
    public boolean verificarCodigo(String email, Integer codigo) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        // Buscar el titular de la cuenta por correo electrónico
        Optional<AccountHolder> accountHolderOpt = accountHolderRepository.findByEmail(email);

        if (accountHolderOpt.isPresent()) {
            AccountHolder accountHolder = accountHolderOpt.get();

            // Buscar el dispositivo en estado "Pendiente de confirmación" asociado al titular de la cuenta
            List<Devices> devices = deviceRepository.findByAccountHolder(accountHolder);
            Devices device = devices.stream()
                    .filter(d -> "Pendiente de confirmación".equals(d.getStatus()))
                    .findFirst()
                    .orElse(null);

            // Verificar si el dispositivo y el código coinciden
            if (device != null) {
                Integer storedCode = device.getVerificationCode();
                if (storedCode != null && storedCode.equals(codigo)) {
                    // Actualizar el estado del dispositivo a "Vinculado"
                    device.setStatus("Vinculado");
                    deviceRepository.save(device);

                    // Enviar mensaje de confirmación
                    try {
                        deviceEmailService.enviarMensajeConfirmacion(email);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }

                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Genera un código de verificación aleatorio.
     *
     * @return Un código de verificación aleatorio.
     */
    public Integer generateVerificationCode() {
        return 100000 + new Random().nextInt(900000);
    }
}
