import { Component } from '@angular/core';
import { AsyncPipe, NgIf } from "@angular/common";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import {Router, RouterLink} from "@angular/router";
import { WebKeyService } from "../../service/web-key.service";
import { CookieService } from "ngx-cookie-service";
import * as bcrypt from 'bcryptjs';

/**
 * Componente para configurar la clave web del usuario.
 * Este componente permite a los usuarios configurar una clave web segura,
 * generar un hash y salt, y guardar la clave en el sistema.
 */
@Component({
    selector: 'app-web-key-setup',
    standalone: true,
    imports: [
        AsyncPipe,
        FormsModule,
        NgIf,
        ReactiveFormsModule,
        RouterLink
    ],
    templateUrl: './web-key-setup.component.html',
    styleUrl: './web-key-setup.component.css'
})
export class WebKeySetupComponent {
    /** RUT del usuario, obtenido desde las cookies. */
    rut: string = '';

    /** Clave ingresada por el usuario. */
    key: string = '';

    /** Confirmación de la clave ingresada por el usuario. */
    confirmKey: string = '';

    /** Hash generado a partir de la clave del usuario. */
    hash: string = '';

    /** Salt generado para el hash de la clave del usuario. */
    salt: string = '';

    /** Indica si hay un error de coincidencia entre las claves ingresadas. */
    keyMatchError: boolean = false;

    /** Indica si la clave ingresada no cumple con los requisitos de formato. */
    keyFormatError: boolean = false;

    passwordFeedback: string | undefined;

    /**
     * Constructor del componente.
     * @param webKeyService Servicio para gestionar la clave web del usuario.
     * @param router Servicio de enrutamiento de Angular.
     * @param cookieService Servicio para manejar cookies.
     */
    constructor(
        private webKeyService: WebKeyService,
        private router: Router,
        private cookieService: CookieService
    ) { }

    /**
     * Maneja el evento de envío del formulario.
     * Este metodo valida las claves, genera el hash y salt, y envía la clave web al servidor para ser guardada.
     * @param event Evento de envío del formulario.
     */
    onSubmit(event: Event) {
        event.preventDefault();  // Evita la recarga de la página

        // Asignar el RUT desde las cookies
        this.rut = JSON.parse(this.cookieService.get('user')).rut || '';

        // Primero, validar las claves
        this.validateKeys();

        // Si la clave es válida, generar el hash y salt
        if (!this.keyFormatError && !this.keyMatchError) {
            console.log('Generando hash y salt...');
            this.generateHashAndSalt(this.key);

            console.log('Datos enviados:', this.salt, this.hash, this.rut);

            // Llamar al servicio para guardar la clave web
            this.webKeyService.saveWebKey(this.salt, this.hash, this.rut).subscribe(
                response => {
                    console.log('Respuesta del servidor:', response);
                    if (response.message === 'Clave web guardada con éxito') {
                        // Si la clave se guarda correctamente, actualizamos el estado y redirigimos
                        this.cookieService.set('webKeySetup', 'true');
                        this.router.navigate(['/display-keys']);
                    } else {
                        console.log(response.message);
                        alert('Hubo un error al guardar la clave web.');
                    }
                },
                error => {
                    console.error('Error en la solicitud:', error);
                    alert('Hubo un error al guardar la clave web. Por favor, inténtelo más tarde.');
                }
            );
        }
    }

    /**
     * Genera el hash y salt para la clave del usuario.
     * @param password La clave del usuario.
     */
    generateHashAndSalt(password: string) {
        const saltRounds = 12;  // Número de rounds de sal
        this.salt = bcrypt.genSaltSync(saltRounds);  // Genera el salt
        this.hash = bcrypt.hashSync(password, this.salt);  // Genera el hash con el salt
    }

    /**
     * Valida las claves ingresadas por el usuario.
     * Verifica que la clave cumpla con los requisitos de formato y que coincida con la confirmación.
     */
    validateKeys(): void {
        // Inicializar las variables de error
        this.keyFormatError = false;
        this.keyMatchError = false;
        this.passwordFeedback = '';

        // Validar longitud mínima de la clave
        const lengthValid = this.key.length >= 12;
        const containsUpperCase = /[A-Z]/.test(this.key);
        const containsLowerCase = /[a-z]/.test(this.key);
        const containsNumber = /\d/.test(this.key);

        if (!lengthValid) {
            this.keyFormatError = true;
            this.passwordFeedback = 'La clave debe tener al menos 12 caracteres.';
        } else if (!containsUpperCase) {
            this.keyFormatError = true;
            this.passwordFeedback = 'La clave debe contener al menos una mayúscula.';
        } else if (!containsLowerCase) {
            this.keyFormatError = true;
            this.passwordFeedback = 'La clave debe contener al menos una minúscula.';
        } else if (!containsNumber) {
            this.keyFormatError = true;
            this.passwordFeedback = 'La clave debe contener al menos un número.';
        }

        // Validación de coincidencia de claves solo si ambos campos tienen algo escrito
        if (this.key && this.confirmKey && this.key !== this.confirmKey) {
            this.keyMatchError = true;
        } else if (this.key && this.confirmKey && this.key === this.confirmKey) {
            this.keyMatchError = false;  // Si coinciden, el error de coincidencia se resuelve
        }
    }

}
