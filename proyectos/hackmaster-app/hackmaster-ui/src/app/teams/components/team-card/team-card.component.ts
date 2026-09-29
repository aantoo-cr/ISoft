import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TeamCardStructure } from '../../domain/team-card.structure';
import { Router } from '@angular/router';

@Component({
  selector: 'app-team-card',
  standalone: true,
  templateUrl: './team-card.component.html',
  styleUrl: './team-card.component.css',
})
export class TeamCardComponent {
  @Input() team!: TeamCardStructure;

  constructor(private router: Router) {}

  onTeamCardClick(teamId: number) {
    this.router.navigate([`teams/info/${teamId}`]);
  }
}
