/**
 * Componente para el formulario de recuperación de contraseña.
 * Permite al usuario solicitar un enlace de restablecimiento de contraseña
 * a través de su dirección de correo electrónico.
 *
 * @author Marcelo
 */

import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from "@angular/forms";
import { CommonModule } from '@angular/common';
import {Router} from "@angular/router";

/**
 * Define el selector del componente, el archivo de plantilla, el archivo de estilos y
 * los módulos necesarios para el uso en la plantilla.
 */
@Component({
    selector: 'app-forgot-password',
    templateUrl: './forgot-password.component.html',
    standalone: true,
    imports: [
        FormsModule,
        CommonModule
    ],
    styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent {
    email: string = '';              // Dirección de correo electrónico ingresada por el usuario
    confirmacion: boolean = false;   // Verificación de que el usuario confirma la solicitud de recuperación
    mensajeExito: boolean = false;   // Control para mostrar mensaje de éxito en la plantilla
    mensajeError: string = '';       // Mensaje de error detallado en caso de fallo
    isLoading: boolean = false;      // Control para mostrar el estado de carga

    /**
     * Constructor del componente ForgotPasswordComponent
     * @param http - Cliente HTTP de Angular para enviar la solicitud de recuperación
     * @param router - Servicio Router para la redirección
     */
    constructor(private http: HttpClient, private router: Router) {}

    /**
     * Valida las condiciones necesarias para proceder con la solicitud de recuperación.
     * Muestra mensajes de alerta si el campo de correo electrónico está vacío o si el
     * usuario no ha confirmado la recuperación de su cuenta.
     */
    validarAcciones(): void {
        if (this.email.trim() === '') {
            this.mostrarError("No deje el campo de texto vacío.");
            return;
        }
        if (!this.confirmacion) {
            this.mostrarError("Confirme que quiere recuperar su cuenta.");
            return;
        }
        this.enviarSolicitudRecuperacion();
    }

    /**
     * Envía una solicitud POST al endpoint /api/forgot-password para iniciar el
     * proceso de recuperación de contraseña con el correo electrónico proporcionado.
     * Actualiza el mensaje de éxito o maneja el mensaje de error basado en la respuesta
     * del servidor.
     */
    enviarSolicitudRecuperacion(): void {
        this.isLoading = true;  // Activa el estado de carga

        this.http.post('http://localhost:8080/forgot-password', { email: this.email })
            .subscribe({
                next: (response: any) => {
                    if (response.success) {
                        this.mensajeExito = true;  // Muestra el mensaje de éxito
                        this.mensajeError = '';    // Limpiar mensaje de error si la solicitud es exitosa
                    } else {
                        this.mensajeError = response.message || 'Error desconocido.';
                    }
                    this.isLoading = false;  // Desactiva el estado de carga
                },
                error: (error) => {
                    this.manejoErrores(error);
                    this.isLoading = false;  // Desactiva el estado de carga
                }
            });
    }

    private mostrarError(mensaje: string): void {
        this.mensajeError = mensaje;
        this.mensajeExito = false;
    }

    private manejoErrores(error: any): void {
        switch (error.status) {
            case 404:
                this.mensajeError = "Correo electrónico no encontrado. Verifique y vuelva a intentarlo.";
                break;
            case 500:
                this.mensajeError = "Hubo un problema en el servidor. Por favor, inténtelo más tarde.";
                break;
            default:
                this.mensajeError = "Hubo un error al enviar la solicitud. Inténtelo nuevamente.";
                break;
        }
    }

    /**
     * Cierra el mensaje de éxito y redirige al usuario al login.
     */
    cerrarMensajeExito(): void {
        this.mensajeExito = false;
        this.router.navigate(['/login']);  // Redirige al login
    }
}
