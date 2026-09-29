import {Component} from '@angular/core';
import {
  ApexAxisChartSeries,
  ApexChart,
  ApexNonAxisChartSeries,
  ApexTitleSubtitle,
  ApexXAxis,
  ChartComponent
} from "ng-apexcharts";
import {SearchTransactionsService} from "../../service/search-transactions.service";
import {ChartsDataDto} from "../../domain/report-chart-data";

export type ChartOptions = {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  xaxis: ApexXAxis;
  title: ApexTitleSubtitle;
};

export type ChartOptions2 = {
  series: ApexNonAxisChartSeries;
  chart: ApexChart;
  title: ApexTitleSubtitle;
  labels: string[];
};

@Component({
  selector: 'app-charts',
  standalone: true,
  imports: [ChartComponent],
  templateUrl: './charts.component.html',
  styleUrls: ['./charts.component.css']
})
export class ChartsComponent {
  chartsData!: ChartsDataDto;
  chartOptions1!: Partial<ChartOptions>;
  chartOptions2!: Partial<ChartOptions>;
  chartOptions5!: Partial<ChartOptions2>;
  userId: string | null = '';

  constructor(private reportsService: SearchTransactionsService) {
  }

  ngOnInit(): void {
      this.userId = localStorage.getItem('user_rut');
      this.reportsService.getChartsData(this.userId).subscribe((data: ChartsDataDto) => {
          this.chartsData = data;
          console.log(data);
          console.log("Charts data: " + this.chartsData);
          console.log("user id: " + this.userId);
          this.setupCharts(this.chartsData);
      });
  }

    private setupCharts(data: ChartsDataDto) {
    this.chartOptions1 = {
      series: [
        {
          name: "Transacciones",
          data: data.transactionsByDateChartDto.counts
        }
      ],
      chart: {
        height: 350,
        type: "line",
        toolbar: {show: false},
        zoom: {enabled: false},
      },
      title: {
        text: "Transacciones por fecha"
      },
      xaxis: {
        categories: data.transactionsByDateChartDto.dates
      }
    };

    this.chartOptions2 = {
      series: [
        {
          name: "Monto",
          data: data.transactionAmountsByDateChartDto.amounts
        }
      ],
      chart: {
        height: 350,
        type: "bar",
        toolbar: {show: false},
      },
      title: {
        text: "Montos por fecha"
      },
      xaxis: {
        categories: data.transactionAmountsByDateChartDto.dates
      }
    };

    this.chartOptions5 = {
      series: data.transactionsByStatusChartDto.counts,
      chart: {
        type: 'pie',
        height: 350
      },
      title: {
        text: 'Transacciones por estado'
      },
      labels: data.transactionsByStatusChartDto.statuses
    };
  }
}
