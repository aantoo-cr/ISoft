import {Component, OnInit} from '@angular/core';
import {GetDataService} from "../../service/get-data.service";
import {DatePipe, NgForOf} from "@angular/common";

@Component({
  selector: 'app-profile',
  standalone: true,
    imports: [
        DatePipe,
        NgForOf
    ],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {
    data: any[] = []; // Arreglo para almacenar los datos del usuario

    constructor(private getDataService: GetDataService) {}

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
