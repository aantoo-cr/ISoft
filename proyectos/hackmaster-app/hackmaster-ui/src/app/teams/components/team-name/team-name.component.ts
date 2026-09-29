import { Component } from '@angular/core';

@Component({
  selector: 'app-team-name',
  standalone: true,
  imports: [],
  templateUrl: './team-name.component.html',
  styleUrl: './team-name.component.css'
})
export class TeamNameComponent {
  nameTeam: string = 'Equipo 1';
}
