// src/app/challenges/service/challenges.service.ts
import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {ChallengeDTO} from "../domain/challenge.model";


@Injectable({
  providedIn: 'root' // Este servicio es global, disponible en toda la aplicación
})
export class ChallengesService {
  private apiUrl = 'http://localhost:8080/api/challenges/listar';  // URL base de tu API

  constructor(private http: HttpClient) { }

  getChallenges(): Observable<any> {
    return this.http.get(this.apiUrl, { responseType: 'json' });
  }

  // Obtener un desafío por ID
  getChallengeById(id: number): Observable<any> {
    return this.http.get<any>(`http://localhost:8080/api/challenges/challenge/${id}`);
  }

  // Inscribir un nuevo desafío
  inscribirChallenge(challenge: ChallengeDTO): Observable<any> {
    return this.http.post<any>(`http://localhost:8080/api/challenges/inscribir`, challenge);
  }

  // Publicar un desafío por ID
  cambiarEstado(id: number): Observable<any> {
    return this.http.put<any>(`http://localhost:8080/api/challenges/${id}/publicar`, {});
  }

  // Editar un desafío por ID
  updateChallenge(id:number, challenge: ChallengeDTO): Observable<any> {
    return this.http.put<any>(`http://localhost:8080/api/challenges/${id}/nuevo`, challenge);
  }

  // Eliminar un desafío por ID
  eliminarChallenge(id: number): Observable<any> {
    return this.http.delete<any>(`http://localhost:8080/api/challenges/${id}`);
  }
}
