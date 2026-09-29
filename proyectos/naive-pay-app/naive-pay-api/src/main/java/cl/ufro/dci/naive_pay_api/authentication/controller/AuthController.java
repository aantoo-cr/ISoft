package cl.ufro.dci.naive_pay_api.authentication.controller;

import cl.ufro.dci.naive_pay_api.authentication.utils.UserAuthProviderUtils;
import cl.ufro.dci.naive_pay_api.authentication.dto.AccountHolderDto;
import cl.ufro.dci.naive_pay_api.authentication.dto.CredentialsDto;
import cl.ufro.dci.naive_pay_api.authentication.dto.UserDto;
import cl.ufro.dci.naive_pay_api.authentication.exceptions.AppException;
import cl.ufro.dci.naive_pay_api.authentication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para manejar las peticiones de autenticación de usuarios.
 * Este controlador permite a los usuarios iniciar sesión y recuperar los datos asociados a su RUT.
 *
 * @author Jesus
 * @version 1.3
 */
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {


    private final UserService userService;
    private final UserAuthProviderUtils userAuthProviderUtils;


    /**
     * Autentica un usuario mediante sus credenciales.
     *
     * <p>Este endpoint recibe un objeto JSON con el correo y contraseña del usuario,
     * valida las credenciales y, en caso de ser correctas, devuelve la información del usuario
     * junto con un token JWT para futuras solicitudes autenticadas.</p>
     *
     * @param credentials Objeto que contiene el correo electrónico y la contraseña del usuario.
     * @return {@link ResponseEntity} con un {@link UserDto} que incluye los datos del usuario y un token de autenticación.
     */
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentials) {
        UserDto user = userService.login(credentials);
        user.setToken(userAuthProviderUtils.createToken(user, 20));
        return ResponseEntity.ok(user);
    }

    /**
     * Recupera los detalles de un titular de cuenta mediante su RUT.
     *
     * <p>Este endpoint permite a los usuarios autenticados obtener información detallada
     * sobre un titular de cuenta utilizando su RUT como parámetro de búsqueda.</p>
     *
     * @param rut El RUT del titular de la cuenta (sin puntos, con guión).
     * @return {@link ResponseEntity} con un objeto {@link AccountHolderDto} que contiene los datos del titular de la cuenta.
     * @throws AppException Si no se encuentra un titular de cuenta con el RUT proporcionado.
     */
    @GetMapping("/api/mostrar/{rut}")
    public ResponseEntity<AccountHolderDto> getAccountByRut(@PathVariable String rut) {
        AccountHolderDto accountHolder = userService.getAccountByRut(rut);
        return ResponseEntity.ok(accountHolder);
    }

    /**
     * Lista todos los titulares de cuentas registrados.
     *
     * <p>Este endpoint devuelve una lista de todos los titulares de cuentas disponibles en el sistema.</p>
     *
     * @return {@link ResponseEntity} con una lista de {@link AccountHolderDto} que representa todos los titulares de cuentas.
     */
    @GetMapping("/api/mostrar")
    public ResponseEntity<List<AccountHolderDto>> getAllAccounts() {
        List<AccountHolderDto> accountHolders = userService.getAllAccounts();
        return ResponseEntity.ok(accountHolders);
    }


}
