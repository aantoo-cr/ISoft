import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { VerificarCorreoService } from '../../service/verificar-correo.service'; // Ajusta la ruta según tu estructura de carpetas

@Component({
  selector: 'app-vincular-dispositivo',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './vincular-dispositivo.component.html',
  styleUrls: ['./vincular-dispositivo.component.css']
})
export class VincularDispositivoComponent {
  email: string = '';
  mensajeCorreoInvalido: string = '';
  deviceName: string = '';
  deviceModel: string = '';

  constructor(
    private verificarCorreoService: VerificarCorreoService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.email = this.route.snapshot.queryParamMap.get('email') || '';
    this.deviceName = this.route.snapshot.queryParamMap.get('deviceName') || '';
    this.deviceModel = this.route.snapshot.queryParamMap.get('deviceModel') || '';
    console.log('Email:', this.email);
    console.log('Device Name:', this.deviceName);
    console.log('Device Model:', this.deviceModel);
  }

  onEmailChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.email = input.value;
  }

  validarCorreo(): boolean {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!regex.test(this.email)) {
      this.mensajeCorreoInvalido = 'Ingrese un correo electrónico válido.';
      return false;
    }
    this.mensajeCorreoInvalido = '';
    return true;
  }

  verificarCorreo(event: Event): void {
    event.preventDefault();
    if (!this.validarCorreo()) return;

    console.log('Email:', this.email);
    console.log('Device Name:', this.deviceName);
    console.log('Device Model:', this.deviceModel);

    this.verificarCorreoService.verificarCorreo(this.email).subscribe({
      next: (response: string) => {
        console.log('Respuesta del servidor:', response);
        if (response.includes('Código de vinculación enviado a')) {
          this.router.navigate(['/codigo-vincular'], { queryParams: { email: this.email, deviceName: this.deviceName, deviceModel: this.deviceModel } });
        } else {
          this.mensajeCorreoInvalido = 'El correo no ha sido encontrado en el sistema.';
        }
      },
      error: (error) => {
        console.error('Error en la solicitud:', error);
        this.mensajeCorreoInvalido = 'Ocurrió un error al verificar el correo.';
      }
    });
  }
}
