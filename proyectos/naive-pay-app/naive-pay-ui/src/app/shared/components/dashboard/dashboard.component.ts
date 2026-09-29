import { Component } from '@angular/core';
import {ProfileComponent} from "../../../authentication/components/profile/profile.component";
import {GetDataService} from "../../../authentication/service/get-data.service";
import {DatePipe, NgForOf} from "@angular/common";

@Component({
  selector: 'app-dashboard',
  standalone: true,
    imports: [
        ProfileComponent,
        DatePipe,
        NgForOf
    ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {
    data: any[] = []; // Arreglo para almacenar los datos del usuario

    constructor(private getDataService: GetDataService) {
    }

    ngOnInit(): void {
        const rut = localStorage.getItem('user_rut');
        if (rut) {
            this.getDataService.getUserByRut(rut).subscribe({
                next: (user) => {
                    this.data = [user]; // Maneja objetos `User`
                },
                error: (error) => {
                    console.error('Error:', error.error || error.message);
                },
            });
        } else {
            console.error('No se encontró el RUT en localStorage');
        }
    }

}
