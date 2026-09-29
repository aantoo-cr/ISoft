import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { VerificarCodigoService } from '../../service/verificar-codigo.service'; // Ajusta la ruta según tu estructura de carpetas

@Component({
  selector: 'app-codigo-vincular',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './codigo-vincular.component.html',
  styleUrls: ['./codigo-vincular.component.css']
})
export class CodigoVincularComponent implements OnInit {
  email: string = '';
  codigo: string = '';
  mensajeCodigoInvalido: string = '';
  deviceName: string = '';
  deviceModel: string = '';

  constructor(
    private route: ActivatedRoute,
    private verificarCodigoService: VerificarCodigoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.email = this.route.snapshot.queryParamMap.get('email') || '';
    this.deviceName = this.route.snapshot.queryParamMap.get('deviceName') || '';
    this.deviceModel = this.route.snapshot.queryParamMap.get('deviceModel') || '';
    console.log('Email:', this.email);
    console.log('Device Name:', this.deviceName);
    console.log('Device Model:', this.deviceModel);
  }

  verificarCodigo(): void {
    if (!this.codigo) {
      this.mensajeCodigoInvalido = 'Ingrese un código válido.';
      return;
    }

    this.verificarCodigoService.verificarCodigo(this.email, this.codigo).subscribe({
      next: (response: string) => {
        if (response.includes('Código de verificación válido')) {
          this.router.navigate(['/dispositivo-vinculado'], { queryParams: { email: this.email, deviceName: this.deviceName, deviceModel: this.deviceModel } });
        } else {
          this.mensajeCodigoInvalido = 'Código de verificación inválido.';
        }
      },
      error: (error) => {
        console.error('Error en la solicitud:', error);
        this.mensajeCodigoInvalido = 'Ocurrió un error al verificar el código.';
      }
    });
  }
}
