import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common'; // Importa CommonModule

import { AddDeviceService } from '../../service/add-device.service';

@Component({
  selector: 'app-ingresar-dispositivo',
  standalone: true,
  imports: [CommonModule, FormsModule], // Agrega CommonModule aquí
  templateUrl: './ingresar-dispositivo.component.html',
  styleUrls: ['./ingresar-dispositivo.component.css']
})
export class IngresarDispositivoComponent {
  email: string = '';
  deviceName: string = '';
  deviceModel: string = '';
  message: string = '';
  isError: boolean = false; // Variable para identificar si el mensaje es de error

  constructor(private addDeviceService: AddDeviceService, private router: Router) {}

  addDevice() {
    this.addDeviceService.addDevice(this.email, this.deviceName, this.deviceModel)
      .subscribe({
        next: (response) => {
          this.message = 'Dispositivo agregado correctamente';
          this.isError = false; // Mensaje de éxito
          this.router.navigate(['/vincular-dispositivo'], { queryParams: { email: this.email, deviceName: this.deviceName, deviceModel: this.deviceModel } });
        },
        error: (error) => {
          if (error.error && typeof error.error === 'string') {
            this.message = error.error; // Mensaje de error personalizado
          } else {
            this.message = 'Ocurrió un error al agregar el dispositivo, ingrese un correo valido';
          }
          this.isError = true; // Indicar que es un error
        }
      });
  }
}
