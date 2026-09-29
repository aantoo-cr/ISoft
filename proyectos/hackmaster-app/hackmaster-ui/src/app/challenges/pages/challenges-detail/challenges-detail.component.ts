import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ChallengesService } from '../../service/challenges.service';
import { HeaderComponent } from '../../../shared/components/header/header.component';
import { FooterComponent } from '../../../shared/components/footer/footer.component';
import { ChallengesFormEditComponent } from '../../components/challenges-form-edit/challenges-form-edit.component';
import {Router} from "@angular/router";

@Component({
  selector: 'app-challenges-detail',
  templateUrl: './challenges-detail.component.html',
  standalone: true,
  styleUrls: ['./challenges-detail.component.css'],
  imports: [
    CommonModule,
    HeaderComponent,
    FooterComponent,
    RouterLink,
    ChallengesFormEditComponent
  ]
})
export class ChallengesDetailComponent implements OnInit {
  Challenge: any;
  isLoading: boolean = true;
  challengeId!: number;
  isDeleted: boolean = false;
  isModalOpen: boolean = false; // Para controlar el estado del modal
  openModal() {
    this.isModalOpen = true;
  }

  closeModal() {
    this.isModalOpen = false;
  }
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private challengesService: ChallengesService
  ) {
  }

  ngOnInit(): void {
    this.challengeId = Number(this.route.snapshot.paramMap.get('challengeId'));
    this.challengesService.getChallengeById(this.challengeId).subscribe(
      (data) => {
        console.log('Datos del desafío:', data);
        this.Challenge = data;
        this.isLoading = false;
      },
      (error) => {
        console.error('Error al obtener detalles del desafío', error);
        this.isLoading = false;
      }
    );
  }

  toggleEstado() {
    this.challengesService.cambiarEstado(this.Challenge.id).subscribe({
      next: (response) => {
        this.Challenge.estado = response.estado;
        console.log('Estado actualizado:', response.estado);
      },
      error: (error) => {
        console.error('Error al actualizar el estado', error);
      }
    });
  }

  deleteChallenge(): void {
    if (confirm('¿Estás seguro de que deseas eliminar este desafío?')) {
      this.challengesService.eliminarChallenge(this.challengeId).subscribe(
        () => {
          console.log('Desafío eliminado con éxito');

          // Redirigir después de 5 segundos
          setTimeout(() => {
            this.isDeleted = true;
            this.router.navigate(['/desafios']);
          }, 500);
        },
        (error) => {
          console.error('Error al eliminar el desafío', error);
        }
      );
    }
  }
}
