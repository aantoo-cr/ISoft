import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TeamCardStructure } from '../domain/team-card.structure';
import { TeamFormStructure } from '../domain/team-form.structure';
import { TeamInfoStructure } from '../domain/team-info.structure';
import { MemberStructure } from '../domain/member-structure';

@Injectable({
  providedIn: 'root',
})
export class TeamsService {
  private readonly teamsUrl = 'http://localhost:8080/api/teams';

  constructor(private httpClient: HttpClient) {}

  createTeam(team: TeamFormStructure): Observable<number> {
    return this.httpClient.post<number>(this.teamsUrl, team);
  }

  getTeams(): Observable<TeamCardStructure[]> {
    return this.httpClient.get<TeamCardStructure[]>(this.teamsUrl);
  }

  getTeamsByKeyword(
    keyword: string,
  ): Observable<TeamCardStructure[]> {
    return this.httpClient.get<TeamCardStructure[]>(
      `${this.teamsUrl}/filter/${keyword}`
    );
  }

  getTeamsForUser(userId: number): Observable<TeamCardStructure[]> {
    return this.httpClient.get<TeamCardStructure[]>(
      `${this.teamsUrl}/by-user/${userId}`
    );
  }

  getTeamsForUserByKeyword(
    userId: number,
    keyword: string
  ): Observable<TeamCardStructure[]> {
    return this.httpClient.get<TeamCardStructure[]>(
      `${this.teamsUrl}/by-user/${userId}/filter/${keyword}`,
    );
  }

  getTeamInfo(id: number): Observable<TeamInfoStructure> {
    return this.httpClient.get<TeamInfoStructure>(`${this.teamsUrl}/${id}`);
  }

  addMember(member: MemberStructure, teamId: number): Observable<void> {
    const payload = {
      teamId: teamId,
      member: member
    };

    return this.httpClient.post<void>(`${this.teamsUrl}/add-member`, payload);
  }
}
