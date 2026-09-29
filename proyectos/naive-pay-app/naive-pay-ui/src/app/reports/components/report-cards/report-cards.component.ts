import {Component} from '@angular/core';
import {SearchTransactionsService} from "../../service/search-transactions.service";
import {ReportsMetrics} from "../../domain/reports-metrics";
import {DecimalPipe} from "@angular/common";

@Component({
  selector: 'app-report-cards',
  standalone: true,
  imports: [
    DecimalPipe
  ],
  templateUrl: './report-cards.component.html',
  styleUrl: './report-cards.component.css'
})
export class ReportCardsComponent {
  metrics!: ReportsMetrics;
  userId: string | null = '';

  constructor(private searchTransactionsService: SearchTransactionsService) {
  }

    ngOnInit(): void {
        this.userId = localStorage.getItem('user_rut');
        this.loadMetrics();
        console.log("user id: " + this.userId)
    }

    public loadMetrics() {
    this.searchTransactionsService.getMetrics(this.userId).subscribe((metrics) => {
      this.metrics = metrics;
      console.log(this.metrics);
    });
  }

}
