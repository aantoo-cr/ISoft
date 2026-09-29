import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { InfoParticipantStructure } from '../../domain/info-participant.structure';

@Component({
  selector: 'app-team-participants',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './team-participants.component.html',
  styleUrl: './team-participants.component.css'
})
export class TeamParticipantsComponent {
  // Define el arreglo de participantes para que tenga id y nombre
  @Input() participants!: InfoParticipantStructure[];
}
