import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

export interface HackathonParticipantDetail {
  teamName: string;
  actorType: string;
  averageHackathonGrade: number;
  hackathonName: string;
  integrantesGrupo: string[];
  actorName: string;
  notaGrupo: number;
  ranking: string[];
}

@Injectable({
  providedIn: 'root'
})
export class CU7Service {

  private apiUrl = 'http://localhost:8080/api/reports/participante/hackaton';

  constructor(private http: HttpClient) { }

  getHackathonDetailsParticipant(actorId: string | null, hackathonId: string | null): Observable<HackathonParticipantDetail> {
    return this.http.get<HackathonParticipantDetail>(`${this.apiUrl}/${actorId}/${hackathonId}`).pipe(
      catchError((error) => {
        console.error('Error al obtener los detalles del hackathon para el participante', error);
        // Valor por defecto en caso de error
        return of({
          teamName: '',
          actorType: '',
          averageHackathonGrade: 0,
          hackathonName: '',
          integrantesGrupo: [],
          actorName: '',
          notaGrupo: 0,
          ranking: []
        });
      })
    );
  }
}
