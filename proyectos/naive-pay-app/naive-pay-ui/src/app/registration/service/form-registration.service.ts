import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs";
import { CookieService } from "ngx-cookie-service";
import { AccountHolderStructure } from "../domain/AccountHolderStructure";

/**
 * Servicio para gestionar el registro y la verificación de un titular de cuenta.
 * Proporciona métodos para enviar el formulario de registro y verificar si un RUT ya existe.
 */
@Injectable({
    providedIn: 'root'
})
export class FormRegistrationService {
    private URL = 'http://localhost:8080/';

    /**
     * Crea una instancia del servicio FormRegistrationService.
     * @param httpClient - La instancia de HttpClient utilizada para realizar las solicitudes HTTP.
     */
    constructor(
        private httpClient: HttpClient,
    ) { }

    /**
     * Envía los datos del titular de cuenta al backend para su registro.
     * Este método envía los datos del formulario de un titular de cuenta al servidor para su creación.
     *
     * @param accountHolder - El objeto que contiene la información del titular de cuenta a registrar.
     * @returns Un Observable que emite una respuesta del servidor con un mensaje y el ID de la sesión.
     *
     * @example
     * formRegistrationService.sendFormContent(accountHolder)
     *   .subscribe(response => {
     *     console.log(response.message, response.sessionId);
     *   });
     */
    sendFormContent(accountHolder: AccountHolderStructure): Observable<{ message: string, sessionId: string }> {
        console.log("Contenido de accountHolder enviado", accountHolder);
        return this.httpClient.post<{
            message: string,
            sessionId: string
        }>(this.URL + 'account-holder/create', accountHolder);
    }

    /**
     * Verifica si un RUT ya existe en el sistema.
     * Este método consulta al servidor para verificar si un RUT y su dígito verificador están registrados.
     *
     * @param rut - El RUT del titular de cuenta que se desea verificar.
     * @param verificationDigit - El dígito verificador asociado al RUT que se desea verificar.
     * @returns Un Observable que emite un valor booleano indicando si el RUT existe o no.
     *
     * @example
     * formRegistrationService.verifyRutExists('12345678', '9')
     *   .subscribe(exists => {
     *     console.log('¿El RUT existe?', exists);
     *   });
     */
    verifyRutExists(rut: string, verificationDigit: string): Observable<boolean> {
        console.log("Rut que se desea verificar: ", rut);
        const params = new HttpParams().set('rut', rut).set('verificationDigit', verificationDigit);
        return this.httpClient.get<boolean>(
            this.URL + 'account-holder/exists',
            { params } // 'rut' y 'verificationDigit' son parámetros de consulta
        );
    }
}
