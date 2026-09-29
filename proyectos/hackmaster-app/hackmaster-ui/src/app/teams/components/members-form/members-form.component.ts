import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-members-form',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './members-form.component.html',
  styleUrl: './members-form.component.css'
})
export class MembersFormComponent {
  @Input() form!: FormGroup;
  @Input() label!: string;
  @Input() buttonText!: string;
  @Input() placeholder!: string;
  @Input() controlName!: string;
  @Input() controlId!: string;

  @Input() requiredErrorMessage!: string;
  @Input() validationErrorMessage!: string;

  @Input() validationType: 'pattern' | 'email' = 'pattern';

  @Output() formSubmitted = new EventEmitter<void>();

  ngOnInit() {
    console.log('Button Text:', this.buttonText);
  }

  onSubmit() {
    if (this.form.valid) {
      this.formSubmitted.emit();
    } else {
      this.form.markAllAsTouched();
    }
  }
}
