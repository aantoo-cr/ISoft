import { Component, OnInit } from '@angular/core';
import { Transaction } from '../../domain/transaction';
import {BankAccount} from "../../domain/bank-account";
import {GetServiceService} from "../../service/get-service.service";
import {HttpClientModule} from "@angular/common/http";
import {AuthModalComponent} from "../auth-modal/auth-modal.component";
import {DecimalPipe, NgIf} from "@angular/common";
import {Router} from "@angular/router";

@Component({
  selector: 'app-payments',
  standalone: true,
    imports: [HttpClientModule, AuthModalComponent, NgIf, DecimalPipe],
    providers: [GetServiceService],
  templateUrl: './payments.component.html',
  styleUrl: './payments.component.css'
})
export class PaymentsComponent {
    pendingTransactions: Transaction[] = [];
    Transaction: Transaction | null = null;
    isAuthModalOpen = false;
    bankAccount: BankAccount | null = null;
    rut: string | null = null;

    constructor(private getService: GetServiceService, private router: Router) { }

    ngOnInit(): void {
        this.loadRut();
        if (!this.rut) {
            console.error('No se ha iniciado sesión');
            this.router.navigate(['/login']);
            return;
        }
        this.loadBankAccountAndTransactions(this.rut);
    }

    public loadRut() {
        this.rut = localStorage.getItem('user_rut');
        console.log('RUT del usuario:', this.rut);
    }

    private loadBankAccountAndTransactions(rut: string | null) {
        this.getService.getBankAccount(rut).subscribe((bankAccount) => {
            this.bankAccount = bankAccount;
            console.log('Número de cuenta:', this.bankAccount?.accountNumber);
            this.loadPendingTransactions();
        });
    }

    public loadPendingTransactions() {
        if (!this.bankAccount?.accountNumber) {
            console.error('No se ha recuperado el numero de cuenta bancaria');
            this.router.navigate(['/login']);
            return;
        }

        this.getService.getPendingTransactions(this.bankAccount.accountNumber).subscribe((transactions) => {
            this.pendingTransactions = transactions;
            console.log(this.pendingTransactions);
        });
    }

    openAuthModal(transaction: Transaction) {
        this.Transaction = transaction; // Asigna la transacción seleccionada
        this.isAuthModalOpen = true;
    }

    closeAuthModal() {
        this.isAuthModalOpen = false;
    }
}
