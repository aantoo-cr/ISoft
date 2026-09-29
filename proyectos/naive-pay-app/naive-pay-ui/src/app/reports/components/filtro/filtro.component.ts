import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-filtro',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './filtro.component.html',
  styleUrls: ['./filtro.component.css'],
})
export class FiltroComponent {
  @Output() filterApplied = new EventEmitter<any>();
  @Output() filterCleared = new EventEmitter<void>();

  fechaInicio: string = '';
  fechaFin: string = '';
  amountMin: number | null = null;
  amountMax: number | null = null;
  tipoFiltro: string = 'all';
  estadoFiltro: string = 'all';
  showDateFilter: boolean = false;
  showAmountFilter: boolean = false;

  constructor(private http: HttpClient) {}

  toggleDateInputs(): void {
    this.showDateFilter = !this.showDateFilter;
  }

  toggleAmountInputs(): void {
    this.showAmountFilter = !this.showAmountFilter;
  }

  aplicarFiltro(): void {
    const filtros = {
      startDate: this.fechaInicio || '2000-01-01', // Valor predeterminado
      endDate: this.fechaFin || '2100-01-01', // Valor predeterminado
      minAmount: this.amountMin !== null ? this.amountMin : 0,
      maxAmount: this.amountMax !== null ? this.amountMax : Number.MAX_SAFE_INTEGER,
      estadoFiltro: this.estadoFiltro !== "all" ?  this.estadoFiltro : null
    };
    console.log('Aplicando filtros:', filtros);
    this.filterApplied.emit(filtros);
  }
  clearFilters(): void {
    this.fechaInicio = '';
    this.fechaFin = '';
    this.amountMin = null;
    this.amountMax = null;
    this.estadoFiltro = 'all';
    console.log('Filtros limpiados');
    this.filterCleared.emit(); // Emitir evento para indicar que los filtros se han limpiado
    // Aquí puedes agregar la lógica para limpiar los filtros, por ejemplo, restablecer los datos filtrados
  }
}
