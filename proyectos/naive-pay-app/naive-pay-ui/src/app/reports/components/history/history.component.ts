import { Component, OnInit } from '@angular/core';
import { BankAccount } from '../../domain/bank-account';
import { FiltroComponent } from '../filtro/filtro.component';
import { ExportPdfComponent } from '../export-pdf/export-pdf.component';
import { HistoryService } from '../../service/history/history.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Transaction } from '../../domain/transaction';

@Component({
  selector: 'app-history',
  standalone: true,
  imports: [CommonModule, FiltroComponent, ExportPdfComponent],
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css'],
})
export class HistoryComponent implements OnInit {
  transactions: Transaction[] = [];
  filteredTransactions: Transaction[] = [];
  currentFilters: { startDate: string; endDate: string; minAmount: number; maxAmount: number; estadoFiltro: string} | null = null;
  isAuthModalOpen = false;
  bankAccount: BankAccount | null = null;
  accountHolder: string | null = null;
  rut: string | null = null;
  showFilter: boolean = false;
 // acountholder: string = '4594896662'; // Cambiado de const a miembro de clase

  constructor(private historyService: HistoryService, private router: Router) {}

  toggleFilterInputs(): void {
    this.showFilter = !this.showFilter;
  }

  ngOnInit(): void {
    this.loadRut();
    if (!this.rut) {
      console.error('No se ha iniciado sesión');
      this.router.navigate(['/login']);
      return;
    }
    this.loadBankAccountAndTransactions(this.rut);
  }

  loadTransactions(): void {
    if (this.bankAccount) {
      this.historyService.getAllTransaction(this.bankAccount.accountNumber).subscribe((transactions: Transaction[]) => {
        
        this.transactions = transactions;
        this.filteredTransactions = [...this.transactions];
        this.currentFilters = null; // No hay filtros aplicados
      });
    } else {
      console.error('No se ha cargado la cuenta bancaria');
    }
  }

  public loadRut() {
    this.rut = localStorage.getItem('user_rut');
    console.log('RUT del usuario:', this.rut);
  }

  private loadBankAccountAndTransactions(rut: string | null) {
    this.historyService.getBankAccount(rut).subscribe((bankAccount) => {
      this.bankAccount = bankAccount;
      this.accountHolder = bankAccount.accountNumber;
      this.loadTransactions();
      console.log('Número de cuenta cargado:', this.accountHolder);
    });
  }

  aplicarFiltro(filter: { startDate: string; endDate: string; minAmount: number; maxAmount: number; estadoFiltro: string}) {
    this.currentFilters = filter;
    if (this.bankAccount) {
      this.historyService.getFilteredTransactions(this.bankAccount.accountNumber, filter.startDate, filter.endDate, filter.minAmount, filter.maxAmount, filter.estadoFiltro).subscribe((transactions: Transaction[]) => {
        this.transactions = transactions;
        this.filteredTransactions = [...this.transactions];
      });
    } else {
      console.error('No se ha cargado la cuenta bancaria');
    }
  }
}