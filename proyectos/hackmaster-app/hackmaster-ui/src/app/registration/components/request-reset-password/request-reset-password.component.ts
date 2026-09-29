import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { PasswordResetService } from '../../service/password-reset.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-request-reset-password',
  standalone: true,
  imports: [CommonModule, FormsModule], // Agregar FormsModule aquí
  templateUrl: './request-reset-password.component.html',
  styleUrls: ['./request-reset-password.component.css'],
})
export class RequestResetPasswordComponent {
  email = '';
  message = '';
  errorMessage = '';

  constructor(
    private passwordResetService: PasswordResetService,
    private router: Router,
  ) {}

  requestPasswordReset() {
    this.passwordResetService.requestPasswordReset(this.email).subscribe({
      next: () => {
        this.message = 'Solicitud enviada. Revisa tu correo.';
        this.errorMessage = '';
        this.router.navigate(['/reset-password']);
      },
      error: (err) => {
        console.error('Error al enviar la solicitud:', err);
        this.errorMessage = 'No se pudo procesar la solicitud.';
        this.message = '';
      },
    });
  }

  onSelectBack() {
    this.router.navigate(['/login']);
  }
}
