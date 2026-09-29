import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TeamsService } from '../../service/teams.service';
import { ProjectsService } from '../../service/projects.service';
import { TeamInfoStructure } from '../../domain/team-info.structure';
import { ProjectCardStructure } from '../../domain/project-card.structure';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from '../../../shared/components/header/header.component';
import { FooterComponent } from '../../../shared/components/footer/footer.component';
@Component({
  selector: 'app-details-team',
  standalone: true,
  imports: [CommonModule,HeaderComponent,FooterComponent],
  templateUrl: './details-team.component.html',
  styleUrl: './details-team.component.css',
})
export class DetailsTeamComponent implements OnInit {
  teamId!: number; // ID del equipo
  teamInfo!: TeamInfoStructure; // Información del equipo
  projectInfo!: ProjectCardStructure | null; // Información del proyecto asociado (puede ser null)

  loading: boolean = true; // Indicador de carga
  error: string | null = null; // Mensaje de error

  constructor(
    private route: ActivatedRoute,
    private teamsService: TeamsService,
    private projectsService: ProjectsService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Obtener el ID del equipo de la URL usando snapshot.paramMap
    this.teamId = Number(this.route.snapshot.paramMap.get('id'));

    if (!this.teamId) {
      console.error('Error: No se encontró un ID válido en la URL');
      this.error = 'No se pudo obtener el ID del equipo.';
      this.loading = false;
      return;
    }

    console.log('Team ID recibido:', this.teamId);
    this.fetchTeamAndProjectDetails(); // Obtener datos del equipo y el proyecto
  }

  // Método para obtener datos del equipo y su proyecto
  fetchTeamAndProjectDetails(): void {
    this.loading = true;
    this.error = null;

    // Obtener información del equipo
    this.teamsService.getTeamInfo(this.teamId).subscribe({
      next: (team) => {
        this.teamInfo = team;
        console.log('Información del equipo:', team);

        // Obtener información del proyecto asociado
        this.projectsService.getProjectForTeam(this.teamId).subscribe({
          next: (project) => {
            this.projectInfo = project;
            console.log('Información del proyecto:', project);
            this.loading = false;
          },
          error: (err) => {
            console.error('Error al obtener el proyecto:', err);
            this.projectInfo = null; // No hay proyecto asociado
            this.loading = false;
          },
        });
      },
      error: (err) => {
        console.error('Error al obtener información del equipo:', err);
        this.error = 'Error al cargar la información del equipo.';
        this.loading = false;
      },
    });
  }

  // Método para redirigir a la página de detalles del proyecto
  navigateToProjectDetails(projectId: number | undefined): void {
    if (projectId) {
      this.router.navigate([`teams/projects/details/${projectId}`]);
    }
  }
}
