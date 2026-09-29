// members-page.component.ts
import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-members-page',
  standalone: true,
  imports: [],
  templateUrl: './members-page.component.html',
  styleUrls: ['./members-page.component.css'] // Corrige "styleUrl" a "styleUrls"
})
export class MembersPageComponent {
  @Output() close = new EventEmitter<void>(); // Evento para notificar el cierre

  closeComponent() {
    this.close.emit(); // Emite el evento cuando se presiona la "X"
  }
}
