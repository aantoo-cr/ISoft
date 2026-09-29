import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProjectsService } from '../../service/projects.service';
import { ProjectInfoStructure } from '../../domain/project-info.structure';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from '../../../shared/components/header/header.component';
import { FooterComponent } from '../../../shared/components/footer/footer.component';
@Component({
  selector: 'app-details-project-complete',
  standalone: true,
  imports: [CommonModule,FooterComponent,HeaderComponent],
  templateUrl: './details-project-complete.component.html',
  styleUrls: ['./details-project-complete.component.css'],
})
export class DetailsProjectCompleteComponent implements OnInit {
  teamId!: number;
  projectInfo!: ProjectInfoStructure;
  loading: boolean = true;
  error: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private projectsService: ProjectsService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.teamId = Number(this.route.snapshot.paramMap.get('id'));
    if (!this.teamId) {
      console.error('Error: No se encontró un ID válido en la URL');
      this.error = 'No se pudo obtener el ID del equipo.';
      this.loading = false;
      return;
    }
    this.fetchProjectDetails();
  }

  fetchProjectDetails(): void {
    this.loading = true;
    this.error = null;

    this.projectsService.getProjectForTeam(this.teamId).subscribe({
      next: (project) => {
        this.projectInfo = project;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Error al cargar los detalles del proyecto.';
        this.loading = false;
      },
    });
  }

  // Método para redirigir a la página 'Mis Equipos'
  goToMyTeams(): void {
    this.router.navigate(['teams/my-teams']);
  }
}
