import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
    providedIn: 'root'
})
export class WebKeySetupGuard implements CanActivate {
    constructor(private router: Router, private cookieService: CookieService) {}

    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot): boolean {
        const isKeySetup = this.cookieService.get('webKeySetup');

        if (isKeySetup === 'true') {
            return true;
        } else {
            this.router.navigate(['/web-key/setup']); // Redirigir a la configuración de la clave
            return false;
        }
    }
}
