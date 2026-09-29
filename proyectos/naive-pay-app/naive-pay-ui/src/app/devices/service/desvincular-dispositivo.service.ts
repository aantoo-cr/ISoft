import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DesvincularDispositivoService {
  private apiUrl: string = 'http://localhost:8080/desvinculacion/enviar-codigo';

  constructor(private http: HttpClient) {}

  enviarCodigo(email: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}?email=${email}`, { responseType: 'json' });
  }
}
