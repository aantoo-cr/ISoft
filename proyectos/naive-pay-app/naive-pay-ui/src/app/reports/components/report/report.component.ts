import { Component } from '@angular/core';
import {CommonModule} from "@angular/common";
import {ReportCardsComponent} from "../report-cards/report-cards.component";
import {ChartsComponent} from "../charts/charts.component";

@Component({
  selector: 'app-report',
  standalone: true,
  imports: [CommonModule, ReportCardsComponent, ChartsComponent],
  templateUrl: './report.component.html',
  styleUrl: './report.component.css'
})
export class ReportComponent {

}
