import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { ActorResponse, Hackaton } from '../../service/cu2.service';
import { Cu2Service } from '../../service/cu2.service';
import {ActivatedRoute, RouterLink} from '@angular/router';
import { Location } from '@angular/common';  // Importar el servicio Location

@Component({
  selector: 'app-cu2',
  standalone: true,
  imports: [CommonModule, HttpClientModule, RouterLink],
  templateUrl: './cu2.component.html',
  styleUrls: ['./cu2.component.css']
})
export class Cu2Component implements OnInit {
  hackatones: Hackaton[] = [];
  actorName: string = '';
  actorType: string = '';
  errorMessage: string = '';
  actorId: number | null = null;


  constructor(
    private cu2Service: Cu2Service,
    private location: Location,
    private route: ActivatedRoute // Inyectar ActivatedRoute
  ) {}

  ngOnInit(): void {
    const actorIdParam = this.route.snapshot.paramMap.get('actorId');
    this.actorId = actorIdParam ? Number(actorIdParam) : null;

    if (this.actorId !== null && !isNaN(this.actorId)) {
      console.log('Actor ID (number):', this.actorId);
      this.loadHackatones(actorIdParam);
    } else {
      console.error('Actor ID no válido');
    }
  }

  loadHackatones(actorId: string | null): void {
    this.cu2Service.getHackatones(actorId).subscribe({
      next: (response: ActorResponse) => {
        this.actorName = response.actorName;
        this.actorType = response.actorType;
        this.hackatones = response.hackatones; // Asigna la lista de hackatones recibida desde el servicio
      },
      error: (err) => {
        this.errorMessage = 'No se pudieron obtener los hackatones. Intenta nuevamente más tarde.';
        console.error(err);
      }
    });
  }

  goBack(): void {
    this.location.back(); // Navega hacia la página anterior
  }
}
