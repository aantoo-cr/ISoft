import { Component, Input, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ExportPdfService } from '../../service/export-pdf/export-pdf.service';
import { BankAccount } from '../../domain/bank-account';

@Component({
  selector: 'app-export-pdf',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './export-pdf.component.html',
  styleUrls: ['./export-pdf.component.css']
})
export class ExportPdfComponent {
  @Input() filters: { startDate: string; endDate: string; minAmount: number; maxAmount: number; estadoFiltro: string } | null = null;
  @Input() accountNumber: string | null = null;

  constructor(private exportPdfService: ExportPdfService) { }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['accountNumber'] && changes['accountNumber'].currentValue) {
      console.log('Recibido accountNumber:', changes['accountNumber'].currentValue);
    }
  }
  
  exportPdf() {
    if (!this.accountNumber) {
      console.error('El número de cuenta es nulo');
      return;
    }

    const defaultFilters = {
      startDate: '2000-01-01', // Rango amplio para incluir todas las fechas
      endDate: '2100-01-01',
      minAmount: 0, // Mínimo monto posible
      maxAmount: Number.MAX_SAFE_INTEGER, // Máximo monto posible
      estadoFiltro: null // Valor predeterminado para estadoFiltro
    };

    // Usar filtros si están definidos, de lo contrario, usar valores predeterminados
    const filtersToUse = this.filters ? {
      ...this.filters,
      estadoFiltro: this.filters.estadoFiltro || null
    } : defaultFilters;

    const {startDate, endDate, minAmount, maxAmount, estadoFiltro } = filtersToUse;

    console.log('Exportando PDF con filtros:', filtersToUse);
    this.exportPdfService.exportPdf(this.accountNumber,startDate, endDate, minAmount, maxAmount, estadoFiltro);

  }
  
}

