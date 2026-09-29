import { Component, OnInit } from '@angular/core';
import { CU1Service, OrganizerMetricsResponse, HackathonMetric } from '../../service/cu1.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-cu1',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './cu1.component.html',
  styleUrls: ['./cu1.component.css']
})
export class Cu1Component implements OnInit {
  actorName: string = '';
  actorType: string = '';
  hackatones: HackathonMetric[] = [];
  errorMessage: string = '';
  actorId: number | null = null; // Variable local para guardar el actorId


  constructor(private cu1Service: CU1Service) {}

  ngOnInit(): void {
    const email = localStorage.getItem('email'); // Obtén el email del localStorage
    const actorType = localStorage.getItem('userType'); // Obtén el tipo de actor del localStorage

    if (email && actorType) {
      this.loadOrganizerMetrics(email, actorType);
    } else {
      this.errorMessage = 'No se pudo obtener la información del usuario.';
    }
  }

  loadOrganizerMetrics(email: string, actorType: string): void {
    this.cu1Service.getActorIdByEmailAndType(email, actorType).subscribe({
      next: (actorId: number) => {
        if (actorId) {
          this.actorId = actorId; // Guarda el actorId en la variable local
          this.fetchMetricsForOrganizer(actorId); // Llama a la función para obtener las métricas con el actorId
        } else {
          this.errorMessage = 'ID del actor no encontrado.';
        }
      },
      error: (err) => {
        this.errorMessage = 'No se pudo obtener el ID del actor. Intenta nuevamente más tarde.';
        console.error(err);
      }
    });
  }

  fetchMetricsForOrganizer(actorId: number): void {
    this.cu1Service.getOrganizerMetrics(actorId).subscribe({
      next: (response: OrganizerMetricsResponse) => {
        this.actorType = response.actorType;
        this.actorName = response.actorName;
        this.hackatones = response.hackatones;
      },
      error: (err) => {
        this.errorMessage = 'No se pudieron obtener las métricas del organizador. Intenta nuevamente más tarde.';
        console.error(err);
      }
    });
  }

}
