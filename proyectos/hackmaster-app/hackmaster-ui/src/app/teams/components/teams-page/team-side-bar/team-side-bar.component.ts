import { Component, EventEmitter, Output } from '@angular/core';
import { SidebarComponent } from "../../commons/side-bar/side-bar.component";
import { faChalkboardUser } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faProjectDiagram } from '@fortawesome/free-solid-svg-icons';
import { faUser } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-team-side-bar',
  standalone: true,
  templateUrl: './team-side-bar.component.html',
  styleUrl: './team-side-bar.component.css',
  imports: [SidebarComponent, FontAwesomeModule],
})
export class TeamSideBarComponent {
  faChalkboardUser = faChalkboardUser;
  faProjectDiagram = faProjectDiagram;
  faUser = faUser;

  @Output() myTeamsClick = new EventEmitter<void>();
  @Output() profileClick = new EventEmitter<void>();

  onMyTeamsClick(): void {
    this.myTeamsClick.emit();
  }

  onProfileClick(): void {
    this.profileClick.emit();
  }
}
