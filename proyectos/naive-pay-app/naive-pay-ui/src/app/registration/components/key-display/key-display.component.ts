import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { CommonModule } from '@angular/common';
import { CredentialService } from '../../service/credential.service';
import { KeyDisplayService } from "../../service/key-display.service";
import { CookieService } from 'ngx-cookie-service';
import { DiffieHellmanService } from "../../service/diffie-hellman.service";

/**
 * Componente que muestra y maneja la generación, copia y almacenamiento de claves públicas y privadas.
 * Permite crear una credencial asociada a un titular de cuenta mediante el uso de varios servicios.
 */
@Component({
    selector: 'app-key-display',
    standalone: true,
    imports: [
        CommonModule,
        ReactiveFormsModule,
        FormsModule
    ],
    templateUrl: './key-display.component.html',
    styleUrls: ['./key-display.component.css'],
})
export class KeyDisplayComponent implements OnInit {
    public publicKey: string = '';
    public privateKey: string = '';
    public errorMessage: string = '';
    public copiedPublic: boolean = false;
    public copiedPrivate: boolean = false;
    public rutAccountHolder: string = '';
    public keyState: 'ACTIVE' | 'INACTIVE' = 'ACTIVE';

    /**
     * Constructor que inyecta los servicios necesarios para el componente.
     *
     * @param keyDisplayService Servicio para manejar la visualización de claves.
     * @param credentialService Servicio para crear credenciales.
     * @param cdr Servicio para la detección de cambios.
     * @param router Servicio de navegación.
     * @param cookieService Servicio de cookies.
     * @param diffieHellmanService Servicio para generar pares de claves Diffie-Hellman.
     */
    constructor(
        private keyDisplayService: KeyDisplayService,
        private credentialService: CredentialService,
        private cdr: ChangeDetectorRef,
        private router: Router,
        private cookieService: CookieService,
        private diffieHellmanService: DiffieHellmanService
    ) {}

    /**
     * Metodo del ciclo de vida del componente que se ejecuta al inicializar.
     * Obtiene el par de claves y el RUT desde la cookie.
     */
    ngOnInit(): void {
        this.getKeyPair();
        this.getRutFromCookie();
    }

    /**
     * Genera un par de claves utilizando el servicio Diffie-Hellman.
     * Asigna las claves generadas a las propiedades y actualiza la vista.
     */
    getKeyPair(): void {
        try {
            const keyPair = this.diffieHellmanService.generateKeyPair();
            this.publicKey = keyPair.publicKey;
            this.privateKey = keyPair.privateKey;
            console.log("Valores obtenidos:", this.publicKey, this.privateKey);
            this.cdr.detectChanges();
        } catch (error) {
            this.errorMessage = 'Error al obtener las claves.';
            console.error("Error en getKeyPair:", error);
            this.cdr.detectChanges();
        }
    }

    /**
     * Obtiene el RUT del titular de cuenta desde una cookie y lo asigna a la propiedad `rutAccountHolder`.
     */
    getRutFromCookie(): void {
        const rutFromCookie = JSON.parse(this.cookieService.get('user')).rut;
        if (rutFromCookie) {
            this.rutAccountHolder = rutFromCookie;
            console.log('RUT obtenido de la cookie:', this.rutAccountHolder);
        } else {
            console.error('No se encontró el RUT en las cookies');
        }
    }

    /**
     * Copia el texto proporcionado al portapapeles y actualiza el estado de copia.
     *
     * @param text El texto que se va a copiar al portapapeles.
     * @param keyType El tipo de clave (public o private) para actualizar el estado correspondiente.
     */
    copyToClipboard(text: string, keyType: string): void {
        navigator.clipboard.writeText(text).then(() => {
            if (keyType === 'public') {
                this.copiedPublic = true;
                setTimeout(() => {
                    this.copiedPublic = false;
                }, 2000);
            } else if (keyType === 'private') {
                this.copiedPrivate = true;
                setTimeout(() => {
                    this.copiedPrivate = false;
                }, 2000);
            }
        }).catch((error) => {
            console.error('Error al copiar al portapapeles', error);
        });
    }

    /**
     * Crea una credencial a partir del DTO de credencial y la guarda usando el servicio CredentialService.
     */
    createCredential(): void {
        const credentialDTO = {
            publicKey: this.publicKey,
            keyState: this.keyState,
            rutAccountHolder: this.rutAccountHolder,
        };

        this.credentialService.createCredential(credentialDTO).subscribe(
            (response) => {
                console.log('Credencial creada exitosamente', response);
                this.cookieService.set('keyDisplayed', 'true');
                this.router.navigate(['/success']);
            },
            (error) => {
                console.error('Error al crear la credencial', error);
                this.errorMessage = 'Hubo un error al crear la credencial.';
            }
        );
    }

    /**
     * Redirige al usuario a la ruta `/success` al hacer clic en "Aceptar".
     */
    onAccept(): void {
        this.createCredential();
        this.router.navigate(['/success']);
    }

    downloadKey(key: string, fileName: string): void {
        const blob = new Blob([key], { type: 'text/plain' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = fileName;
        a.click();
        window.URL.revokeObjectURL(url);
    }

}
