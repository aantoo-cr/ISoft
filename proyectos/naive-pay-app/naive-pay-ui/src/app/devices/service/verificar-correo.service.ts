import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VerificarCorreoService {
  private apiUrl: string = 'http://localhost:8080/vinculacion/enviar-codigo';

  constructor(private http: HttpClient) {}

  verificarCorreo(email: string): Observable<string> {
    return this.http.get<string>(`${this.apiUrl}?email=${email}`, { responseType: 'text' as 'json' });
  }
}
