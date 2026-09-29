import { Component } from '@angular/core';
import {SearchBarComponent} from "./shared/search-bar/search-bar.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [SearchBarComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'housing-ui';
}
