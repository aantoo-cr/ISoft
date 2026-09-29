import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'ui';
  products: any[] = [];
  showPopup: boolean = false;
  errorMessage: string = '';

  showErrorPopup(message: string) {
    this.errorMessage = message;
    this.showPopup = true;
  }

  resetPopup() {
    this.showPopup = false;
    this.errorMessage = '';
  }
}
