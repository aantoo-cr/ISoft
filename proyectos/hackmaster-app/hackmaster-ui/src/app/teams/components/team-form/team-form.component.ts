import { TeamsService } from './../../service/teams.service';
import { Component, Input, OnInit } from '@angular/core';
import { TeamFormStructure } from '../../domain/team-form.structure';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { HeaderComponent } from '../../../shared/components/header/header.component';
import { FooterComponent } from '../../../shared/components/footer/footer.component';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ProjectsService } from '../../service/projects.service';
import { MembersFormComponent } from "../members-form/members-form.component";
import { MemberStructure } from '../../domain/member-structure';
import { ParticipantService } from '../../../registration/service/participant.service';
import { Participant } from '../../../registration/domain/Participant';

@Component({
  selector: 'app-team-form',
  standalone: true,
  imports: [
    HeaderComponent,
    ReactiveFormsModule,
    FooterComponent,
    CommonModule,
    MembersFormComponent
],
  templateUrl: './team-form.component.html',
  styleUrl: './team-form.component.css',
})
export class TeamFormComponent implements OnInit {
  leaderId!: number;

  teamForm!: FormGroup;
  memberNameForm!: FormGroup;
  memberEmailForm!: FormGroup;
  conflictError: boolean = false;
  teamId!: number;

  showMemberNameForm: boolean = false;
  showMemberEmailForm: boolean = false;

  members: MemberStructure[] = [];


  constructor(private router: Router, private teamsService: TeamsService, private projectsService: ProjectsService, private participantService: ParticipantService) {}

  //Necesario porque Users en el token devuelve el email y no el id;
  //mientras que teams, en el controller, requiere el id.

  ngOnInit(): void {
    const userEmail = localStorage.getItem('email');

    if (userEmail) {
      this.participantService.getUserByEmail(userEmail).subscribe({
        next: (user: Participant) => {
          this.leaderId = Number(user.id);
        },
        error: (err: any) => {
          console.error('Error al obtener el usuario:', err);
          this.router.navigate(['/login']);
        }
      });
    }

    this.teamForm = new FormGroup({
      teamName: new FormControl('', [
        Validators.required,
        Validators.pattern('^(?!\\s*$).+'),
        Validators.pattern('^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*$'),
      ]),
    });

    this.memberNameForm = new FormGroup({
      memberName: new FormControl('', [
        Validators.required,
        Validators.pattern('^(?!\\s*$).+'),
        Validators.pattern('^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*$'),
      ]),
    });

    this.memberEmailForm = new FormGroup({
      memberEmail: new FormControl('', [
        Validators.required,
        Validators.email,
      ]),
    });
  }

  onSubmit(): void {
    this.conflictError = false;

    if (this.teamForm.invalid) {
      this.teamForm.markAllAsTouched();
      return;
    }

    const newTeam: TeamFormStructure = {
      name: this.teamForm.value.teamName!,
      leaderId: this.leaderId
    };
    this.createTeam(newTeam);
  }

  createTeam(team: TeamFormStructure): void {
    this.teamsService.createTeam(team).subscribe({
      next: (teamId) => {
        console.log('Equipo creado exitosamente');
        this.teamId = teamId;
        this.router.navigate([`teams/project-form/${teamId}`]);
      },
      error: (error) => {
        if (error.status === 409) {
          this.conflictError = true;
          console.error('Error: El equipo ya existe en la base de datos');
        } else {
          console.error('Error al crear el equipo:', error);
        }
      },
    });
  }

  get teamName() {
    return this.teamForm.get('teamName');
  }

  getErrorClasses(control: AbstractControl | null) {
    if (!control || !(control instanceof FormControl)) return;

    const hasError = control.touched && control.errors;
    return {
      'no-margin': hasError,
      'error-input': hasError
    };
  }

  addMember(): void {
    const memberName = this.memberNameForm.value.memberName;
    const memberEmail = this.memberEmailForm.value.memberEmail;

    if (memberName && memberEmail) {
      this.members.push({ name: memberName, email: memberEmail });
      this.memberNameForm.reset();
      this.memberEmailForm.reset();
      console.log(this.members);
    }
  }

  openMemberNameForm(): void {
    this.showMemberNameForm = true;
    this.showMemberEmailForm = false;
  }

  closeMemberNameForm(): void {
    this.showMemberNameForm = false;
    this.showMemberEmailForm = true;
  }

  closeMemberEmailForm(): void {
    this.showMemberEmailForm = false;
    this.addMember();
  }
}
