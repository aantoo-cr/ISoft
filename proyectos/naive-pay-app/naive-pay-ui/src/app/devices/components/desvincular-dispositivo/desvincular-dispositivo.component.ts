import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from '../../../shared/components/header/header.component';
import { SidebarComponent } from '../../../shared/components/sidebar/sidebar.component';
import { DesvincularDispositivoService } from '../../service/desvincular-dispositivo.service'; // Ajusta la ruta según tu estructura de carpetas

@Component({
  selector: 'app-desvincular-dispositivo',
  standalone: true,
  imports: [CommonModule, FormsModule, HeaderComponent, SidebarComponent],
  templateUrl: './desvincular-dispositivo.component.html',
  styleUrls: ['./desvincular-dispositivo.component.css']
})
export class DesvincularDispositivoComponent {
  email: string = '';
  mensajeCorreoInvalido: string = '';
  deviceName: string = '';
  deviceModel: string = '';

  constructor(
    private desvincularDispositivoService: DesvincularDispositivoService,
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

    this.desvincularDispositivoService.enviarCodigo(this.email).subscribe({
      next: (response: any) => {
        console.log('Respuesta del servidor:', response);
        if (response.message.includes('Código de desvinculación enviado a')) {
          this.deviceName = response.deviceName;
          this.deviceModel = response.deviceModel;
          this.router.navigate(['/codigo-desvincular'], { queryParams: { email: this.email, deviceName: this.deviceName, deviceModel: this.deviceModel } });
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
