import { Component } from '@angular/core';
import { FundsService } from '../../service/funds.service';

import { Router } from '@angular/router';
import { CommonModule, CurrencyPipe, DatePipe, DecimalPipe } from "@angular/common";
import { Transaction } from "../../domain/transaction.model";

import { GetaccountService } from '../../service/getaccount.service';
import { Subscription } from 'rxjs'; // Import Subscription from rxjs

/**
 * `FundsComponent` es un componente que maneja la visualización y operación de fondos de un usuario.
 * Permite al usuario ver su saldo, añadir fondos y consultar sus transacciones.
 */
@Component({
    selector: 'app-funds',
    standalone: true,
    templateUrl: './funds.component.html',
    imports: [
        CommonModule,
        DatePipe,
        DecimalPipe,
        CurrencyPipe
    ],
    styleUrls: ['./funds.component.css']
})

export class FundsComponent {

    /**
     * Representa el saldo del usuario. Inicializado como oculto.
     * @type {string}
     */
    saldo: string = "**********";

    /**
     * Total de transacciones del usuario.
     * @type {number}
     */
    totalItems: number = 0;

    /**
     * Indicador de si el saldo está visible o no.
     * @type {boolean}
     */
    saldoVisible: boolean = false;

    /**
     * Mensaje de error a mostrar en la interfaz si ocurre un fallo.
     * @type {string | null}
     */
    mensajeError: string | null = null;

    /**
     * Mensaje de éxito a mostrar en la interfaz si la operación es exitosa.
     * @type {string | null}
     */
    mensajeExito: string | null = null;

    /**
     * Mensaje informativo a mostrar en la interfaz, como cuando no hay transacciones.
     * @type {string | null}
     */
    mensajeInfo : string | null = null;

    /**
     * Rut del usuario actual. Este valor puede obtenerse de un formulario o de la sesión o llamando a getaccount.service.
     * @type {string}
     */
    numCuenta: string = "";

    /**
     * Lista de transacciones del usuario.
     * @type {Transaction[]}
     */
    transacciones: Transaction[] = [];

    /**
     * Lista de transacciones para la página actual.
     * @type {any[]}
     */
    transaccionesPagina: any[] = [];

    /**
     * Página actual en el sistema de paginación.
     * @type {number}
     */
    paginaActual: number = 1;

    /**
     * Número de transacciones por página.
     * @type {number}
     */
    transaccionesPorPagina: number = 15;

    /**
     * Total de páginas para la paginación de transacciones.
     * @type {number}
     */
    totalPaginas: number = 1;


    /**
     * Suscripción inicializada para gestionar las suscripciones de los observables.
     */
    private subscription: Subscription = new Subscription(); // Initialize subscription


    /**
     * Constructor que inyecta los servicios de fondos y cuenta, además del enrutador.
     *
     * @param {FundsService} fundsService Servicio para manejar operaciones relacionadas con fondos.
     * @param {GetaccountService} getAccountService Servicio para obtener el número de cuenta del usuario.
     * @param {Router} router Servicio para manejar la navegación entre rutas.
     */

    constructor(
        private fundsService: FundsService,
        private getAccountService: GetaccountService,
        private router: Router
    ) {}


    /**
     * Método del ciclo de vida de Angular que se ejecuta cuando se inicializa el componente.
     *
     * En este método:
     * - Se llama al servicio `GetaccountService` para obtener el número de cuenta del usuario.
     * - Si el número de cuenta no está disponible o es inválido, se redirige al usuario a la página de inicio de sesión.
     * - En caso de error al obtener el número de cuenta, también se redirige al usuario a la página de inicio de sesión.
     *
     * @returns {void}
     */
    ngOnInit(): void {
        // Llamamos al servicio GetaccountService para obtener el número de cuenta
        this.getAccountService.obtenerNumCuenta().subscribe({
            next: (numCuenta) => {
                this.numCuenta = numCuenta;

                if (!this.numCuenta) {
                    console.error('No se ha iniciado sesión');
                    this.router.navigate(['/login']); // Redirige si no hay número de cuenta
                } else {
                    console.log('Número de cuenta:', this.numCuenta);
                }
            },
            error: (err) => {
                console.error('Error al obtener el número de cuenta:', err);
                this.router.navigate(['/login']); // Redirige en caso de error
            },
            complete: () => {
                console.log('Solicitud de número de cuenta completada');
            }
        });
    }


    /**
     * Método de ciclo de vida `ngOnDestroy`. Se asegura de que todas las suscripciones sean canceladas
     * al destruir el componente para evitar fugas de memoria.
     *
     * @returns {void}
     */

    ngOnDestroy(): void {
        // Unsubscribe from all subscriptions to prevent memory leaks
        this.subscription.unsubscribe();
    }


    /**
     * Muestra el saldo actual del usuario llamando al servicio de fondos.
     * Si hay un error al obtener el saldo, muestra un mensaje de error.
     *
     * @returns {void}
     */
    mostrarSaldo(): void {
        this.fundsService.obtenerSaldo(this.numCuenta).subscribe({
            next: (saldo) => this.saldo = saldo.toFixed(2),
            error: () => this.saldo = "Saldo no disponible"
        });
    }

    /**
     * Oculta el saldo del usuario reemplazándolo por asteriscos.
     *
     * @returns {void}
     */
    ocultarSaldo(): void {
        this.saldo = "**********";
    }


    /**
     * Alterna entre mostrar y ocultar el saldo.
     *
     * @returns {void}
     */
    toggleSaldo(): void {
        if (this.saldoVisible) {
            this.ocultarSaldo();
        } else {
            this.mostrarSaldo();
        }
        this.saldoVisible = !this.saldoVisible;
    }


    /**
     * Obtiene el texto del botón dependiendo de si el saldo está visible o no.
     *
     * @returns {string} Texto para el botón, "Ver Saldo" o "Ocultar Saldo".
     */
    obtenerTextoBoton(): string {
        return this.saldoVisible ? 'Ocultar Saldo' : 'Ver Saldo';
    }

    /**
     * Obtiene las transacciones del usuario llamando al servicio de fondos.
     * Si ocurre un error, muestra un mensaje de error.
     *
     * @returns {void}
     */
    verMovimientos(): void {
        this.limpiarMensajes(); // Limpiamos cualquier mensaje existente antes de realizar la llamada
        this.fundsService.obtenerTransacciones(this.numCuenta).subscribe({
            next: (transacciones) => {
                if (transacciones && transacciones.length > 0) {
                    this.transacciones = transacciones;
                    this.totalItems = transacciones.length;  // Asegúrate de que totalItems se actualice
                    this.totalPaginas = Math.ceil(this.totalItems / this.transaccionesPorPagina); // Calcula el total de páginas
                    this.actualizarPagina(); // Actualiza las transacciones mostradas según la página actual
                } else {
                    this.mensajeInfo = 'No hay movimientos encontrados';
                    setTimeout(() => this.mensajeInfo = null, 5000); // Ocultar mensaje después de 5 segundos
                }
            },
            error: () => {
                this.mensajeError = 'Error al obtener Movimientos';
                setTimeout(() => this.mensajeError = null, 5000);
            }
        });
    }



    /**
     * Actualiza las transacciones mostradas según la página actual.
     *
     * @returns {void}
     */
    actualizarPagina(): void {
        const inicio = (this.paginaActual - 1) * this.transaccionesPorPagina;
        const fin = inicio + this.transaccionesPorPagina;
        this.transaccionesPagina = this.transacciones.slice(inicio, fin);
    }


    /**
     * Cambia entre la página anterior o siguiente en la paginación.
     *
     * @param {('anterior' | 'siguiente')} direccion - Dirección para cambiar de página.
     *
     * @returns {void}
     */
    cambiarPagina(direccion: 'anterior' | 'siguiente'): void {
        if (direccion === 'anterior' && this.paginaActual > 1) {
            this.paginaActual--;
        } else if (direccion === 'siguiente' && this.paginaActual < this.totalPaginas) {
            this.paginaActual++;
        }
        this.actualizarPagina();
    }

    /**
     * Cambia directamente a una página específica.
     *
     * @param {number} pagina - Número de la página a la que cambiar.
     *
     * @returns {void}
     */
    cambiarPaginaDirecta(pagina: number): void {
        if (pagina >= 1 && pagina <= this.totalPaginas) {
            this.paginaActual = pagina;
            this.actualizarPagina();
        }
    }

    /**
     * Obtiene los números de las páginas para mostrar los botones de paginación.
     *
     * @returns {number[]} Arreglo de números de página.
     */
    getPageNumbers(): number[] {
        const pages = [];
        for (let i = 1; i <= this.totalPaginas; i++) {
            pages.push(i);
        }
        return pages;
    }


    /**
     * Añade fondos a la cuenta del usuario.
     * Si la operación es exitosa, muestra un mensaje de éxito.
     *
     * @returns {void}
     */
    anadirFondos(): void {
        this.limpiarMensajes(); // Limpiamos cualquier mensaje existente antes de realizar la llamada
        const monto = 10000;  // Monto fijozz
        this.fundsService.agregarFondos(this.numCuenta, monto).subscribe({
            next: (transaccion) => {
                this.mensajeExito = 'Se han añadido 10.000 a su cuenta';
                setTimeout(() => this.mensajeExito = null, 5000);
            },
            error: () => {
                this.mensajeError = 'Error al añadir saldo';
                setTimeout(() => this.mensajeError = null, 5000);
            }
        });
    }

    /**
     * Limpia los mensajes de error, éxito e información.
     *
     * @returns {void}
     */
    private limpiarMensajes(): void {
        this.mensajeError = null;
        this.mensajeExito = null;
        this.mensajeInfo = null;
    }

    protected readonly Math = Math;
}

