import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

/**
 * Servicio para gestionar la verificación de identidad.
 * Proporciona métodos para solicitar un código de verificación y validar el código recibido.
 */
@Injectable({
    providedIn: 'root'
})
export class IdentityVerificationService {

    /**
     * Crea una instancia del servicio IdentityVerificationService.
     * @param httpClient - La instancia de HttpClient utilizada para realizar las solicitudes HTTP.
     */
    constructor(private httpClient: HttpClient) {}

    /**
     * Solicita un código de verificación para un correo electrónico.
     * Este metodo envía una solicitud al backend para generar un código de verificación y enviarlo al correo proporcionado.
     *
     * @param email - El correo electrónico al cual se enviará el código de verificación.
     * @returns Un Observable que emite la respuesta del servidor con un mensaje indicando el estado de la solicitud.
     *
     * @example
     * identityVerificationService.requestCodeVerification('usuario@example.com')
     *   .subscribe(response => {
     *     console.log(response.message);
     *   });
     */
    requestCodeVerification(email: string): Observable<{ message: string }> {
        const requestBody = { email };
        return this.httpClient.post<{ message: string }>('http://localhost:8080/verification/code-request', requestBody);
    }


    /**
     * Valida el código de verificación recibido.
     * Este metodo envía el código PIN para su validación en el backend.
     *
     * @param pin - El código de verificación (PIN) recibido para validación.
     * @returns Un Observable que emite la respuesta del servidor con un mensaje indicando el estado de la validación.
     *
     * @example
     * identityVerificationService.requestValidateCode('123456')
     *   .subscribe(response => {
     *     console.log(response.message);
     *   });
     */
    requestValidateCode(pin: string): Observable<{ message: string }> {
        const requestBody = { pin };
        return this.httpClient.post<{ message: string }>('http://localhost:8080/verification/validate-code', requestBody);
    }

}
