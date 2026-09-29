import { Component } from '@angular/core';
import {Router, RouterLink} from '@angular/router';  // Para la navegación
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { jwtDecode } from 'jwt-decode';
import { DevicesService } from "../../../devices/service/device.service";
import { AuthService } from "../../service/auth.service";
import {routes} from "../../../app.routes";  // Importar la librería para decodificar JWT

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [CommonModule, FormsModule, RouterLink],
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent {
    errorMessage: string | null = null;
    userRut: string | null = null;

    email: string = "";
    password: string = "";

    constructor(
        private authService: AuthService,  // Inyectamos el servicio de autenticación
        private router: Router,
        private devicesService: DevicesService  // Servicio para manejar dispositivos vinculados
    ) {}

    ngOnInit(): void {
        const storedToken = localStorage.getItem('access_token');

        // Si ya existe un token, redirigir automáticamente al dashboard
        if (storedToken) {
            this.router.navigate(['/dashboard']);
        }
    }

    /**
     * Método para manejar el inicio de sesión.
     * Si ya hay un token en localStorage, no es necesario volver a hacer login.
     * Si no, realiza el login de forma normal y verifica si el usuario tiene dispositivos vinculados.
     */
    onSubmitLogin(): void {
        const storedToken = localStorage.getItem('access_token');

        // Si ya existe un token en localStorage, no es necesario hacer login de nuevo
        if (storedToken) {
            this.errorMessage = null;

            // Decodificar el token almacenado
            const decoded: any = jwtDecode(storedToken);
            const rut = decoded?.iss;
            const userRole = decoded?.role;  // Decodificar el rol

            // Verificar si los datos del token siguen siendo válidos en localStorage
            if (rut && userRole) {
                localStorage.setItem('user_rut', rut);
                localStorage.setItem('user_role', userRole);
                this.userRut = rut;
                this.routeBasedOnRole(userRole); // Redirigir según el rol
                this.checkDeviceLinked(); // Verificar dispositivos vinculados
            } else {
                // Si los datos no son válidos, borrar el token y redirigir al login
                localStorage.removeItem('access_token');
                localStorage.removeItem('user_rut');
                localStorage.removeItem('user_role');
                this.router.navigate(['/login']);
            }

            return;  // Salir si ya existe un token
        }

        // Si no hay token, realizar login de forma normal
        this.authService.login(this.email, this.password)
            .subscribe({
                next: (response) => {
                    const token = response.token;

                    if (token) {
                        this.errorMessage = null;

                        // Guardar el token en localStorage
                        localStorage.setItem('access_token', token);

                        // Decodificar el token para extraer el RUT y el rol
                        const decoded: any = jwtDecode(token);
                        const rut = decoded?.iss;
                        const userRole = decoded?.role;

                        if (userRole === "ADMIN"){
                            localStorage.setItem('user_rut', rut);
                            localStorage.setItem('user_role', userRole);
                            this.router.navigate(['/admin']);
                        }
                        // Almacenar RUT y rol en localStorage si están disponibles
                        else if (userRole === 'TITULAR') {
                            localStorage.setItem('user_rut', rut);
                            localStorage.setItem('user_role', userRole);
                            this.userRut = rut;

                            // this.routeBasedOnRole(userRole);
                            // Verificar dispositivos vinculados
                            this.checkDeviceLinked();

                            // Redirigir según el rol

                        }
                    } else {
                        this.errorMessage = 'No token received in response';
                    }
                },
                error: (error) => {
                    // Manejo de error en caso de credenciales incorrectas
                    this.errorMessage = error?.error?.message || 'Invalid login credentials';
                }
            });
    }

    /**
     * Verifica si el usuario tiene dispositivos vinculados.
     * Si tiene dispositivos, lo redirige a la página principal (dashboard),
     * de lo contrario, lo redirige a la página para ingresar un dispositivo.
     */
    checkDeviceLinked(): void {
        const rut = localStorage.getItem('user_rut');
        if (!rut) {
            this.errorMessage = 'User RUT is not available';
            return;
        }

        // Llamada al servicio para obtener dispositivos vinculados al RUT
        this.devicesService.getDevicesByRut(rut).subscribe(
            (devices: any[]) => {
                if (devices && devices.length > 0) {
                    // Si tiene dispositivos, redirigir al dashboard
                    this.router.navigate(['/dashboard']);
                } else {
                    // Si no tiene dispositivos vinculados, redirigir a la página para agregar uno
                    this.router.navigate(['/ingresar-dispositivo']);
                }
            },
            (error) => {
                // Manejo de error al verificar los dispositivos
                console.error('Error verifying linked devices:', error);
                this.errorMessage = 'Error verifying devices. Please try again later.';
            }
        );


    }
    /**
     * Redirige al usuario según su rol: TITULAR -> '/' o ADMIN -> '/admin'
     * @param userRole El rol del usuario (TITULAR o ADMIN)
     */
    routeBasedOnRole(userRole: string): void {
        if (userRole === 'TITULAR') {
            this.router.navigate(['/']);  // Redirige al inicio para TITULAR
        } else if (userRole === 'ADMIN') {
            this.router.navigate(['/admin']);  // Redirige a /admin para ADMIN
        } else {
            this.router.navigate(['/dashboard']);  // Redirige a un dashboard genérico si no tiene rol específico
        }
    }

}
