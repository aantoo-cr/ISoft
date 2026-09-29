import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VerificarCodigoService {
  private apiUrl: string = 'http://localhost:8080/vinculacion/verificar-codigo';

  constructor(private http: HttpClient) {}

  verificarCodigo(email: string, codigo: string): Observable<string> {
    return this.http.get<string>(`${this.apiUrl}?email=${email}&codigo=${codigo}`, { responseType: 'text' as 'json' });
  }
}
