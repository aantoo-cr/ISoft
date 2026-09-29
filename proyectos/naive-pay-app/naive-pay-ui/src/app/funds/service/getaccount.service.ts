import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable, of} from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';  // Importar HttpClient y HttpParams

/**
 * Servicio para gestionar la cuenta de un usuario, obteniendo su número de cuenta
 * desde el backend y gestionando el RUT del usuario en el almacenamiento local.
 */
@Injectable({
    providedIn: 'root'
})
export class GetaccountService {
    private apiUrl = 'http://localhost:8080';  // URL de tu servidor backend

    private userRutSubject = new BehaviorSubject<string | null>(this.getUserRut());

    /**
     * Constructor que inicializa el servicio y escucha los cambios en el almacenamiento local.
     * @param http El HttpClient para realizar solicitudes HTTP.
     */
    constructor(private http: HttpClient)  {
        // Escuchar los cambios en localStorage
        window.addEventListener('storage', this.onStorageChange.bind(this));
    }


    /**
     * Obtiene el RUT del usuario desde el almacenamiento local.
     * @returns El RUT del usuario almacenado o null si no existe.
     */
    getUserRut(): string | null {
        return localStorage.getItem('user_rut');
    }


    /**
     * Maneja los cambios en el almacenamiento local relacionados con el RUT del usuario.
     * Actualiza el BehaviorSubject cuando el RUT cambia en el almacenamiento local.
     * @param event El evento de almacenamiento.
     */
    private onStorageChange(event: StorageEvent): void {
        if (event.key === 'user_rut') {
            this.userRutSubject.next(event.newValue); // Emitir el nuevo valor cuando cambia
        }
    }

    /**
     * Método para obtener el número de cuenta del backend, usando el RUT del usuario.
     *
     * Este método utiliza el RUT obtenido a través de `obtenerRutUser` para hacer una solicitud GET
     * al servidor backend, recuperando el número de cuenta asociado al RUT del usuario.
     *
     * @returns Observable<string> - El número de cuenta correspondiente al RUT.
     */
    obtenerNumCuenta(): Observable<string> {
        const rut = this.getUserRut();
        if (!rut) {
            return of('');  // Si no se encuentra el RUT, retornamos un valor vacío
        }

        const params = new HttpParams().set('rut', rut);  // Establecemos el RUT como parámetro

        // Realizamos la solicitud GET al backend con el RUT
        console.log(this.http.get<string>(`${this.apiUrl}/api/funds/accountNumber/${rut}`));
        return this.http.get<string>(`${this.apiUrl}/api/funds/accountNumber/${rut}`, { responseType: 'text' as 'json' });
    }
}
