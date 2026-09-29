import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
    private apiUrl = 'http://localhost:8080/login'; // URL del endpoint para el login
    private userRole: string | null = null;

    constructor(private http: HttpClient, private router: Router) {
        const userRole = localStorage.getItem('user_role'); // Obtiene el rol del usuario
    }

    /**
     * Realiza una solicitud de inicio de sesión.
     * @param email El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     * @returns Un observable con la respuesta de la API.
     */
    login(email: string, password: string): Observable<any> {
        return this.http.post<any>(this.apiUrl, {email, password});
    }
    logout(): void {
        // Limpia cualquier token o datos almacenados
        sessionStorage.clear(); // Borra todo el almacenamiento de sesión
        localStorage.removeItem('access_token'); // Borra el token de acceso
        localStorage.removeItem('user_rut'); // Borra el RUT (o cualquier otro dato de usuario)
        localStorage.removeItem('user_role'); // Borra el rol del usuario

        // Redirige al usuario al inicio o página de login
        this.router.navigate(['/login']);
    }
    redirectHome(): void {
        this.userRole = localStorage.getItem('user_role'); // Obtiene el valor actualizado del rol
        if (this.userRole === 'ADMIN') {
            this.router.navigate(['/admin']);
        } else if (this.userRole === 'TITULAR') {
            this.router.navigate(['/dashboard']);
        } else {
            this.router.navigate(['/login']);
        }
    }
}
