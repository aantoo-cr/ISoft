package cl.ufro.dci.naive_pay_api.authentication.service;

import cl.ufro.dci.naive_pay_api.authentication.dto.PasswordResetDto;
import cl.ufro.dci.naive_pay_api.authentication.exceptions.AppException;
import cl.ufro.dci.naive_pay_api.registration.domain.AccountHolder;
import cl.ufro.dci.naive_pay_api.registration.repository.AccountHolderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de gestionar el proceso de restablecimiento de contraseñas
 * y la actualización de la información del usuario en la base de datos.
 *
 * Funcionalidades principales:
 *   Validación de la existencia de un usuario mediante el correo electrónico.
 *   Generación de un hash seguro para la nueva contraseña.
 *   Actualización de la contraseña en la base de datos.
 *
 *
 * @author Jesus y Marcelo
 * @version 1.0
 */
@Service
public class ResetPasswordService {

    private final AccountHolderRepository accountHolderRepository;
    private final PasswordEncoder passwordEncoder;

    public ResetPasswordService(AccountHolderRepository accountHolderRepository, PasswordEncoder passwordEncoder) {
        this.accountHolderRepository = accountHolderRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Cambia la contraseña del usuario utilizando el email y la nueva contraseña.
     *
     * @param passwordResetDto Contiene el email y la nueva contraseña.
     * @throws AppException Si el usuario no existe o hay un error al guardar la nueva contraseña.
     */
    public void changePassword(PasswordResetDto passwordResetDto) {
        // Buscar el usuario por email
        AccountHolder accountHolder = accountHolderRepository.findByEmail(passwordResetDto.getEmail())
                .orElseThrow(() -> new AppException("Correo no registrado", HttpStatus.NOT_FOUND));

        // Generar el hash de la nueva contraseña
        String hashedPassword = passwordEncoder.encode(passwordResetDto.getNewPassword());

        // Actualizar la contraseña del usuario
        accountHolder.setWebKey(hashedPassword);
        accountHolderRepository.save(accountHolder);
    }
}