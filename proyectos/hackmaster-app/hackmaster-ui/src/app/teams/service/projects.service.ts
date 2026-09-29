import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ProjectFormStructure } from '../domain/project-form.structure';
import { ProjectCardStructure } from '../domain/project-card.structure';
import { ProjectInfoStructure } from '../domain/project-info.structure';

@Injectable({
  providedIn: 'root',
})
export class ProjectsService {
  private readonly projectsUrl = 'http://localhost:8080/api/teams/projects';

  constructor(private httpClient: HttpClient) {}

  createProject(project: ProjectFormStructure): Observable<number> {
    return this.httpClient.post<number>(this.projectsUrl, project);
  }

  getProjects(): Observable<ProjectCardStructure[]> {
    return this.httpClient.get<ProjectCardStructure[]>(this.projectsUrl);
  }

  getProjectForTeam(teamId: number): Observable<ProjectInfoStructure> {
    return this.httpClient.get<ProjectInfoStructure>(
      `${this.projectsUrl}/by-team/${teamId}`
    );
  }

  getProjectPreviewForTeam(teamId: number): Observable<ProjectCardStructure> {
    return this.httpClient.get<ProjectInfoStructure>(
      `${this.projectsUrl}/preview/by-team/${teamId}`
    );
  }

  updateTeamPhoto(id: number, photo: File): Observable<void> {
    const formData = new FormData();
    formData.append('file', photo);
    return this.httpClient.put<void>(`${this.projectsUrl}/${id}/photo`, formData);
  }
}
