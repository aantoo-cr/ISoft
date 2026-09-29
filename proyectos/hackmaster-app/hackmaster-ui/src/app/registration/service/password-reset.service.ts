import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PasswordResetService {
  private resetRequestUrl =
    'http://localhost:8080/api/password-reset/reset-request';
  private resetPasswordUrl = 'http://localhost:8080/api/password-reset/reset';

  constructor(private http: HttpClient) {}

  requestPasswordReset(email: string): Observable<any> {
    return this.http.post(this.resetRequestUrl, { email });
  }

  resetPassword(
    email: string,
    token: string,
    newPassword: string,
  ): Observable<any> {
    const body = { email, token, newPassword };
    return this.http.post(this.resetPasswordUrl, body, {
      responseType: 'text',
    });
  }
}
