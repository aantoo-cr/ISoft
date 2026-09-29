import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DevicesService {
  private apiUrl = 'http://localhost:8080/devices'; // Base URL

  constructor(private http: HttpClient) {}

  // Method to fetch devices by RUT
  getDevicesByRut(rut: string): Observable<any> {
    const params = new HttpParams().set('rut', rut);
    return this.http.get(`${this.apiUrl}/by-rut`, { params }); // Fixed backtick usage
  }
}
