import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from '../../../shared/components/header/header.component';
import { SidebarComponent } from '../../../shared/components/sidebar/sidebar.component';
import { DesvincularCodigoService } from '../../service/desvincular-codigo.service'; // Ajusta la ruta según tu estructura de carpetas

@Component({
  selector: 'app-codigo-desvincular',
  standalone: true,
  imports: [CommonModule, FormsModule, HeaderComponent, SidebarComponent],
  templateUrl: './codigo-desvincular.component.html',
  styleUrls: ['./codigo-desvincular.component.css']
})
export class CodigoDesvincularComponent implements OnInit {
  email: string = '';
  codigo: string = '';
  mensajeCodigoInvalido: string = '';
  deviceName: string = '';
  deviceModel: string = '';

  constructor(
    private route: ActivatedRoute,
    private desvincularCodigoService: DesvincularCodigoService,
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

    this.desvincularCodigoService.verificarCodigo(this.email, this.codigo).subscribe({
      next: (response: string) => {
        if (response.includes('Dispositivo desvinculado con éxito')) {
          this.router.navigate(['/dispositivo-desvinculado'], { queryParams: { email: this.email, deviceName: this.deviceName, deviceModel: this.deviceModel } });
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
