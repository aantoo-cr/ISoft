import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProjectsService } from '../../service/projects.service';
import { ProjectFormStructure } from '../../domain/project-form.structure';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FooterComponent } from '../../../shared/components/footer/footer.component';
import { HeaderComponent } from '../../../shared/components/header/header.component';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-project-form',
  standalone: true,
  imports: [
    FooterComponent,
    HeaderComponent,
    ReactiveFormsModule,
    CommonModule,
  ],
  templateUrl: './project-form.component.html',
  styleUrl: './project-form.component.css',
})
export class ProjectFormComponent implements OnInit {
  teamId!: number; // ID del equipo
  projectForm!: FormGroup; // Formulario reactivo

  constructor(
    private route: ActivatedRoute,
    private projectsService: ProjectsService,
    private fb: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Obtener el teamId desde la URL

    this.teamId = Number(this.route.snapshot.paramMap.get('id'));

    // Inicializar el formulario
    this.projectForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      description: ['', [Validators.required, Validators.minLength(10)]],
      repositoryUrl: [
        '',
        [Validators.required, Validators.pattern(/https?:\/\/.+/)],
      ],
    });
  }

  // Método para enviar el formulario
  onSubmit(): void {
    if (this.projectForm.invalid) {
      this.projectForm.markAllAsTouched(); // Marca los campos como tocados para mostrar errores
      return;
    }

    // Crear el objeto ProjectFormStructure
    const projectData: ProjectFormStructure = {
      ...this.projectForm.value,
      teamId: this.teamId, // Asignar el teamId
    };

    // Llamar al servicio para enviar el proyecto
    this.projectsService.createProject(projectData).subscribe({
      next: (projectId) => {
        console.log('Proyecto creado con éxito. ID:', projectId);
        this.router.navigate([`/teams/projects/update-photo/${projectId}`]); // Redirigir tras la creación
      },
      error: (err) => {
        console.error('Error al crear el proyecto:', err);
      },
    });
  }
}
