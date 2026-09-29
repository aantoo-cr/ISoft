import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Prize } from '../../domain/prize';
import { PrizeService } from '../../service/PrizeService';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from "@angular/common";
import { Input } from '@angular/core';
import { OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-prize-form',
  standalone: true,
  imports: [CommonModule,
    ReactiveFormsModule],
  templateUrl: './prize-form.component.html',
  styleUrl: './prize-form.component.css'
})
export class PrizeFormComponent {
  @Input() challengeId!: number;
  @Output() close = new EventEmitter<void>();

  prizeForm: FormGroup;
  successMessage: string | null = null;

  constructor(
    private formBuilder: FormBuilder,
    private prizeService: PrizeService,
    private router: Router
  ) {
    this.prizeForm = this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      valueReward: [0, [Validators.required, Validators.min(0)]]
    });
  }

  onSubmit() {
    if (this.prizeForm.valid) {
      const prizeData = this.prizeForm.value;
      this.prizeService.assignPrize(this.challengeId, prizeData).subscribe({
        next: () => {
          this.successMessage = '¡Premio guardado correctamente!';
          setTimeout(() => {
            window.location.reload();
          }, 1000);
        },
        error: () => {
          this.successMessage = 'Hubo un error al guardar el premio. Inténtalo de nuevo.';
        }
      });
    }
  }

  closeForm() {
    this.close.emit();
  }
}
