import { Component, EventEmitter, Output } from '@angular/core';
import { faX } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@Component({
  selector: 'app-x-button',
  standalone: true,
  imports: [FontAwesomeModule],
  templateUrl: './x-button.component.html',
  styleUrl: './x-button.component.css'
})
export class XButtonComponent {
  faX = faX;
  @Output() onClick = new EventEmitter<void>();

  emitClick() {
    this.onClick.emit();
  }
}
