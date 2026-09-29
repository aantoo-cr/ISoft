import { Component, OnInit } from '@angular/core';
import { CU3Service, HackathonDetail } from '../../service/cu3.service';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Location } from '@angular/common';
import {ActivatedRoute} from "@angular/router";  // Importar el servicio Location

@Component({
  selector: 'app-cu3',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './cu3.component.html',
  styleUrls: ['./cu3.component.css']
})
export class Cu3Component implements OnInit {
  hackathonDetail: HackathonDetail | null = null;
  errorMessage: string = '';
  actorId: number | null = null;
  hackatonId: number | null = null;

  constructor(
    private cu3Service: CU3Service,
    private location: Location,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {

    const actorIdParam = this.route.snapshot.paramMap.get('actorId');
    const hackatonIdParam = this.route.snapshot.paramMap.get('hackatonId');

    this.actorId = actorIdParam ? Number(actorIdParam) : null;
    this.hackatonId = hackatonIdParam ? Number(hackatonIdParam) : null;

    if (this.actorId !== null && !isNaN(this.actorId)) {
      console.log('Actor ID:', this.actorId);
    } else {
      console.error('Actor ID no válido');
    }

    if (this.hackatonId !== null && !isNaN(this.hackatonId)) {
      console.log('Hackathon ID:', this.hackatonId);
    } else {
      console.error('Hackathon ID no válido');
    }

    if (this.hackatonId !== null && !isNaN(this.hackatonId)) {
      console.log('Hackathon ID:', this.hackatonId);
      this.loadHackathonDetails(actorIdParam, hackatonIdParam); // Usamos los ids de ejemplo 1 para actorId y hackathonId
    } else {
      console.error('Hackathon ID no válido');
    }

  }

  loadHackathonDetails(actorId: string | null, hackathonId: string | null): void {
    this.cu3Service.getHackathonDetails(actorId, hackathonId).subscribe({
      next: (response: HackathonDetail | null) => {
        if (response) {
          this.hackathonDetail = response;
        } else {
          this.errorMessage = 'No se pudo obtener la información del hackathon. Intenta nuevamente más tarde.';
        }
      },
      error: (err) => {
        this.errorMessage = 'Ocurrió un error al cargar los detalles del hackathon.';
        console.error(err);
      }
    });
  }

  // Función para volver a la página anterior
  goBack(): void {
    this.location.back();  // Navega hacia la página anterior
  }
}
