import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-president-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class PresidentFormComponent {
  rut: string = '';
  presidente: string = '';
  error: string = '';
  loading: boolean = false;

  constructor(private http: HttpClient) {}

  onSubmit() {
    if (!this.rut) return;

    this.loading = true;
    this.error = '';
    this.presidente = '';

    // Llamar a la API (ajusta la URL según tu endpoint)
    this.http.get<any>(`/api/empresas/${this.rut}/presidente`)
      .subscribe({
        next: (response) => {
          this.presidente = response.nombre;
          this.loading = false;
        },
        error: (error) => {
          this.error = error.error?.message || 'Error al consultar el presidente';
          this.loading = false;
        }
      });
  }
}
