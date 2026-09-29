import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../domain/user.model";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class GetDataService {
    private apiUrl = 'http://localhost:8080'; // URL base desde environment

    constructor(private http: HttpClient) {}

    getUserByRut(rut: string): Observable<User> {
        const url = `${this.apiUrl}/api/mostrar/${rut}`;
        return this.http.get<any>(url).pipe(
            map((response) => new User(response)) // Transforma la respuesta en un User
        );
    }

    getAllUsers(): Observable<User[]> {
        const url = `${this.apiUrl}/api/mostrar`;
        return this.http.get<any[]>(url).pipe(
            map((response) => {
                // Filtrar los usuarios con rol "TITULAR"
                return response.filter(user => user.role !== 'ADMIN')
                    .map(user => new User(user)); // Mapea los usuarios filtrados a DTOs
            })
        );
    }
}
