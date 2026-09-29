import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {PrizeListComponent} from "./rec_management/components/prize-list/prize-list.component";
import {RouterEvent, RouterLink} from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'hackmaster-ui';
}
