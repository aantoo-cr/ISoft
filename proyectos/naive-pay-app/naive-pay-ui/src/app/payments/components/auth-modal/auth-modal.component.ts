import { Component, Output, EventEmitter, Input } from '@angular/core';
import { Transaction } from '../../domain/transaction';
import { GetServiceService } from "../../service/get-service.service";
import {NgClass, NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {DiffieHellmanService} from "../../../registration/service/diffie-hellman.service";

@Component({
    selector: 'app-auth-modal',
    standalone: true,
    imports: [
        NgIf,
        FormsModule,
        NgClass
    ],
    templateUrl: './auth-modal.component.html',
    styleUrl: './auth-modal.component.css'
})
export class AuthModalComponent {
    @Input() transaction: Transaction | null = null;
    @Output() closeModal: EventEmitter<void> = new EventEmitter<void>();
    privateKey: string = ''; // esta es la que deberia ingresar en el html
    publicKey: string = ''; // esta es la que deberia recibir de la api
    isFeedbackVisible: boolean = false;
    feedbackMessage: string = '';

    constructor(private getService: GetServiceService, private diffieHellmanService: DiffieHellmanService) {
    }

    ngOnInit() {
        this.loadCredential();
    }

    onClose() {
        this.showCredential();
        this.closeModal.emit();
    }

    approveTransaction() {
        if (!this.validateDiffieHellmanKeys()) {
            console.error('Las claves no son válidas');
            return;
        }
        this.showCredential();
        this.updateTransactionState('APPROVED', '¡La transacción ha sido aprobada correctamente!');
    }

    rejectTransaction() {
        if (!this.validateDiffieHellmanKeys()) {
            console.error('Las claves no son válidas');
            return;
        }
        this.showCredential();
        this.updateTransactionState('REJECTED', 'La transacción ha sido rechazada correctamente.');
    }

    private updateTransactionState(newState: string, feedback: string) {
        if (!this.transaction) return;
        this.getService.updateTransactionState(this.transaction.token, newState)
            .subscribe(
                () => {
                    this.feedbackMessage = feedback;
                    this.isFeedbackVisible = true;

                    // Ocultar el feedback después de 3 segundos y cerrar el modal
                    setTimeout(() => {
                        this.isFeedbackVisible = false;
                        this.onClose();
                    }, 3000);
                },
                (error: any) => console.error('Error updating transaction:', error)
            );
    }

    private loadCredential() {
        const rut = this.transaction?.originAccount?.rutAccountHolder?.rut;
        this.getService.getCredentials(rut).subscribe((response) => {
            console.log(response);
            this.publicKey = response.public;
        });
    }

    private showCredential() {
        console.log('Public Key: ', this.publicKey);
    }

    private validateDiffieHellmanKeys(): boolean {
        if (!this.privateKey || !this.publicKey) {
            console.error('Faltan claves para la validación');
            return false;
        }
        return this.diffieHellmanService.verifyKeyPair(this.publicKey, this.privateKey);
    }
}
