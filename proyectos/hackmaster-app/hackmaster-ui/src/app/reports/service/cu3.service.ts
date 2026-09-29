// src/app/service/cu3.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

export interface HackathonDetail {
  numberOfRegistrations: number;
  actorType: string;
  membersCountPerTeam: number[];
  actorName: string;
  hackatonName: string;
  totalRevenue: number;
  numberOfTeams: number;
}

export interface cu3Chart{

}

@Injectable({
  providedIn: 'root'
})

export class CU3Service {

  private apiUrl = 'http://localhost:8080/api/reports/organizador/hackaton';

  constructor(private http: HttpClient) { }

  getHackathonDetails(actorId: string | null, hackathonId: string | null): Observable<HackathonDetail> {
    return this.http.get<HackathonDetail>(`${this.apiUrl}/${actorId}/${hackathonId}`).pipe(
      catchError((error) => {
        console.error('Error al obtener los detalles del hackathon', error);
        return of({
          numberOfRegistrations: 0,
          actorType: '',
          membersCountPerTeam: [],
          actorName: '',
          hackatonName: '',
          totalRevenue: 0,
          numberOfTeams: 0
        }) // Retorna null en caso de error
      })
    );
  }


}
