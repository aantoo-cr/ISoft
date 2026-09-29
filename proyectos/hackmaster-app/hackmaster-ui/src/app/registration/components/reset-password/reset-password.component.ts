import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PasswordResetService } from '../../service/password-reset.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-reset-password',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css'],
})
export class ResetPasswordComponent {
  token = '';
  newPassword = '';
  message = '';
  errorMessage = '';

  constructor(
    private passwordResetService: PasswordResetService,
    private router: Router,
  ) {}

  resetPassword() {
    const email = localStorage.getItem('email');
    if (!email) {
      this.errorMessage =
        'No se encontró el correo electrónico en el almacenamiento local.';
      return;
    }

    this.passwordResetService
      .resetPassword(email, this.token, this.newPassword)
      .subscribe({
        next: () => {
          this.message = 'Contraseña cambiada exitosamente.';
          this.errorMessage = '';
          this.router.navigate(['/home']);
        },
        error: (err) => {
          console.error('Error al cambiar la contraseña:', err);
          this.errorMessage =
            'Error al cambiar la contraseña. Verifica el token e inténtalo nuevamente.';
          this.message = '';
        },
      });
  }

  onSelectBack() {
    this.router.navigate(['/login']);
  }
}
