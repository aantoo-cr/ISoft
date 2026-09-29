import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router, RouterStateSnapshot } from '@angular/router';
import {jwtDecode} from 'jwt-decode';

@Injectable({
    providedIn: 'root'
})
export class UrlTokenGuard implements CanActivate {
    private extractedToken: string | null = null; // Almacenar el token extraído
    private extractedEmail: string | null = null; // Almacenar el email extraído
    private extractedFirstName: string | null = null; // Almacenar el nombre extraído

    constructor(private router: Router) {}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        const url = state.url;
        const token = new URLSearchParams(url.split('?')[1]).get('token');

        if (token) {
            try {
                const decodedToken: any = jwtDecode(token);
                const email = decodedToken.email;
                const firstName = decodedToken.firstName;


                // Validar expiración del token
                const currentTime = Math.floor(Date.now() / 1000);
                if (decodedToken.exp < currentTime) {
                    alert('El token ha expirado. Por favor, solicita uno nuevo.');
                    this.router.navigate(['/error'], { queryParams: { message: 'Token expirado' } });
                    return false;
                }

                this.extractedToken = token; // Almacenar el token válido
                this.extractedEmail = email; // Almacenar el email válido
                this.extractedFirstName = firstName; // Almacenar el nombre válido
                return true;
            } catch (error) {
                alert('El token proporcionado es inválido. Por favor, verifica la URL.');
                this.router.navigate(['/error'], { queryParams: { message: 'Token inválido' } });
                return false;
            }
        }

        alert('No se encontró el token en la URL. Asegúrate de que el enlace es correcto.');
        this.router.navigate(['/error'], { queryParams: { message: 'Token no encontrado' } });
        return false;
    }

    // Método para obtener el token extraído
    getToken(): string | null {
        return this.extractedToken;
    }
    getEmail(): string | null {
        return this.extractedEmail;
    }
    getFirstName(): string | null {
        return this.extractedFirstName;
    }
}
