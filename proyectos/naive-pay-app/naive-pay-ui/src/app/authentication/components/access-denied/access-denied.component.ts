import { Component } from '@angular/core';
import {RouterLink} from "@angular/router";
import {AuthService} from "../../service/auth.service";

@Component({
  selector: 'app-access-denied',
  standalone: true,
    imports: [
        RouterLink
    ],
  templateUrl: './access-denied.component.html',
  styleUrl: './access-denied.component.css'
})
export class AccessDeniedComponent {
    constructor(private authService: AuthService) {}

    // Método para redirigir según el rol
    redirectHome(): void {
        this.authService.redirectHome();
    }
}
