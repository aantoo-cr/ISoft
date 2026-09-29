import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-side-bar-button4',
  standalone: true,
  imports: [],
  templateUrl: './side-bar-button4.component.html',
  styleUrls: ['./side-bar-button4.component.css'] // Corrección de "styleUrl" a "styleUrls"
})
export class SideBarButton4Component {
  constructor(private router: Router) {}

  navigateToTeams() {
    this.router.navigate(['/teams']); // Navega a la ruta '/teams'
  }
}
