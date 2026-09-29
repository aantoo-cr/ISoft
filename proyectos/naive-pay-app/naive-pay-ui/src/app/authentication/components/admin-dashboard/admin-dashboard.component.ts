import { Component } from '@angular/core';
import {DatePipe, NgForOf} from "@angular/common";
import {ProfileComponent} from "../profile/profile.component";
import {GetDataService} from "../../service/get-data.service";

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
    imports: [
        NgForOf,
        ProfileComponent,
        DatePipe
    ],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent {
    data: any[] = []; // Arreglo para almacenar los datos de los usuarios

    constructor(private getDataService: GetDataService) {}


    ngOnInit(): void {
        const userRole = localStorage.getItem('user_role');
        if (userRole === 'ADMIN') {
            this.getDataService.getAllUsers().subscribe({
                next: (response) => {
                    // Si la API devuelve un solo objeto
                    if (!Array.isArray(response)) {
                        this.data = [response]; // Convierte en arreglo si es único
                    } else {
                        this.data = response; // Asigna directamente si es un arreglo
                    }
                    console.log(this.data); // Para depurar y ver qué datos llegan
                },
                error: (error) => {
                    console.error('Error:', error.error || error.message);
                },
            });
        } else {
            console.error('El usuario no tiene rol de ADMIN');
        }
    }



}
