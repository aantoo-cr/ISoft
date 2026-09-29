import { Component, OnInit } from '@angular/core';
import confetti from 'canvas-confetti';
import { RegistrationSuccessService } from "../../service/registration-success.service";
import { CookieService } from "ngx-cookie-service";
import { Router} from '@angular/router';

/**
 * Componente que se muestra al usuario después de un registro exitoso.
 * Muestra una animación de confeti y realiza acciones de registro adicionales como la creación de una cuenta bancaria.
 */
@Component({
    selector: 'app-registration-success',
    standalone: true,
    templateUrl: './registration-success.component.html',
    styleUrl: './registration-success.component.css'
})
export class RegistrationSuccessComponent implements OnInit {

    /**
     * Constructor del componente.
     * @param registrationSuccessService Servicio para manejar el registro de éxito y la creación de cuenta bancaria.
     * @param cookieService Servicio para manejar cookies.
     * @param router
     */
    constructor(private registrationSuccessService: RegistrationSuccessService, private cookieService: CookieService, private router: Router) {
    }

    /**
     * Lanza la animación de confeti en la pantalla.
     * Utiliza la librería `canvas-confetti` para generar el efecto visual.
     */
    launchConfetti() {
        confetti({
            particleCount: 200,   // Número de partículas de confeti
            spread: 1000,         // Ángulo de dispersión
            origin: {y: 0.3}    // Posición de origen de las partículas
        });
    }

    /**
     * Metodo de ciclo de vida `ngOnInit` que se ejecuta cuando el componente es inicializado.
     * Lanza la animación de confeti y crea una cuenta bancaria para el usuario.
     * También limpia las cookies almacenadas después de la ejecución.
     */
    ngOnInit() {
        // Lanza la animación de confeti
        this.launchConfetti();

        // Obtiene el RUT del usuario desde las cookies
        const rut: string = JSON.parse(this.cookieService.get('user')).rut;
        console.log('Rut:', rut);

        // Llama al servicio para crear una cuenta bancaria utilizando el RUT del usuario
        this.registrationSuccessService.createBankAccount(rut || '').subscribe(
            response => {
                console.log('Respuesta del servidor:', response);
                if (response.message === 'Cuenta bancaria creada con éxito') {
                    console.log('Cuenta creada con éxito');
                } else {
                    console.log(response.message);
                }
            },
            error => {
                console.error('Error en la solicitud:', error);
            }
        );
    }

    onAccept(): void {
        // Elimina todas las cookies relacionadas con el usuario
        this.cookieService.deleteAll();

        // Redirige al usuario a la página de inicio de sesión
        this.router.navigate(['/login']);
    }
}

