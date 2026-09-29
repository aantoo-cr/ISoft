import { Component, OnInit } from '@angular/core';
import { IdentityVerificationService } from "../../service/identity-verification.service";
import { Router, RouterLink } from "@angular/router";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { CookieService } from 'ngx-cookie-service';  // Servicio de cookies
import { FormRegistrationService } from "../../service/form-registration.service";
import {NgIf} from "@angular/common";

/**
 * Componente VerifyIdentityComponent
 * Este componente gestiona la verificación de identidad a través de un código de verificación
 * enviado al email del usuario. Permite al usuario ingresar y validar el código, y continuar
 * con el proceso de registro si es correcto.
 */
@Component({
    selector: 'app-verify-identity',
    standalone: true,
    imports: [
        ReactiveFormsModule,
        FormsModule,
        RouterLink,
        NgIf
    ],
    templateUrl: './verify-identity.component.html',
    styleUrl: './verify-identity.component.css'
})
export class VerifyIdentityComponent implements OnInit {

    /** Email del usuario, obtenido de las cookies. */
    email: string = '';

    /** Código PIN enviado al usuario para verificación. */
    pin: string = '';

    /** Contador de intentos de verificación. */
    cont: number = 0;
    errorMessage: string | null = null;

    /**
     * Constructor del componente VerifyIdentityComponent.
     * @param {FormRegistrationService} formRegistrationService - Servicio para registrar el formulario de usuario.
     * @param {IdentityVerificationService} verificationService - Servicio para verificar la identidad del usuario.
     * @param {Router} router - Servicio Angular para navegar entre rutas.
     * @param {CookieService} cookieService - Servicio para manejar cookies.
     */
    constructor(
        private formRegistrationService: FormRegistrationService,
        private verificationService: IdentityVerificationService,
        private router: Router,
        private cookieService: CookieService
    ) {
    }

    /**
     * ngOnInit: Ciclo de vida de Angular.
     * Se ejecuta cuando se inicializa el componente.
     * Obtiene el email del usuario desde las cookies y envía el código de verificación.
     */
    ngOnInit(): void {
        const userCookie = this.cookieService.get('user');
        if (userCookie) {
            const user = JSON.parse(userCookie);
            this.email = user.email;
            if (this.email) {
                this.sendVerificationCode();
            } else {
                console.error('No se encontró el email en las cookies');
                this.router.navigate(['form-registration']);
            }
        } else {
            console.error('No se encontró la cookie del usuario');
            this.router.navigate(['form-registration']);
        }
    }

    /**
     * Envía el código de verificación al email del usuario.
     * Llama al servicio de verificación para generar y enviar el código.
     */
    sendVerificationCode(): void {
        console.log("Enviando código de verificación a:", this.email);

        this.verificationService.requestCodeVerification(this.email).subscribe(
            (response: any) => { // Usa 'any' aquí temporalmente para evitar el problema de tipo
                console.log('Respuesta de la API:', response); // Imprime toda la respuesta
                if (response && response.message === "Código enviado exitosamente") {
                    console.log('El código de verificación fue enviado correctamente.');
                    this.errorMessage = null;
                } else {
                    console.error('Respuesta inesperada del servidor:', response);
                    this.errorMessage = "Hubo un problema al enviar el código. Por favor, inténtelo nuevamente.";
                }
            },
            (error: any) => {
                console.error('Error al enviar el código de verificación:', error);
                this.errorMessage = "Hubo un problema al enviar el código. Por favor, inténtelo nuevamente.";
            }
        );

    }


    validateCode(): void {
        if (!this.pin) {
            this.errorMessage = 'Por favor ingrese el código de verificación';
            return;
        }

        this.verificationService.requestValidateCode(this.pin).subscribe(
            (response) => {
                const user = JSON.parse(this.cookieService.get('user'));
                this.formRegistrationService.sendFormContent(user).subscribe(
                    (response) => {
                        console.log('Respuesta del servidor:', response);
                        if (response.message === 'Cuenta creada con éxito') {
                            this.cookieService.set('sessionId', response.sessionId);
                            this.cookieService.set('identityVerified', 'true');
                            this.router.navigate(['web-key/setup']);
                        } else {
                            console.log(response.message);
                            this.errorMessage = 'Hubo un problema al verificar la cuenta. Inténtelo nuevamente.';
                        }
                    },
                    (error) => {
                        console.error('Error en la solicitud:', error);
                        this.errorMessage = 'Hubo un error al enviar la información. Intente nuevamente.';
                    }
                );
            },
            (error) => {
                if (error.status === 500) {
                    this.errorMessage = 'Código de verificación incorrecto. Por favor, intente nuevamente.';
                } else {
                    this.errorMessage = 'Error al validar el código. Por favor, intente nuevamente.';
                }
            }
        );
    }
}
