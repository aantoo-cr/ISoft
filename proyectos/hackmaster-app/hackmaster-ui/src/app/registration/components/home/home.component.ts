import {Component, HostListener} from '@angular/core';
import { Router } from '@angular/router';
import { BannerComponent } from '../../../shared/components/banner/banner.component';
import { FooterComponent } from '../../../shared/components/footer/footer.component';
import { HeaderComponent } from '../../../shared/components/header/header.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [BannerComponent, FooterComponent, CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent {
  constructor(private router: Router) {}
  menuOpen = false;

  toggleMenu(): void {
    this.menuOpen = !this.menuOpen;
  }

  userType = localStorage.getItem('userType');

  logOut() {
    localStorage.removeItem('token');
    this.router.navigate(['/']);
  }

  editUser() {
    const email = localStorage.getItem('email');
    if (email && this.userType === 'participant') {
      this.router.navigate(['/edit-user', email]);
    } else if (email && this.userType === 'organization') {
      this.router.navigate(['/edit-organization', email]);
    } else {
      console.error('No se encontró un email en localStorage.');
    }
  }

  resetPassword() {
    this.router.navigate(['/request-password-reset']);
  }

  // Detectar clics globales
  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent): void {
    const targetElement = event.target as HTMLElement;

    // Verificar si el clic ocurrió fuera del menú
    const isClickInsideMenu = targetElement.closest('.menu-container');
    if (!isClickInsideMenu && this.menuOpen) {
      this.menuOpen = false; // Cerrar el menú
    }
  }
}
