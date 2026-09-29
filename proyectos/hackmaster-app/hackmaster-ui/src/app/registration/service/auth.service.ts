import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private baseEndpoint = 'http://localhost:8080/auth'; // Base endpoint para autenticación
  private loggedIn = new BehaviorSubject<boolean>(this.hasToken());

  constructor(private http: HttpClient) {}

  login(
    email: string,
    password: string,
    userType: string,
  ): Observable<boolean> {
    const endpoint =
      userType === 'participant'
        ? `${this.baseEndpoint}/login/participant`
        : `${this.baseEndpoint}/login/organization`;

    return this.http
      .post<any>(endpoint, { email, password })
      .pipe(
        map((response) => {
          if (response.token) {
            // Guardar datos del usuario en localStorage
            localStorage.setItem('token', response.token);
            localStorage.setItem('userType', userType);
            localStorage.setItem('email', email);
            localStorage.setItem('username', response.username);
            this.loggedIn.next(true);
            return true;
          }
          return false;
        }),
      );
  }

  logout(): void {
    localStorage.clear();
    this.loggedIn.next(false);
  }

  private hasToken(): boolean {
    return !!localStorage.getItem('token');
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getUserType(): string | null {
    return localStorage.getItem('userType');
  }
}
