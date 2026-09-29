import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-selection',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './select.component.html',
  styleUrl: './select.component.css',
})
export class SelectComponent {
  constructor(private router: Router) {}

  onSelectParticipant() {
    this.router.navigate(['/participant']);
  }

  onSelectOrganization() {
    this.router.navigate(['/organization']);
  }

  onSelectBack() {
    this.router.navigate(['/']);
  }
}
