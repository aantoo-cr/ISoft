import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AddDeviceService {


  constructor(private http: HttpClient) {}

  addDevice(email: string, deviceName: string, deviceModel: string): Observable<string> {
    const params = new HttpParams()
      .set('email', email)
      .set('deviceName', deviceName)
      .set('deviceModel', deviceModel);

    return this.http.get<string>('http://localhost:8080/vinculacion/add-device', { params });
  }
}
