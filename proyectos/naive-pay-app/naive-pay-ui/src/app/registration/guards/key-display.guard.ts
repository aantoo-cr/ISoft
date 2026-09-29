import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
    providedIn: 'root'
})
export class KeyDisplayGuard implements CanActivate {
    constructor(private router: Router, private cookieService: CookieService) {}

    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot): boolean {
        const isKeyDisplayed = this.cookieService.get('keyDisplayed');

        if (isKeyDisplayed === 'true') {
            return true;
        } else {
            this.router.navigate(['/display-keys']); // Redirigir a la visualización de la clave
            return false;
        }
    }
}
