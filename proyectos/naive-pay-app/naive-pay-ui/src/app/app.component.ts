import {Component, inject} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {FundsComponent} from "./funds/components/funds/funds.component";
import { CommonModule } from '@angular/common';
import {HttpClient, HttpClientModule} from "@angular/common/http";  // Importa CommonModule


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, FundsComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'naive-pay-ui';
  http = inject(HttpClient)

}
