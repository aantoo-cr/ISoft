import {Component, EventEmitter, Output} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {ChallengesService} from "../../service/challenges.service";
import {ChallengeDTO} from "../../domain/challenge.model";
import {NgForOf} from "@angular/common";


@Component({
  selector: 'app-challenges-form-inicial',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf
  ],
  templateUrl: './challenges-form-inicial.component.html',
  styleUrl: './challenges-form-inicial.component.css'
})
export class ChallengesFormInicialComponent {
  challenge: ChallengeDTO = new ChallengeDTO(
    '',
    '',
    '',
    new Date(),
    new Date(),
    1,
    '',
    [] as string[],
    0
  )
  nuevoCriterio: string = '';
  @Output() close = new EventEmitter<void>();
  constructor(private challengesService: ChallengesService) { }
  closeModal() {
    this.close.emit();
  }

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

  onSubmit() {
    if (this.challenge.nombre &&
      this.challenge.descripcion &&
      this.challenge.requisitos_Participantes &&
      this.challenge.cuposParticipantes &&
      this.challenge.fechaI &&
      this.challenge.fechaT &&
      this.challenge.criteriosEval.length > 0 &&
      this.challenge.challengePrice ) {

      // Llama al servicio para guardar los datos
      this.challengesService.inscribirChallenge(this.challenge).subscribe({
        next: (response) => {
          window.alert('Desafío guardado exitosamente');
          this.resetForm();
          window.location.reload();
        },
        error: (error) => {
          console.error('Error al guardar el desafío:', error);
          console.log(this.challenge)
        }
      });
      this.closeModal();

    } else {
      // Ventana emergente cuando hay campos vacíos
      window.alert('Todos los campos deben ser llenados correctamente');
    }
  }


  resetForm() {
    this.challenge = new ChallengeDTO('', '', '', new Date(), new Date(), 1, '',[],0);
  }

}
