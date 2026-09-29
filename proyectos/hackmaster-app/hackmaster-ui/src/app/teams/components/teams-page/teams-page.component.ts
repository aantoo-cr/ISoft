import { Component, OnInit } from '@angular/core';
import { debounceTime, Subject, switchMap } from 'rxjs';
import { TeamCardStructure } from '../../domain/team-card.structure';
import { TeamsService } from '../../service/teams.service';
import { Router } from '@angular/router';
import { HeaderComponent } from "../../../shared/components/header/header.component";
import { NavBarComponent } from "../commons/nav-bar/nav-bar.component";
import { TeamCardsComponent } from "../team-cards/team-cards.component";
import { FooterComponent } from "../../../shared/components/footer/footer.component";
import { TeamSideBarComponent } from "./team-side-bar/team-side-bar.component";

@Component({
  selector: 'app-teams-page',
  standalone: true,
  imports: [HeaderComponent, NavBarComponent, TeamCardsComponent, FooterComponent, TeamSideBarComponent],
  templateUrl: './teams-page.component.html',
  styleUrl: './teams-page.component.css'
})
export class TeamsPageComponent implements OnInit {
  teams: TeamCardStructure[] = [];
  private searchSubject = new Subject<string>();

  constructor(private teamsService: TeamsService, private router: Router) {}

  ngOnInit(): void {
    if (localStorage.getItem('userType') == "participant") {
      this.router.navigate(['teams/my-teams']);
      return;
    }
    this.searchTeams();
  }

  onSearchChange(prefix: string): void {
    this.searchSubject.next(prefix);
  }

  onMyTeamsClick() {
    this.router.navigate(['teams/my-teams']);
  }

  onTeamCardClick(teamId: number) {
    this.router.navigate([`teams/info/${teamId}`]);
  }

  searchTeams(): void {
      this.teamsService
        .getTeams()
        .subscribe({
          next: (teams) => {
            this.teams = teams;
          },
          error: (error) => {
            console.error('Error al cargar los equipos:', error);
          },
        });
  }

  searchTeamsByKeyword(): void {
    this.searchSubject
      .pipe(
        debounceTime(500),
        switchMap((keyword: string) => {
          if (!keyword.trim()) {
            this.teams = [];
            return [];
          }
          return this.teamsService.getTeamsByKeyword(keyword);
        })
      )
      .subscribe(
        (data: TeamCardStructure[]) => {
          this.teams = data;
        },
        (error) => {
          console.error('Error al buscar los equipos:', error);
        }
      );
  }
}

