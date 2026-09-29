import { Component } from '@angular/core';
import { Router } from '@angular/router';
import {AuthService} from "../../service/auth.service";

/**
 * Componente que gestiona el cierre de sesión del usuario.
 *
 * @author 00jesus
 * @version 1.0
 */
@Component({
  selector: 'app-logout',
  standalone: true,
  imports: [],
  templateUrl: './logout.component.html',
  styleUrl: './logout.component.css'
})
export class LogoutComponent {
    /**
     * Constructor del componente.
     *
     * @param authService
     */
    constructor(private authService: AuthService) {
    }

    /**
     * Realiza el cierre de sesión del usuario.
     * Borra todos los datos almacenados en localStorage y sessionStorage y redirige al login.
     */
    logout(): void {
        // Limpia cualquier token o datos almacenados
        this.authService.logout()
    }

    /**
     * Redirige al inicio.
     */
    redirectHome(): void {
        this.authService.redirectHome();
    }
}
