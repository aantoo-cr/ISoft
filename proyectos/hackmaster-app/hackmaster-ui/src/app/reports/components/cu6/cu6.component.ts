import { Component, OnInit } from '@angular/core';
import { CU6Service, ParticipantDetailsResponse, HackathonDetail } from '../../service/cu6.service';
import { CommonModule } from '@angular/common';
import {ActivatedRoute, RouterLink} from "@angular/router";
import { Location } from '@angular/common';  // Importa Location para el servicio de navegación

@Component({
  selector: 'app-cu6',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './cu6.component.html',
  styleUrls: ['./cu6.component.css']
})
export class Cu6Component implements OnInit {
  participantName: string = '';
  actorType: string = '';
  hackatones: HackathonDetail[] = [];
  errorMessage: string = '';
  actorId: number | null = null;



  constructor(
    private cu6Service: CU6Service,
    private location: Location,
    private route: ActivatedRoute // Inyectar ActivatedRoute
  ) { }

  ngOnInit(): void {
    const actorIdParam = this.route.snapshot.paramMap.get('actorId');
    this.actorId = actorIdParam ? Number(actorIdParam) : null;

    if (this.actorId !== null && !isNaN(this.actorId)) {
      console.log('Actor ID (number):', this.actorId);

      this.loadParticipantDetails(actorIdParam);

    } else {
      console.error('Actor ID no válido');
    }

  }

  loadParticipantDetails(actorId: string | null): void {
    this.cu6Service.getParticipantDetails(actorId).subscribe({
      next: (response: ParticipantDetailsResponse) => {
        this.actorType = response.actorType;
        this.participantName = response.actorName;
        this.hackatones = response.hackatonesDetails;
      },
      error: (err) => {
        this.errorMessage = 'No se pudieron obtener los detalles del participante. Intenta nuevamente más tarde.';
        console.error(err);
      }
    });
  }

  goBack(): void {
    this.location.back();  // Navega a la página anterior
  }
}
