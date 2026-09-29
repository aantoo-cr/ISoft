import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
    providedIn: 'root'
})
export class IdentityVerifiedGuard implements CanActivate {
    constructor(private router: Router, private cookieService: CookieService) {}

    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot): boolean {
        const isIdentityVerified = this.cookieService.get('identityVerified');

        if (isIdentityVerified === 'true') {
            return true;
        } else {
            this.router.navigate(['/verify-identity']); // Redirigir a la verificación de identidad
            return false;
        }
    }
}
