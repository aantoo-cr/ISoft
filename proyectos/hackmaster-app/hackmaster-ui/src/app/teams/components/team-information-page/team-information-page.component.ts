import { Component } from '@angular/core';
import { DescriptionTeamComponent } from '../description-team/description-team.component';
import { TeamParticipantsComponent } from '../team-participants/team-participants.component';
import { SidebarComponent } from '../commons/side-bar/side-bar.component';
import { TeamNameComponent } from '../team-name/team-name.component';
import { HeaderComponent } from '../../../shared/components/header/header.component';
import { AddParticipantButtonComponent } from '../add-participant-button/add-participant-button.component';
import { ActivatedRoute } from '@angular/router';
import { TeamsService } from '../../service/teams.service';
import { TeamInfoStructure } from '../../domain/team-info.structure';
import { CommonModule } from '@angular/common';
import { SideBarButton3Component } from './side-bar-button3/side-bar-button3.component';
import { SideBarButton4Component } from './side-bar-button4/side-bar-button4.component';
import { FooterComponent } from '../../../shared/components/footer/footer.component';
import { MembersPageComponent } from '../members-page/members-page.component';
@Component({
  selector: 'app-team-information-page',
  standalone: true,
  imports: [DescriptionTeamComponent, TeamParticipantsComponent, SidebarComponent, TeamNameComponent,HeaderComponent,AddParticipantButtonComponent,CommonModule,SideBarButton3Component,SideBarButton4Component,MembersPageComponent,FooterComponent],
  templateUrl: './team-information-page.component.html',
  styleUrl: './team-information-page.component.css'
})
export class TeamInformationPageComponent {

  teamId!: number;
  teamInfo!:TeamInfoStructure;
  isAdmin!:boolean;
  showMembersPage = false;
  constructor(private route: ActivatedRoute,private teamsService: TeamsService) {}

  ngOnInit(): void {
    this.teamId = +this.route.snapshot.paramMap.get('id')!;
    document.body.style.overflowY = 'auto';
    document.body.style.overflowX = 'hidden';
  }

  ngOnDestroy(): void {

    document.body.style.overflow = 'auto';
  }

  loadTeamInfo(): void {
    this.teamsService.getTeamInfo(this.teamId).subscribe({
      next: (teamInfo) => {
        this.teamInfo= teamInfo;
      },
      error: (error) => {
        console.error('Error al crear el equipo:', error);
      },
    });
  }

  showMembersPageComponent() {
    this.showMembersPage = true;
  }

}
