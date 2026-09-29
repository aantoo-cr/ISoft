import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { AccountHolderStructure } from "../domain/AccountHolderStructure";

/**
 * Servicio para manejar la creación de una cuenta bancaria para un titular de cuenta.
 * Proporciona un metodo para crear una cuenta bancaria asociada al RUT del titular.
 */
@Injectable({
    providedIn: 'root'
})
export class RegistrationSuccessService {
    // URL para el endpoint de la API
    private URL = 'http://localhost:8080/bank-account/create';

    /**
     * Crea una instancia del servicio RegistrationSuccessService.
     * @param httpClient - La instancia de HttpClient utilizada para hacer solicitudes HTTP.
     */
    constructor(private httpClient: HttpClient) { }

    /**
     * Crea una cuenta bancaria para el titular de cuenta basado en su RUT.
     * Envía el RUT del titular de cuenta al servidor para proceder con la creación de la cuenta.
     *
     * @param rutAccountHolder - El RUT del titular de la cuenta para el cual se creará la cuenta bancaria.
     *
     * @returns Un Observable que emite la respuesta del servidor con un mensaje que indica si la creación fue exitosa o no.
     */
    createBankAccount(rutAccountHolder: string): Observable<{ message: string }> {
        console.log("Rut de accountHolder enviado", rutAccountHolder);
        const requestBody = { rutAccountHolder };

        return this.httpClient.post<{ message: string }>(this.URL, requestBody);
    }
}
