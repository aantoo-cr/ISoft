import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './shared/components/homepage/homepage.component';
import { SignInComponent } from './registration/components/sign-in/sign-in.component';
import { Error404Component } from './shared/components/error404/error404.component';
import { OrganizationComponent } from './registration/components/registration/select/organization/organization.component';
import { ParticipantComponent } from './registration/components/registration/select/participant/participant.component';
import { SelectComponent } from './registration/components/registration/select/select.component';
import { HomeComponent } from './registration/components/home/home.component';
import { NgModule } from '@angular/core';
import { AdminComponent } from './registration/components/admin/admin.component';
import { RequestsListComponent } from './registration/components/admin/requests-list/requests-list.component';
import { AuthGuard } from './registration/guards/auth.guard';
import { EditUserComponent } from './registration/components/edit-user/edit-user.component';
import { RequestResetPasswordComponent } from './registration/components/request-reset-password/request-reset-password.component';
import { ResetPasswordComponent } from './registration/components/reset-password/reset-password.component';
import { EditOrganizationComponent } from './registration/components/edit-organization/edit-organization.component';
import { CommunicationComponent } from './communication/components/communication/communication.component';
import { ForumListComponent } from './communication/components/communication/forum-list/forum-list.component';
import { ForumCreateComponent } from './communication/components/communication/forum-create/forum-create.component';
import { ForumDetailComponent } from './communication/components/communication/forum-detail/forum-detail.component';
import { AvanceComponent } from './tracking/components/Avance/avance.component';
import { ChallengesHomeComponent } from './challenges/pages/challenges-home/challenges-home.component';
import { ChallengesDetailComponent } from './challenges/pages/challenges-detail/challenges-detail.component';
import { PrizeListComponent } from './rec_management/components/prize-list/prize-list.component';
import { MyTeamsPageComponent } from './teams/components/my-teams-page/my-teams-page.component';
import { ProjectFormComponent } from './teams/components/project-form/project-form.component';
import { TeamFormComponent } from './teams/components/team-form/team-form.component';
import { TeamsPageComponent } from './teams/components/teams-page/teams-page.component';
import {ReportsComponent} from "./reports/components/reports/reports.component";
import {Cu1Component} from "./reports/components/cu1/cu1.component";
import {Cu2Component} from "./reports/components/cu2/cu2.component";
import {Cu3Component} from "./reports/components/cu3/cu3.component";
import {Cu4Component} from "./reports/components/cu4/cu4.component";
import {Cu5Component} from "./reports/components/cu5/cu5.component";
import {Cu6Component} from "./reports/components/cu6/cu6.component";
import {Cu7Component} from "./reports/components/cu7/cu7.component";
import {Cu8Component} from "./reports/components/cu8/cu8.component";
import { DetailsTeamComponent } from './teams/components/details-team/details-team.component';
import { UpdatePhotoComponent } from './teams/components/update-photo/update-photo.component';
import { DetailsProjectCompleteComponent } from './teams/components/details-project-complete/details-project-complete.component';

export const routes: Routes = [
  { path: '', component: HomepageComponent },
  { path: 'login', component: SignInComponent },
  { path: 'signup', component: SelectComponent },
  { path: 'organization', component: OrganizationComponent },
  { path: 'participant', component: ParticipantComponent },
  { path: 'edit-user/:email', component: EditUserComponent },
  { path: 'edit-organization/:email', component: EditOrganizationComponent },
  { path: 'admin', component: AdminComponent },
  { path: 'list', component: RequestsListComponent },
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'request-password-reset', component: RequestResetPasswordComponent },
  { path: 'reset-password', component: ResetPasswordComponent },
  { path: 'chats', component: CommunicationComponent },
  { path: 'foros', component: ForumListComponent },
  { path: 'foro/crear-topico', component: ForumCreateComponent },
  { path: 'foros/tema/:slug', component: ForumDetailComponent },
  { path: 'avance', component: AvanceComponent },
  { path: 'desafios', component: ChallengesHomeComponent },
  { path: 'desafio/:challengeId', component: ChallengesDetailComponent },
  { path: 'challenges/:challengeId/prizes', component: PrizeListComponent },
  {path: 'teams/info/:id', component: DetailsTeamComponent},
  {path: 'teams/project-form/:id', component: ProjectFormComponent},
  {path: 'teams', component: TeamsPageComponent},
  {path: 'teams/my-teams', component: MyTeamsPageComponent},
  {path: 'teams/projects/update-photo/:id', component: UpdatePhotoComponent},
  {path: 'teams/projects/details/:id', component: DetailsProjectCompleteComponent},
  {path: 'teams/form', component: TeamFormComponent},
  {
    path: 'reports',
    component: ReportsComponent,
    children: [
      { path: 'cu1', component: Cu1Component},
      { path: 'cu2/:actorId', component: Cu2Component},
      { path: 'cu3/:actorId/:hackatonId', component: Cu3Component},
      { path: 'cu4/', component: Cu4Component},
      { path: 'cu5/:actorId/:hackatonId', component: Cu5Component},
      { path: 'cu6/:actorId', component: Cu6Component},
      { path: 'cu7:actorId/:hackatonId', component: Cu7Component},
      { path: 'cu8', component: Cu8Component}]
  },
  { path: '**', component: Error404Component },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
