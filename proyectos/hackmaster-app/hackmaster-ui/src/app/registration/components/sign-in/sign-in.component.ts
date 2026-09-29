import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../service/auth.service';

@Component({
  selector: 'app-sign-in',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css'],
})
export class SignInComponent {
  email = '';
  password = '';
  userType = 'participant';
  errorMessage = '';
  passwordVisible = false;

  constructor(
    private authService: AuthService,
    private router: Router,
  ) {}

  onEmailChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.email = input.value;
  }

  onPasswordChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.password = input.value;
  }

  onUserTypeChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.userType = input.value;
  }

  login(): void {
    console.log('Intentando iniciar sesión con:', this.email, this.userType);
    this.authService.login(this.email, this.password, this.userType).subscribe(
      (success) => {
        if (success) {
          console.log('Login exitoso');
          this.router.navigate(['/desafios']);
        } else {
          this.errorMessage =
            this.userType === 'organization'
              ? 'Cuenta de organización no aprobada'
              : 'Email o contraseña incorrectos';
        }
      },
      (error) => {
        console.error('Error en el login:', error);
        this.errorMessage = 'Error de conexión o servidor';
      },
    );
  }

  toggleUserType(): void {
    this.userType =
      this.userType === 'participant' ? 'organization' : 'participant';
  }

  togglePasswordVisibility(): void {
    this.passwordVisible = !this.passwordVisible;
  }

  onSelectBack() {
    this.router.navigate(['/']);
  }

  onSelectForgotPassword() {
    this.router.navigate(['/request-password-reset']);
  }

  onRegisterNewAccount() {
    this.router.navigate(['/signup']);
  }
}
