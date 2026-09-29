import { Component, OnInit } from '@angular/core';
import { CU4Service, JudgeResponse, JudgeHackathon } from '../../service/cu4.service';
import { CommonModule } from '@angular/common';
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-cu4',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './cu4.component.html',
  styleUrls: ['./cu4.component.css']
})
export class Cu4Component implements OnInit {
  hackatones: JudgeHackathon[] = [];
  actorName: string = '';
  actorType: string = '';
  errorMessage: string = '';
  actorId: number | null = null; // Variable local para guardar el actorId


  constructor(private cu4Service: CU4Service) { }

  ngOnInit(): void {
    const email = localStorage.getItem('email'); // Obtén el email del localStorage
    const actorType = localStorage.getItem('userType'); // Obtén el tipo de actor del localStorage

    if (email && actorType) {
      this.loadHackathonsJudge(email, actorType);
    } else {
      this.errorMessage = 'No se pudo obtener la información del usuario.';
    }

  }


  loadHackathonsJudge(email: string, actorType: string): void {
    this.cu4Service.getActorIdByEmailAndType(email, actorType).subscribe({
      next: (actorId: number) => {
        if (actorId) {
          this.actorId = actorId; // Guarda el actorId en la variable local
          this.loadHackathons(actorId); // Llama a la función para obtener las métricas con el actorId
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

  loadHackathons(judgeId: number): void {

    this.cu4Service.getHackathonsByJudge(judgeId).subscribe({
      next: (response: JudgeResponse) => {
        this.actorName = response.actorName;
        this.actorType = response.actorType;
        this.hackatones = response.hackatones;
      },
      error: (err) => {
        this.errorMessage = 'No se pudieron obtener los hackatones del juez. Intenta nuevamente más tarde.';
        console.error(err);
      }
    });
  }


}
