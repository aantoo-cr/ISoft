import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

/**
 * Servicio para interactuar con el backend para guardar los datos de la clave web.
 * Proporciona un metodo para enviar un salt (sal), hash y rut al servidor.
 */
@Injectable({
    providedIn: 'root'
})
export class WebKeyService {
    // URL para el endpoint de la API
    private URL = 'http://localhost:8080/account-holder/setWebkey';

    /**
     * Crea una instancia del servicio WebKeyService.
     * @param httpClient - La instancia de HttpClient utilizada para hacer solicitudes HTTP.
     */
    constructor(private httpClient: HttpClient) { }

    /**
     * Guarda la clave web en el backend.
     * Envía el salt (sal), hash y rut como parámetros de consulta al servidor.
     *
     * @param salt - El valor de la sal utilizada para el hash.
     * @param hash - El valor hasheado que se debe guardar.
     * @param rut - El RUT (Rol Único Tributario) asociado con el titular de la cuenta.
     *
     * @returns Un Observable que emite la respuesta del servidor con un mensaje.
     */
    saveWebKey(salt: string, hash: string, rut: string): Observable<{ message: string }> {
        const params = new HttpParams()
            .set('salt', salt)
            .set('hash', hash)
            .set('rut', rut);

        return this.httpClient.post<{ message: string }>(this.URL, null, { params });
    }
}
