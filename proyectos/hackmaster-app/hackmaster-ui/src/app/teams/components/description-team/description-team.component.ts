import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-description-team',
  standalone: true,
  imports: [],
  templateUrl: './description-team.component.html',
  styleUrl: './description-team.component.css'
})
export class DescriptionTeamComponent {
  // Definimos una descripción provisional en el objeto team
  @Input() description!:string;
}
