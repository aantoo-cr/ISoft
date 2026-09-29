import { Component, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';
import { Organization } from '../../domain/Organization';
import { OrganizationService } from '../../service/organization.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-edit-organization',
  standalone: true,
  imports: [FormsModule, NgIf, ReactiveFormsModule],
  templateUrl: './edit-organization.component.html',
  styleUrl: './edit-organization.component.css',
})
export class EditOrganizationComponent implements OnInit {
  user: Organization = {
    id: 0,
    username: '',
    password: '',
    email: '',
    socialNameOrganization: '',
    rutOrganization: '',
    rutRepresentative: '',
    representativeName: '',
    status: '',
  };

  password = '';
  errorMessage = '';
  constructor(
    private organizationService: OrganizationService,
    private router: Router,
    private route: ActivatedRoute,
  ) {}

  ngOnInit() {
    const email = this.route.snapshot.paramMap.get('email');
    if (email) {
      this.organizationService.getOrganizationByEmail(email).subscribe({
        next: (data) => {
          this.user = data;
        },
        error: (err) => {
          console.error('Error al obtener los datos del usuario:', err);
          this.errorMessage = 'Error al cargar los datos del usuario.';
        },
      });
    }
  }

  updateOrganization() {
    if (!this.password) {
      this.errorMessage =
        'Debes ingresar tu contraseña actual para confirmar los cambios.';
      return;
    }

    const payload = {
      ...this.user, // Incluye los datos del usuario
      password: this.password, // Incluye la contraseña
    };

    this.organizationService.updateOrganization(payload).subscribe({
      next: (response) => {
        console.log('Usuario actualizado:', response);
      },
      error: (err) => {
        console.error('Error al actualizar los datos del usuario:', err);
        this.errorMessage = 'Error al actualizar los datos del usuario.';
      },
    });
  }
}
