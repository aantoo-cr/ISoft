import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

/**
 * Servicio para obtener un par de claves desde el backend.
 * Proporciona un método para solicitar un par de claves a la API.
 */
@Injectable({
    providedIn: 'root'
})
export class KeyDisplayService {

    // URL para el endpoint de la API que genera las claves
    private apiUrl = 'http://localhost:8080/generateKeys';

    /**
     * Crea una instancia del servicio KeyDisplayService.
     * @param httpClient - La instancia de HttpClient utilizada para realizar las solicitudes HTTP.
     */
    constructor(private httpClient: HttpClient) {}

    /**
     * Obtiene un par de claves desde el servidor.
     * Hace una solicitud GET al servidor para obtener un par de claves generadas.
     *
     * @returns Un Observable que emite el par de claves recibido desde el servidor.
     */
    getKeyPair(): Observable<any> {
        return this.httpClient.get<any>(this.apiUrl);
    }
}
