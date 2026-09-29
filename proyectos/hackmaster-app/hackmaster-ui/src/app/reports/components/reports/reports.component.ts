import { Component } from '@angular/core';
import {Router, RouterOutlet} from '@angular/router';
import {FooterComponent} from "../../../shared/components/footer/footer.component";
import {HeaderComponent} from "../../../shared/components/header/header.component";

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  standalone: true,
  imports: [
    RouterOutlet,
    FooterComponent,
    HeaderComponent
  ],
  styleUrls: ['./reports.component.css']
})
export class ReportsComponent {
  constructor(private router: Router) {}

  ngOnInit(): void {
    const token = localStorage.getItem('token');
    const userType = localStorage.getItem('userType');

    // Redirigir al login si el token no existe
    if (!token) {
      this.router.navigate(['/login']);
      return;
    }

    // Redirigir a la ruta correspondiente según el userType
    switch (userType) {
      case 'organization':
        this.router.navigate(['/reports/cu1']);
        break;
      case 'participant':
        this.router.navigate(['/reports/cu8']);
        break;
      case 'judge':
        this.router.navigate(['/reports/cu4']);
        break;
      default:
        // Opcional: redirigir a una ruta de error o predeterminada si el userType no es válido
        this.router.navigate(['/error']);
    }
  }
}
