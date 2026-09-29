package cl.ufro.dci.naive_pay_api.registration.service;

import cl.ufro.dci.naive_pay_api.registration.domain.AccountHolder;
import cl.ufro.dci.naive_pay_api.registration.dto.AccountHolderDTO;
import cl.ufro.dci.naive_pay_api.registration.repository.AccountHolderRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;

/**
 * Servicio para la gestión de titulares de cuentas.
 * Proporciona métodos para crear, leer, actualizar y eliminar titulares de cuentas
 * así como para validar sus datos.
 * @author Gerson, Camille
 * @version 2.0
 */
@Service
public class AccountHolderService {

    private final AccountHolderRepository accountHolderRepository;

    /**
     * Constructor del servicio de titulares de cuenta.
     *
     * @param accountHolderRepository el repositorio de titulares de cuenta.
     */
    public AccountHolderService(AccountHolderRepository accountHolderRepository) {
        this.accountHolderRepository = accountHolderRepository;
    }
    public boolean existsAccountHolderByRut(String rut, String verificationDigit) {
        boolean existsByRut= accountHolderRepository.existsById(rut);
        boolean existsByVerificationDigit = accountHolderRepository.existsByVerificationDigit(verificationDigit);
        return existsByRut && existsByVerificationDigit ;
    }

    /**
     * Obtiene el titular de cuenta por su RUT.
     *
     * @param rut el RUT del titular de cuenta.
     * @return el titular de cuenta correspondiente al RUT especificado.
     * @throws ResponseStatusException si no se encuentra el titular de la cuenta.
     */
    public AccountHolder getAccountHolderByRut(String rut) {
        return accountHolderRepository.findById(rut)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Holder not found with RUT: " + rut));
    }

    /**
     * Obtiene todos los titulares de cuenta.
     *
     * @return una lista iterable de todos los titulares de cuenta.
     * @throws ResponseStatusException si ocurre un error al obtener los titulares.
     */
    public Iterable<AccountHolder> getAllAccountHolders() {
        try {
            return accountHolderRepository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving account holders", e);
        }
    }

    /**
     * Crea un nuevo titular de cuenta.
     *
     * @param accountHolderDTO el DTO con los datos del titular de cuenta a crear.
     * @throws ResponseStatusException si ocurre un error al crear el titular de cuenta.
     */
    public void createAccountHolder(AccountHolderDTO accountHolderDTO) {
        try {
            AccountHolder accountHolder = new AccountHolder(accountHolderDTO.getRut(),accountHolderDTO.getVerificationDigit(), accountHolderDTO.getFirstName(),
                    accountHolderDTO.getLastName(), accountHolderDTO.getEmail(), accountHolderDTO.getPhoneNumber(),
                    accountHolderDTO.getDocumentNumber(), accountHolderDTO.getAddress(), accountHolderDTO.getOccupation(),
                    accountHolderDTO.getDateOfBirth(), accountHolderDTO.getRole());

            accountHolderRepository.save(accountHolder);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account Holder with RUT already exists: " + accountHolderDTO.getRut(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating account holder", e);
        }
    }

    /**
     * Establece una clave web (hash) para un titular de cuenta.
     *
     * @param rut    el RUT del titular de cuenta.
     * @param salt
     * @param hash
     * @throws ResponseStatusException si ocurre un error al establecer la clave web.
     */
    public void setWebKey(String rut, String salt, String hash) {
        AccountHolder accountHolder = getAccountHolderByRut(rut);
        try {
            accountHolder.setSalt(salt);
            accountHolder.setWebKey(hash);
            accountHolderRepository.save(accountHolder);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error setting web key", e);
        }
    }

    /**
     * Actualiza un titular de cuenta por RUT.
     *
     * @param rut                 el RUT del titular de cuenta a actualizar.
     * @param updateAccountHolder el objeto con los datos actualizados
     *                            del titular de cuenta.
     * @throws ResponseStatusException si no se encuentra el titular de cuenta
     *                                 u ocurre un error al actualizarlo.
     */
    public void updateAccountHolderByRut(String rut, AccountHolder updateAccountHolder) {
        AccountHolder existingAccountHolder = getAccountHolderByRut(rut);

        existingAccountHolder.setFirstName(updateAccountHolder.getFirstName());
        existingAccountHolder.setLastName(updateAccountHolder.getLastName());
        existingAccountHolder.setEmail(updateAccountHolder.getEmail());
        existingAccountHolder.setPhoneNumber(updateAccountHolder.getPhoneNumber());
        existingAccountHolder.setDocumentNumber(updateAccountHolder.getDocumentNumber());

        try {
            accountHolderRepository.save(existingAccountHolder);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating account holder", e);
        }
    }

    /**
     * Elimina un titular de cuenta por RUT.
     *
     * @param rut el RUT del titular de cuenta a eliminar.
     * @throws ResponseStatusException si el titular de cuenta no se encuentra
     *                                 o si ocurre un error al eliminarlo.
     */
    public void deleteAccountHolderByRut(String rut) {
        if (!accountHolderRepository.existsById(rut)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account holder with RUT " + rut + " not found");
        }
        try {
            accountHolderRepository.deleteById(rut);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting account holder", e);
        }
    }

    /**
     * Valida que una cadena de texto no sea nula, no esté vacía,
     * tenga una longitud mayor o igual a 2, y no contenga valores numéricos.
     * Se utiliza para validar nombres, apellidos, ciudades y países.
     *
     * @param value la cadena de texto a validar.
     * @return true si la cadena de texto cumple con los requisitos
     * y se valida correctamente.
     */
    public boolean validateString(String value) {
        return value != null &&
                !value.trim().isEmpty() &&
                value.length() >= 2 &&
                !value.matches(".*\\d.*");
    }

    /**
     * Valida un correo electrónico.
     * El correo electrónico es válido si contiene solo una arroba, y mínimo
     * un punto después de la arroba. Además, no puede ser un valor nulo o vacío.
     *
     * @param email el correo electrónico a validar.
     * @return true si el correo electrónico es válido.
     */
    public boolean validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        int atCount = email.length() - email.replace("@", "").length();
        int dotCount = email.length() - email.replace(".", "").length();
        return atCount == 1 && dotCount >= 1;
    }

    /**
     * Valida el RUT considerando el contexto chileno.
     * Se considera válido cuando no es un valor nulo ni vacío, a la vez que cuenta
     * con un máximo de 8 dígitos, más el dígito verificador.
     *
     * @param rut el RUT a validar, que debe ingresarse sin puntos y con guión.
     * @return true si el RUT es válido.
     */
    public boolean validateRUT(String rut) {
        if (rut == null || rut.trim().isEmpty()) {
            return false;
        }

        if (!rut.matches("^\\d{1,8}-[0-9K]$")) {
            return false;
        }

        String[] parts = rut.split("-");
        String rutNumber = parts[0];
        String verificationDigit = parts[1].toUpperCase();

        String calculatedDigit = calculateVerificationDigit(rutNumber);

        return verificationDigit.equals(calculatedDigit);
    }

    /**
     * Calcula el dígito verificador chileno correcto según el RUT especificado.
     *
     * @param rutNumber la parte numérica del RUT del titular de cuenta.
     * @return el dígito verificador correspondiente a la parte numérica
     * del RUT especificado.
     */
    public String calculateVerificationDigit(String rutNumber) {
        int sum = 0;
        int multiplier = 2;

        for (int i = rutNumber.length() - 1; i >= 0; i--) {
            sum += (rutNumber.charAt(i) - '0') * multiplier;
            multiplier++;
            if (multiplier > 7) {
                multiplier = 2;
            }
        }

        int remainder = sum % 11;
        if (remainder == 0) {
            return "0";
        } else if (remainder == 1) {
            return "K";
        } else {
            return String.valueOf(11 - remainder);
        }
    }

    /**
     * Valida el número de teléfono.
     * El número de teléfono no puede ser un valor nulo ni vacío.
     * No debe contener letras, y debe tener una longitud de entre 7 y 9 dígitos.
     *
     * @param phoneNumber el número de teléfono a validar.
     * @return true si el número de teléfono es válido.
     */
    public boolean validatePhoneNumber(String phoneNumber) {
        return phoneNumber != null &&
                !phoneNumber.trim().isEmpty() &&
                phoneNumber.matches("^[0-9]{7,9}$");
    }

    /**
     * Descompone un RUT en su número y dígito verificador.
     *
     * @param rut el RUT a descomponer.
     * @return un arreglo de strings con el número y el dígito verificador del RUT.
     */
    private String[] descomposeRut(String rut) {
        return rut.split("-");
    }

    /**
     * Calcula la edad a partir de la fecha de nacimiento.
     *
     * @param dateOfBirth la fecha de nacimiento.
     * @return la edad calculada en años.
     */
    public int calculateAge(LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            return 0;
        }
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

}
