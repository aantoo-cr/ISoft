import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {avance } from "../domain/avance";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    Authorization: 'my-auth-token'
  })
};

@Injectable({
  providedIn: 'root'
})
export class AvanceService {

  private avanceUrl = 'http://localhost:8080/api/avances/avance';
  private avancesUrl = 'http://localhost:8080/api/avances/avances';


  constructor(private httpClient: HttpClient) {
    this.httpClient = httpClient;
  }
  obtenerAvances(): Observable<avance[]> {
    return this.httpClient.get<avance[]>(this.avancesUrl)
  }
  crearAvance(avanceDTO: avance): Observable<avance> {
    return this.httpClient.post<avance>(this.avanceUrl, avanceDTO);
  }
}
