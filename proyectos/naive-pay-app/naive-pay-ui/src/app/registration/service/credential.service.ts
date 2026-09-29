import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

/**
 * DTO que representa la credencial.
 */
interface CredentialDTO {
    publicKey: string;
    keyState: 'ACTIVE' | 'INACTIVE';
    rutAccountHolder: string;
}
interface CredentialDTO {
    publicKey: string;
    keyState: 'ACTIVE' | 'INACTIVE';  // Usamos los valores posibles de enum KeyState
    rutAccountHolder: string;
}

/**
 * Servicio para gestionar las credenciales.
 * Contiene métodos para crear nuevas credenciales.
 */
@Injectable({
    providedIn: 'root'
})
export class CredentialService {

    private apiUrl = 'http://localhost:8080/credential/create'; // URL del backend

    /**
     * Crea una nueva credencial en el sistema.
     *
     * @param {CredentialDTO} credentialDTO - El objeto que contiene la información de la nueva credencial.
     * @returns {Observable<any>} Un Observable que se resuelve con la respuesta del backend.
     * @example
     * credentialService.createCredential({ publicKey: '...', keyState: 'ACTIVE', rutAccountHolder: '12345678-9' })
     *   .subscribe(response => { ... });
     */
    constructor(private http: HttpClient) { }

    createCredential(credentialDTO: CredentialDTO): Observable<any> {
        return this.http.post<any>(this.apiUrl, credentialDTO);
    }
}

