import { Component } from '@angular/core';
import {FundsComponent} from "../../../funds/components/funds/funds.component";
import {RouterLink, RouterModule, RouterOutlet} from "@angular/router";
import {PaymentsComponent} from "../../../payments/components/pendingTransactions/payments.component";
import { CommonModule } from '@angular/common';
import {FormRegistrationComponent} from "../../../registration/components/form-registration/form-registration.component";

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterModule, RouterLink, RouterOutlet,
      PaymentsComponent,PaymentsComponent, FundsComponent],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent {
  isSideMenuOpen: boolean = false;
  isPagesMenuOpen: boolean = false; // Nuevo estado para el submenú
  isReportSubmenuOpen: boolean = false;

  // Método para controlar la apertura/cierre del sidebar completo
  toggleSideMenu() {
    this.isSideMenuOpen = !this.isSideMenuOpen;
  }

  // Método para controlar la apertura/cierre del submenú "Pages"
  togglePagesMenu() {
    this.isPagesMenuOpen = !this.isPagesMenuOpen;
  }

  toggleReportSubmenu() {
    this.isReportSubmenuOpen = !this.isReportSubmenuOpen;
  }

  closeSideMenu() {
    this.isSideMenuOpen = false;
  }
  redirectToCreateAccount(){

  }
}
