import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';  // Importa RouterModule
import { HeaderComponent } from '../../../shared/components/header/header.component'; // Ajusta la ruta según sea necesario
import { SidebarComponent } from '../../../shared/components/sidebar/sidebar.component'; // Ajusta la ruta segun sea necesario

@Component({
  selector: 'app-gestion-dispositivos',
  standalone: true,
  imports: [RouterModule, HeaderComponent, SidebarComponent],
  templateUrl: './gestion-dispositivos.component.html',
  styleUrls: ['./gestion-dispositivos.component.css']
})
export class GestionDispositivosComponent {
  constructor(private router: Router) {}

  goToIngresarDispositivo(): void {
    this.router.navigate(['/ingresar-dispositivo']);
  }

  goToDesvincularDispositivo(): void {  // Nuevo método
    this.router.navigate(['/desvincular-dispositivo']);
  }
}


