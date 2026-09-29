import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Organization } from '../../../../domain/Organization';
import { OrganizationService } from '../../../../service/organization.service';

@Component({
  selector: 'app-organization',
  standalone: true,
  imports: [],
  templateUrl: './organization.component.html',
  styleUrl: './organization.component.css',
})
export class OrganizationComponent {
  constructor(
    private organizationService: OrganizationService,
    private router: Router,
  ) {}

  onSelectBack() {
    this.router.navigate(['/signup']);
  }

  addOrganization(organization: Organization): void {
    this.organizationService.addOrganization(organization).subscribe({
      next: (newOrganization: Organization) => {
        console.log(
          '[OrganizationComponent] Organizador guardado exitosamente:',
          newOrganization,
        );
      },
      error: (error) => {
        console.error(
          '[OrganizationComponent] Error al agregar participante: ',
          error,
        );
      },
    });
  }

  organization: Organization = {
    username: '',
    password: '',
    email: '',
    socialNameOrganization: '',
    rutOrganization: '',
    rutRepresentative: '',
    representativeName: '',
  };

  cleanInputs() {
    this.organization = {
      username: '',
      password: '',
      email: '',
      socialNameOrganization: '',
      rutOrganization: '',
      rutRepresentative: '',
      representativeName: '',
    };
  }

  onChange(event: Event) {
    const input = event.target as HTMLInputElement;
    const value = input.value;
    const key = input.name as keyof Organization;

    if (key === 'id') {
      this.organization[key] = Number(value);
    } else {
      this.organization[key] = value;
    }
  }

  onSubmit(event: Event) {
    event.preventDefault();
    const newOrganization = {
      ...this.organization,
    };
    this.addOrganization(newOrganization);
  }
}
