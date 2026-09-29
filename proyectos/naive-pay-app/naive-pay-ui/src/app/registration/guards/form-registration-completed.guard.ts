import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';  // Usamos ngx-cookie-service para manejar cookies

@Injectable({
    providedIn: 'root'
})
export class FormRegistrationCompletedGuard implements CanActivate {
    constructor(private router: Router, private cookieService: CookieService) {}

    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot): boolean {
        const isFormCompleted = this.cookieService.get('formCompleted');

        if (isFormCompleted === 'true') {
            return true; // Si la cookie está configurada, permitir el acceso
        } else {
            this.router.navigate(['/form-registration']); // Redirigir al formulario si no se completó
            return false;
        }
    }
}
