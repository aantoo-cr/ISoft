import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-error-popup',
  templateUrl: './error-popup.component.html',
  styleUrls: ['./error-popup.component.css']
})
export class ErrorPopupComponent {
  @Input() showPopup: boolean = false;
  @Input() errorMessage: string = '';
  @Output() popupClosed = new EventEmitter<void>();

  closePopup() {
    this.showPopup = false;
    this.popupClosed.emit();
  }
}
