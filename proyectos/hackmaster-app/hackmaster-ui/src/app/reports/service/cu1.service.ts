import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface HackathonMetric {
  hackatonName: string;
  ingresosGenerados: number;
  totalPremios: number;
  totalInscripciones: number;
}

export interface OrganizerMetricsResponse {
  actorName: string;
  actorType: string;
  hackatones: HackathonMetric[];
}

@Injectable({
  providedIn: 'root'
})
export class CU1Service {

  private apiUrl = 'http://localhost:8080/api/reports'; // Asegúrate de que esta URL sea correcta

  constructor(private http: HttpClient) {}

  // Método para obtener el actorId de manera dinámica
  getActorIdByEmailAndType(email: string, actorType: string): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/getId/${email}/${actorType}`);
  }

  // Método para obtener las métricas del organizador
  getOrganizerMetrics(actorId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/organizador/metricas/${actorId}`);
  }
}
