import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

export interface CollaborationDetail {
  collaborationsCount: number;
  collaboratorName: string;
}

export interface ParticipantDetail {
  actorType: string;
  numberOfVictories: number;
  totalHackatonesParticipados: number;
  collaborators: CollaborationDetail[];
  actorName: string;
}

@Injectable({
  providedIn: 'root'
})
export class CU8Service {
  private apiUrl = 'http://localhost:8080/api/reports'; // URL base, igual que en CU1Service

  constructor(private http: HttpClient) {}

  // Método para obtener el actorId de manera dinámica
  getActorIdByEmailAndType(email: string, actorType: string): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/getId/${email}/${actorType}`);
  }

  // Método para obtener los detalles del participante
  getParticipantDetails(actorId: number): Observable<ParticipantDetail> {
    return this.http.get<ParticipantDetail>(`${this.apiUrl}/participante/detalles/${actorId}`).pipe(
      catchError((error) => {
        console.error('Error al obtener los detalles del participante', error);
        // Valor por defecto en caso de error
        return of({
          actorType: '',
          numberOfVictories: 0,
          totalHackatonesParticipados: 0,
          collaborators: [],
          actorName: ''
        });
      })
    );
  }
}
