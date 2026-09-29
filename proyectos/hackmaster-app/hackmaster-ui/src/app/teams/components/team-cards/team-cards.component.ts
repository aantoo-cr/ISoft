import { Component, Input } from '@angular/core';
import { TeamCardComponent } from '../team-card/team-card.component';
import { TeamCardStructure } from '../../domain/team-card.structure';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-team-cards',
  standalone: true,
  imports: [TeamCardComponent, CommonModule],
  templateUrl: './team-cards.component.html',
  styleUrl: './team-cards.component.css',
})
export class TeamCardsComponent {
  @Input() teams: TeamCardStructure[] = [];
}
