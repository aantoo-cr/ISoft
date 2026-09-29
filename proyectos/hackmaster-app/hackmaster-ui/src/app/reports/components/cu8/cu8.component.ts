import { Component, OnInit } from '@angular/core';
import { CU8Service, ParticipantDetail } from '../../service/cu8.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-cu8',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './cu8.component.html',
  styleUrls: ['./cu8.component.css']
})
export class Cu8Component implements OnInit {
  participantDetail: ParticipantDetail | null = null;
  errorMessage: string = '';
  actorId: number | null = null; // Variable local para guardar el actorId


  constructor(private cu8Service: CU8Service) {}

  ngOnInit(): void {
    const email = localStorage.getItem('email'); // Obtén el email del localStorage
    const actorType = localStorage.getItem('userType'); // Obtén el tipo de actor del localStorage

    if (email && actorType) {
      this.loadParticipantDetail(email, actorType);
    } else {
      this.errorMessage = 'No se pudo obtener la información del usuario.';
    }
  }

  loadParticipantDetail(email: string, actorType: string): void {
    // Llama al servicio para obtener el ID del actor
    this.cu8Service.getActorIdByEmailAndType(email, actorType).subscribe({
      next: (actorId: number) => {
        if (actorId) {
          this.actorId = actorId; // Guarda el actorId en la variable local
          this.fetchDetailsForParticipant(actorId); // Usa el actorId para obtener los detalles
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

  fetchDetailsForParticipant(actorId: number): void {
    // Usa el actorId para obtener los detalles del participante
    this.cu8Service.getParticipantDetails(actorId).subscribe({
      next: (response: ParticipantDetail) => {
        this.participantDetail = response;
      },
      error: (err) => {
        this.errorMessage = 'No se pudieron obtener los detalles del participante. Intenta nuevamente más tarde.';
        console.error(err);
      }
    });
  }
}
