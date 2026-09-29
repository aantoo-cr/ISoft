import { Component } from '@angular/core';
import { ThemeService } from '../../service/theme.service';


@Component({
  selector: 'app-header',
  standalone: true,
  imports: [],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  // Estado para manejar el menú de notificaciones y perfil
  isNotificationsMenuOpen: boolean = false;
  isProfileMenuOpen: boolean = false;

  // Cantidad de notificaciones no leídas (este valor puede venir del backend)
  unreadNotifications: number = 13;

  constructor(private themeService: ThemeService) {}

  // Métodos para manejar la apertura y cierre del menú de notificaciones y perfil
  toggleNotificationsMenu() {
    this.isNotificationsMenuOpen = !this.isNotificationsMenuOpen;
  }

  closeNotificationsMenu() {
    this.isNotificationsMenuOpen = false;
  }

  toggleProfileMenu() {
    this.isProfileMenuOpen = !this.isProfileMenuOpen;
  }

  closeProfileMenu() {
    this.isProfileMenuOpen = false;
  }

  // Método para cambiar entre tema claro y oscuro usando el servicio
  toggleTheme() {
    console.log('Toggle theme clicked');
    this.themeService.toggleTheme();
  }
  

  // Método para verificar si el modo oscuro está activo
  isDarkMode(): boolean {
    return this.themeService.isDarkMode();
  }

  // Método para alternar la visibilidad del sidebar (puede ser usado por el contenedor padre)
  toggleSidebar() {
    // Lógica para el sidebar (si es necesario)
  }
}
