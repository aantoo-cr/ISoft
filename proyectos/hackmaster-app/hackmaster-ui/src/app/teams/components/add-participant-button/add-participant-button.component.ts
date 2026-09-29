import { Component } from '@angular/core';
import { Router } from '@angular/router';
@Component({
  selector: 'app-add-participant-button',
  standalone: true,
  imports: [],
  templateUrl: './add-participant-button.component.html',
  styleUrl: './add-participant-button.component.css'
})
export class AddParticipantButtonComponent {
  constructor(private router: Router) {}

 
  navigateToOrganizer() {
    this.router.navigate(['/organizer']);
  }
}
