import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import {UrlTokenGuard} from "../../auth/url-token.guard";
import {Router} from "@angular/router";

/**
 * @author Marcelo
 */
@Component({
    selector: 'app-reset-password',
    standalone: true,
    imports: [FormsModule, CommonModule],
    templateUrl: './reset-password.component.html',
    styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent {
    email: string = '';
    newPassword: string = '';
    confirmPassword: string = '';
    message: string = '';
    errorMessage: string = '';
    firstName: string | null = '';  // Aquí almacenamos el primer nombre del usuario

    constructor(
        private http: HttpClient,
        private urlTokenGuard: UrlTokenGuard,
        private router: Router // Inyecta el servicio Router
    ) {}

    ngOnInit(): void {
        // Obtener el primer nombre al iniciar el componente
        this.firstName = this.urlTokenGuard.getFirstName();
    }

    resetPassword() {
        if (this.newPassword !== this.confirmPassword) {
            this.errorMessage = 'Las contraseñas no coinciden.';
            return;
        }

        const validationError = this.validatePasswords(this.newPassword);
        if (validationError) {
            this.errorMessage = validationError;
            return;
        }

        // Obtener el token y email desde el guard
        const token = this.urlTokenGuard.getToken();
        const email = this.urlTokenGuard.getEmail();
        if (!token) {
            this.errorMessage = 'No se encontró un token válido para completar la operación.';
            return;
        }

        const data = {
            email,
            newPassword: this.newPassword
        };

        // Solicitud HTTP al backend
        this.http.post('http://localhost:8080/api/auth/password-reset', data, { responseType: 'text' })
            .subscribe(
                (response) => {
                    this.message = response; // Respuesta de texto
                    this.errorMessage = '';

                    // Redirigir al login después de un breve mensaje
                    setTimeout(() => {
                        this.router.navigate(['/login']);
                    }, 2000); // Espera 3 segundos antes de redirigir
                },
                (error) => {
                    this.errorMessage = error.error || 'Hubo un error al restablecer la contraseña. Intenta nuevamente.';
                    console.error('Error:', error);
                }
            );
    }

    private validatePasswords(password: string): string | null {
        if (password.length < 8 || password.length > 20) {
            return 'La contraseña debe tener entre 8 y 20 caracteres.';
        }
        if (!/[A-Z]/.test(password)) {
            return 'La contraseña debe contener al menos una letra mayúscula.';
        }
        if (!/[a-z]/.test(password)) {
            return 'La contraseña debe contener al menos una letra minúscula.';
        }
        if (!/\d/.test(password)) {
            return 'La contraseña debe contener al menos un número.';
        }
        if (!/[!@#$%^&*(),.?":{}|<>]/.test(password)) {
            return 'La contraseña debe contener al menos un carácter especial.';
        }
        return null;
    }
}
