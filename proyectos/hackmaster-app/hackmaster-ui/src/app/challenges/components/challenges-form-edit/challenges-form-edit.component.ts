import { Component, Input, Output, EventEmitter } from '@angular/core';
import { ChallengesService } from '../../service/challenges.service';
import { ChallengeDTO } from '../../domain/challenge.model';
import {ActivatedRoute} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-challenges-form-edit',
  templateUrl: './challenges-form-edit.component.html',
  styleUrls: ['./challenges-form-edit.component.css'],
  imports: [
    FormsModule,
    NgIf,
    NgForOf
  ],
  standalone: true
})
export class ChallengesFormEditComponent {
  @Input()challenge: ChallengeDTO = new ChallengeDTO('', '', '', new Date(), new Date(), 1,'',[] as string[],0)
  nuevoCriterio: string = '';
  @Output() close = new EventEmitter<void>();
  challengeId!: number;

  constructor(private route: ActivatedRoute, private challengesService: ChallengesService) {}

  // Método para agregar un nuevo criterio
  agregarCriterio(): void {
    // Agrega un criterio solo si hay menos de 5 y el nuevo criterio no está vacío
    if (this.nuevoCriterio.trim() && this.challenge.criteriosEval.length < 5) {
      this.challenge.criteriosEval.push(this.nuevoCriterio.trim());
      this.nuevoCriterio = ''; // Limpiamos el campo de entrada
    }
  }

  eliminarCriterio(index: number): void {
    // Elimina el criterio en el índice especificado
    this.challenge.criteriosEval.splice(index, 1);
  }

  onSubmit(): void {
    this.challengeId = Number(this.route.snapshot.paramMap.get('challengeId'));
    if (this.challenge.nombre &&
      this.challenge.descripcion &&
      this.challenge.requisitos_Participantes &&
      this.challenge.cuposParticipantes &&
      this.challenge.fechaI &&
      this.challenge.fechaT &&
      this.challenge.criteriosEval.length > 0 &&
      this.challenge.challengePrice) {
      this.challengesService.updateChallenge(this.challengeId, this.challenge).subscribe({
        next: (response) => {
          window.alert('Desafío guardado exitosamente');
          this.closeModal();
          window.location.reload();
        },
        error: (error) => {
          console.error('Error al editar el desafío:', error);
          console.log(error)
          console.log(this.challenge)
        },
      });
      this.closeModal();
    }else {
      // Ventana emergente cuando hay campos vacíos
      window.alert('Todos los campos deben ser llenados correctamente');
    }
  }

  closeModal(): void {
    this.close.emit();
  }
}
