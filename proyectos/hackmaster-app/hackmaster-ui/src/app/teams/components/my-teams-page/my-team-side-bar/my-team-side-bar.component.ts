import { Component, EventEmitter, Output } from '@angular/core';
import { SidebarComponent } from "../../commons/side-bar/side-bar.component";
import { faChalkboardUser } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faProjectDiagram } from '@fortawesome/free-solid-svg-icons';
import { faUser } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-my-team-side-bar',
  standalone: true,
  templateUrl: './my-team-side-bar.component.html',
  styleUrl: './my-team-side-bar.component.css',
  imports: [SidebarComponent, FontAwesomeModule],
})
export class MyTeamSideBarComponent {
  faChalkboardUser = faChalkboardUser;
  faProjectDiagram = faProjectDiagram;
  faUser = faUser;

  @Output() createTeamClick = new EventEmitter<void>();
  @Output() profileClick = new EventEmitter<void>();
  @Output() challengeClick = new EventEmitter<void>();

  onCreateTeamClick(): void {
    this.createTeamClick.emit();
  }

  onProfileClick(): void {
    this.profileClick.emit();
  }

  onChallengeClick(): void {
    this.challengeClick.emit();
  }
}
