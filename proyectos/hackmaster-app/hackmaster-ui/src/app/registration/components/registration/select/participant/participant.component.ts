import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { Participant } from '../../../../domain/Participant';
import { ParticipantService } from '../../../../service/participant.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-participant',
  standalone: true,
  imports: [RouterLink, CommonModule],
  templateUrl: './participant.component.html',
  styleUrl: './participant.component.css',
})
export class ParticipantComponent {
  participants: Participant[] = [];

  constructor(
    private participantService: ParticipantService,
    private router: Router,
  ) {}

  addParticipant(participant: Participant): void {
    this.participantService.addParticipant(participant).subscribe({
      next: (newParticipant: Participant) => {
        console.log('Participante guardado exitosamente :', newParticipant);
      },
      error: (error) => {
        console.error('Error al agregar participante: ', error);
      },
    });
  }

  onSelectBack() {
    this.router.navigate(['/select']);
  }

  participant: Participant = {
    id: '',
    username: '',
    password: '',
    email: '',
    rut: '',
    name: '',
    lastname: '',
    city: '',
    country: '',
    phoneNumber: '',
  };

  cleanInputs() {
    this.participant = {
      id: '',
      email: '',
      username: '',
      password: '',
      rut: '',
      name: '',
      lastname: '',
      city: '',
      country: '',
      phoneNumber: '',
    };
  }

  onChange(event: Event) {
    const input = event.target as HTMLInputElement;
    const { name, value } = input;
    const key = name as keyof typeof this.participant;

    if (key === 'phoneNumber') {
      this.participant[key] = Number(value) as any;
    } else {
      this.participant[key] = value as any;
    }
  }

  onSubmit(event: Event) {
    event.preventDefault();
    const newParticipant = { ...this.participant };
    this.addParticipant(newParticipant);
    this.cleanInputs();
  }
}
