import { Participant } from './../../../registration/domain/Participant';
import { ParticipantService } from './../../../registration/service/participant.service';
import { Component, Input, OnInit } from '@angular/core';
import { HeaderComponent } from '../../../shared/components/header/header.component';
import { FooterComponent } from '../../../shared/components/footer/footer.component';
import { TeamCardsComponent } from '../team-cards/team-cards.component';
import { NavBarComponent } from '../commons/nav-bar/nav-bar.component';
import { MyTeamSideBarComponent } from './my-team-side-bar/my-team-side-bar.component';
import { TeamCardStructure } from '../../domain/team-card.structure';
import { TeamsService } from '../../service/teams.service';
import { debounceTime, Subject, switchMap } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-my-teams-page',
  standalone: true,
  imports: [
    HeaderComponent,
    FooterComponent,
    TeamCardsComponent,
    NavBarComponent,
    MyTeamSideBarComponent,
],
  templateUrl: './my-teams-page.component.html',
  styleUrl: './my-teams-page.component.css',
})
export class MyTeamsPageComponent implements OnInit {
  myTeams: TeamCardStructure[] = [];
  private searchSubject = new Subject<string>();
  userId!: number;

  constructor(private teamsService: TeamsService, private router: Router, private participantService: ParticipantService) {}

  //Necesario porque Users en el token devuelve el email y no el id;
  //mientras que teams, en el controller, requiere el id.
  ngOnInit(): void {
    const userEmail = localStorage.getItem('email');

    if (userEmail) {
      this.participantService.getUserByEmail(userEmail).subscribe({
        next: (user: Participant) => {
          this.userId = Number(user.id);
          this.searchMyTeams();
        },
        error: (err: any) => {
          console.error('Error al obtener el usuario:', err);
          this.router.navigate(['/login']);
        }
      });
    }
  }

  onSearchChange(prefix: string): void {
    this.searchSubject.next(prefix);
  }

  onCreateTeamClick() {
    this.router.navigate(['teams/form']);
  }

  searchMyTeams(): void {
      this.teamsService
        .getTeamsForUser(this.userId)
        .subscribe({
          next: (teams) => {
            this.myTeams = teams;
            console.log(this.myTeams);
          },
          error: (error) => {
            console.error('Error al crear el equipo:', error);
          },
        });
  }

  searchTeamsByKeyword(): void {
    this.searchSubject
      .pipe(
        debounceTime(500),
        switchMap((keyword: string) => {
          if (!keyword.trim()) {
            this.myTeams = [];
            return [];
          }
          return this.teamsService.getTeamsForUserByKeyword(this.userId, keyword);
        })
      )
      .subscribe(
        (data: TeamCardStructure[]) => {
          this.myTeams = data;
        },
        (error) => {
          console.error('Error al buscar los equipos:', error);
        }
      );
  }

  onTeamCardClick(teamId: number) {
    this.router.navigate([`teams/info/${teamId}`]);
  }
}
