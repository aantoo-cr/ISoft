package cl.ufro.dci.naive_pay_api.authentication.service;


import cl.ufro.dci.naive_pay_api.authentication.dto.AccountHolderDto;
import cl.ufro.dci.naive_pay_api.authentication.dto.CredentialsDto;
import cl.ufro.dci.naive_pay_api.authentication.dto.RutRequest;
import cl.ufro.dci.naive_pay_api.authentication.dto.UserDto;
import cl.ufro.dci.naive_pay_api.authentication.exceptions.AppException;
import cl.ufro.dci.naive_pay_api.authentication.mapper.AccountMapper;
import cl.ufro.dci.naive_pay_api.authentication.mapper.UserMapper;
import cl.ufro.dci.naive_pay_api.registration.domain.AccountHolder;
import cl.ufro.dci.naive_pay_api.registration.repository.AccountHolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.List;

/**
 * Servicio que gestiona las operaciones relacionadas con la autenticación de usuarios.
 * Permite validar credenciales, iniciar sesión y recuperar información de titulares de cuentas.
 *
 * @author Jesus
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final AccountHolderRepository accountHolderRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AccountMapper accountMapper;

    /**
     * Valida las credenciales del usuario y realiza el inicio de sesión.
     *
     * @param credentialsDto Objeto que contiene el correo electrónico y la contraseña del usuario.
     * @return Un objeto UserDto con los datos del usuario autenticado.
     * @throws AppException Si el correo no está registrado o la contraseña es incorrecta.
     */
    public UserDto login (CredentialsDto credentialsDto) {
        AccountHolder user = accountHolderRepository.findByEmail(credentialsDto.email())
                .orElseThrow(() -> new AppException("Correo no registrado", HttpStatus.NOT_FOUND));
        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()),
                user.getWebKey())) {
            return userMapper.toUserDto(user);
        }
        throw new AppException("Contraseña incorrecta", HttpStatus.BAD_REQUEST);
    }

    /**
     * Recupera los detalles de un titular de cuenta usando su RUT.
     *
     * @param rut El RUT del titular de la cuenta.
     * @return AccountHolderDto Objeto que contiene los datos del titular de cuenta.
     * @throws AppException Si no se encuentra un titular de cuenta con el RUT proporcionado.
     */
    public AccountHolderDto getAccountByRut(String rut) {
        // Buscar el AccountHolder por RUT
        AccountHolder accountHolder = accountHolderRepository.findByRut(rut)
                .orElseThrow(() -> new AppException("RUT no encontrado", HttpStatus.NOT_FOUND));

        // Convertir la entidad a DTO usando MapStruct
        return accountMapper.toAccountDto(accountHolder);
    }

    /**
     * Recupera la lista de todos los titulares de cuenta registrados.
     *
     * @return Una lista de objetos AccountHolderDto con los datos de los titulares de cuenta.
     */
    public List<AccountHolderDto> getAllAccounts() {
        // Obtener todos los usuarios
        List<AccountHolder> accountHolders = accountHolderRepository.findAll();

        // Convertir la lista de entidades a DTOs usando MapStruct
        return accountHolders.stream()
                .map(accountMapper::toAccountDto)
                .toList();
    }

}
