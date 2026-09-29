import { Component } from '@angular/core';
import {AbstractControl, ReactiveFormsModule, ValidationErrors, ValidatorFn} from '@angular/forms';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from "@angular/router";
import { CookieService } from 'ngx-cookie-service';
import { HttpClientModule } from "@angular/common/http";
import { FormRegistrationService } from "../../service/form-registration.service";
import { NumberFormatDirective } from './directives/number-format.directive';
import { RutFormatDirective } from './directives/rut-format.directive';

/**
 * Componente de registro de formulario para gestionar la recopilación de datos de usuario.
 * Este componente valida y envía los datos del formulario, incluyendo RUT, correo electrónico,
 * teléfono y más. Además, realiza la verificación del RUT para evitar duplicados.
 */
@Component({
    selector: 'app-form-registration',
    standalone: true,
    imports: [ReactiveFormsModule, CommonModule, HttpClientModule, NumberFormatDirective, RutFormatDirective],
    templateUrl: './form-registration.component.html',
    styleUrls: ['./form-registration.component.css']
})
export class FormRegistrationComponent {

    /** Formulario reactivo de registro */
    registrationForm: FormGroup;

    /** Mensaje de advertencia que se muestra si el formulario es inválido */
    warningMessage: string = "";

    /** Referencia al tipo FormGroup */
    protected readonly FormGroup = FormGroup;

    /**
     * Constructor del componente FormRegistrationComponent.
     *
     * @param formRegistrationService Servicio de registro del formulario que maneja la verificación del RUT.
     * @param fb Servicio de FormBuilder para crear el formulario reactivo.
     * @param router Instancia de Router para redirigir a otras vistas.
     * @param cookieService Servicio para gestionar cookies.
     */
    constructor(
        private formRegistrationService: FormRegistrationService,
        private fb: FormBuilder,
        private router: Router,
        private cookieService: CookieService
    ) {
        this.registrationForm = this.fb.group({
            firstName: ['', [Validators.required, Validators.minLength(2), Validators.pattern('^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$')]],
            lastName: ['', [Validators.required, Validators.minLength(2), Validators.pattern('^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$')]],
            email: ['', [Validators.required, Validators.email, this.emailValidator()]],
            phoneNumber: ['', [Validators.required, Validators.pattern('^569[0-9]{8}$')]],
            rut: ['', [Validators.required, Validators.pattern('^[0-9]{7,8}-[0-9kK]{1}$')]],  // Validación para RUT
            documentNumber: ['', [Validators.required, Validators.pattern('^[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}$')]],
            occupation: ['', [Validators.required]],
            address: ['', [Validators.required]],
            dateOfBirth: ['', [Validators.required, this.ageValidator(18, 100)]],
            termsAccepted: [false, Validators.requiredTrue]
        });
    }

    /**
     * Método que maneja el envío del formulario.
     * Si el formulario es válido, verifica que el RUT no exista, y luego guarda los datos en cookies.
     * Redirige a la página de verificación de identidad.
     */
    onSubmit() {
        if (this.registrationForm.valid) {
            const rut = this.separateRut(this.registrationForm.get('rut')?.value);
            this.formRegistrationService.verifyRutExists(rut[0], rut[1]).subscribe(
                (exists) => {
                    if (exists) {
                        this.registrationForm.get('rut')?.setErrors({ rutExists: true });
                    } else {
                        console.log('Formulario enviado:', this.registrationForm.value);
                        const { termsAccepted, ...accountHolderData } = this.registrationForm.value;
                        console.log("Contenido de accountHolderData", accountHolderData);
                        const partsRut: string[] = this.separateRut(accountHolderData.rut);
                        accountHolderData.rut = partsRut[0];
                        accountHolderData.verificationDigit = partsRut[1];
                        console.log("Contenido de accountHolderData: ", accountHolderData);
                        this.warningMessage = ''; // Limpiar mensaje de advertencia si el formulario es válido
                        this.cookieService.set('user', JSON.stringify(accountHolderData));
                        console.log("valor de email", JSON.parse(this.cookieService.get('user')).email);
                        this.cookieService.set('formCompleted', 'true');
                        this.router.navigate(['/verify-identity']); // redirección a la ruta verify-identity
                    }
                },
                (error) => {
                    console.error('Error al verificar el nombre', error);
                }
            );
        } else {
            console.log('Formulario no válido');
            this.markAllAsTouched(); // Marca todos los campos como tocados para mostrar los errores

            // Mostrar mensaje específico si el formulario no es válido
            this.warningMessage = 'Por favor, completa todos los campos obligatorios correctamente.';
        }
    }

    /**
     * Marca todos los campos del formulario como tocados para que se muestren los errores de validación.
     */
    markAllAsTouched() {
        Object.keys(this.registrationForm.controls).forEach(field => {
            const control = this.registrationForm.get(field);
            control?.markAsTouched({ onlySelf: true });
        });
    }

    /**
     * Validador de edad que asegura que el usuario esté dentro del rango de edad permitido.
     *
     * @param minAge Edad mínima permitida.
     * @param maxAge Edad máxima permitida.
     * @returns Función validadora que devuelve un objeto de error si la edad es incorrecta.
     */
    ageValidator(minAge: number, maxAge: number): ValidatorFn {
        return (control: AbstractControl): ValidationErrors | null => {
            const birthDate = new Date(control.value);
            const today = new Date();
            const age = today.getFullYear() - birthDate.getFullYear();

            const isUnderage = age < minAge ||
                (age === minAge && (today.getMonth() < birthDate.getMonth() ||
                    (today.getMonth() === birthDate.getMonth() && today.getDate() < birthDate.getDate())));

            const isOverage = age > maxAge ||
                (age === maxAge && (today.getMonth() > birthDate.getMonth() ||
                    (today.getMonth() === birthDate.getMonth() && today.getDate() > birthDate.getDate())));

            if (isUnderage) {
                console.log("Underage error");  // Verifica si este log aparece
                return { underage: true };
            }
            if (isOverage) {
                console.log("Overage error");  // Verifica si este log aparece
                return { overage: true };
            }
            return null;
        };
    }


    /**
     * Función para separar el RUT en su parte numérica y su dígito de verificación.
     *
     * @param rut El RUT completo con su dígito de verificación.
     * @returns Un array con la parte numérica del RUT y el dígito de verificación.
     */
    separateRut(rut: string): string[] {
        return rut.split('-');
    }

    emailValidator(): ValidatorFn {
        return (control: AbstractControl): ValidationErrors | null => {
            const value = control.value || '';
            let error: ValidationErrors = {}; // Usar un objeto vacío para los errores

            // Verificar si hay al menos un punto y una arroba
            if (!value.includes('@')) {
                error['missingAt'] = true;
            }
            if (value.indexOf('@') !== value.lastIndexOf('@')) {
                error['multipleAt'] = true;
            }
            if (!value.includes('.')) {
                error['missingDot'] = true;
            }

            // Si hay errores, devolver el objeto de errores
            return Object.keys(error).length > 0 ? error : null;
        };
    }
}

