import { Component, OnInit } from '@angular/core';
import { CU7Service, HackathonParticipantDetail } from '../../service/cu7.service';
import {CommonModule, Location} from '@angular/common';
import {ActivatedRoute, RouterLink} from "@angular/router";

@Component({
  selector: 'app-cu7',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './cu7.component.html',
  styleUrls: ['./cu7.component.css']
})
export class Cu7Component implements OnInit {
  hackathonDetail: HackathonParticipantDetail | null = null;
  errorMessage: string = '';
  private actorId: number | null = null;
  private hackatonId: number | null = null;

  constructor(private cu7Service: CU7Service,
              private location: Location,
              private route: ActivatedRoute
              ) { }

  ngOnInit(): void {

    const actorIdParam = this.route.snapshot.paramMap.get('actorId');
    const hackatonIdParam = this.route.snapshot.paramMap.get('hackatonId');

    this.actorId = actorIdParam ? Number(actorIdParam) : null;
    this.hackatonId = hackatonIdParam ? Number(hackatonIdParam) : null;
    this.loadHackathonDetail(actorIdParam, hackatonIdParam); // Ejemplo con actorId 4 y hackathonId 1
  }

  loadHackathonDetail(actorId: string | null, hackathonId: string | null): void {
    this.cu7Service.getHackathonDetailsParticipant(actorId, hackathonId).subscribe({
      next: (response: HackathonParticipantDetail) => {
        this.hackathonDetail = response;
      },
      error: (err) => {
        this.errorMessage = 'No se pudieron obtener los detalles del hackathon. Intenta nuevamente más tarde.';
        console.error(err);
      }
    });
  }

  goBack(): void {
    this.location.back(); // Navega hacia la página anterior
  }
}
