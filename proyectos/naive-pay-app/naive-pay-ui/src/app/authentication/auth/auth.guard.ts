import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import {jwtDecode} from "jwt-decode";
import {AuthService} from "../service/auth.service";

/**
 * Guard para proteger rutas que requieren autenticación.
 * Verifica la existencia de un token de acceso en el almacenamiento local.
 *
 * @author 00jesus
 * @version 1.0
 */
@Injectable({
    providedIn: 'root'
})
export class AuthGuard implements CanActivate {

    /**
     * Constructor del guard. Inyecta el servicio Router para manejar redirecciones.
     *
     * @param router Servicio Angular Router para redirigir al login si el usuario no está autenticado.
     * @param authService
     */
    constructor(private router: Router, private authService: AuthService) {}

    /**
     * Determina si se permite el acceso a una ruta específica.
     *
     * @param next Datos sobre la ruta que se intenta acceder.
     * @param state Estado actual del router.
     * @returns `true` si el usuario está autenticado y tiene el rol adecuado.
     * Redirige al login en caso contrario.
     */
    canActivate(
        next: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<boolean> | Promise<boolean> | boolean {

        // Verifica si el token está presente en localStorage
        const token = localStorage.getItem('access_token');
        if (token) {
            try {
                // Decodifica el token
                const decodedToken: any = jwtDecode(token);
                const userRole = decodedToken?.role; // Asumiendo que el rol está en la propiedad "role" del token
                const expiration = decodedToken?.exp; // Tiempo de expiración del token (timestamp)

                // Verifica si el token ha expirado
                const currentTime = Math.floor(Date.now() / 1000); // Tiempo actual en segundos
                if (expiration && expiration < currentTime) {
                    // El token ha expirado
                    alert('Tu sesión ha expirado. Por favor, inicia sesión nuevamente.');

                    this.authService.logout()
                    return false;
                }

                // Verifica si la ruta requiere un rol específico
                const allowedRoles = next.data['roles']; // Obtén los roles permitidos desde la configuración de la ruta

                // Si no hay roles permitidos en la ruta, permite el acceso
                if (!allowedRoles || allowedRoles.includes(userRole)) {
                    return true; // El usuario tiene acceso
                } else {
                    // Si el rol no está permitido, redirige a la página de acceso denegado
                    this.router.navigate(['/access-denied']);
                    return false;
                }
            } catch (error) {
                // Si ocurre un error al decodificar el token, redirige al login
                console.error('Error al decodificar el token:', error);
                this.router.navigate(['/login']);
                return false;
            }
        } else {
            // Si no hay token, redirige al login
            this.router.navigate(['/login']);
            return false;
        }
    }
}
