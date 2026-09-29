import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupExampleComponent } from './shared/components/signup-example/signup-example.component';
import { MainLayoutComponent } from './shared/components/main-layout/main-layout.component';
import { PageNotFoundComponent } from './shared/components/page-not-found/page-not-found.component';
import { LoginExampleComponent } from './shared/components/login-example/login-example.component';
import { DashboardComponent } from './shared/components/dashboard/dashboard.component';
import {FundsComponent} from "./funds/components/funds/funds.component"; // se agrego la ruta para poder nevegar hasta esecomponente
import { PaymentsComponent } from "./payments/components/pendingTransactions/payments.component";
import { FormRegistrationComponent } from "./registration/components/form-registration/form-registration.component";
import { VerifyIdentityComponent } from "./registration/components/verify-identity/verify-identity.component";
import { WebKeySetupComponent } from "./registration/components/web-key-setup/web-key-setup.component";
import {FormRegistrationCompletedGuard} from "./registration/guards/form-registration-completed.guard";
// @ts-ignore
import { LoginComponent } from "./authentication/components/login/login.component";
import { ForgotPasswordComponent } from "./authentication/components/forgot-password/forgot-password.component";
import { ResetPasswordComponent } from "./authentication/components/reset-password/reset-password.component";
import { RegistrationSuccessComponent } from "./registration/components/registration-success/registration-success.component";
import { KeyDisplayComponent } from "./registration/components/key-display/key-display.component";
import { GestionDispositivosComponent } from './devices/components/gestion-dispositivos/gestion-dispositivos.component';
import { VincularDispositivoComponent } from './devices/components/vincular-dispositivo/vincular-dispositivo.component';
import { DesvincularDispositivoComponent } from './devices/components/desvincular-dispositivo/desvincular-dispositivo.component';
import { SidebarComponent } from './shared/components/sidebar/sidebar.component';
import { CodigoVincularComponent } from './devices/components/codigo-vincular/codigo-vincular.component';
import { CodigoDesvincularComponent } from './devices/components/codigo-desvincular/codigo-desvincular.component';
import { DispositivoVinculadoComponent } from './devices/components/dispositivo-vinculado/dispositivo-vinculado.component';
import { DispositivoDesvinculadoComponent } from './devices/components/dispositivo-desvinculado/dispositivo-desvinculado.component';
import { IngresarDispositivoComponent } from './devices/components/ingresar-dispositivo/ingresar-dispositivo.component';
import {LogoutComponent} from "./authentication/components/logout/logout.component";
import {ReportComponent} from "./reports/components/report/report.component";
import {HistoryComponent} from "./reports/components/history/history.component";
import {AuthGuard} from "./authentication/auth/auth.guard";
import {IdentityVerifiedGuard} from "./registration/guards/identity-verified.guard";
import {WebKeySetupGuard} from "./registration/guards/web-key-setup.guard";
import {KeyDisplayGuard} from "./registration/guards/key-display.guard";
import {AccessDeniedComponent} from "./authentication/components/access-denied/access-denied.component";
import {ProfileComponent} from "./authentication/components/profile/profile.component";
import {AdminDashboardComponent} from "./authentication/components/admin-dashboard/admin-dashboard.component";
import {UrlTokenGuard} from "./authentication/auth/url-token.guard";


export const routes: Routes = [
    {
        path: '',
        component: MainLayoutComponent,
        canActivate: [AuthGuard],
        data: { roles: ['TITULAR'] },
        children: [
            { path: 'dashboard', component: DashboardComponent },
            { path: 'error', component: PageNotFoundComponent },
            { path: '', redirectTo: '/login', pathMatch: 'full' }, // Redirige la raíz a /login
            { path: 'report', component: ReportComponent },
            { path: 'history', component: HistoryComponent },
            { path: 'funds', component: FundsComponent }, // defini la url en este caso es funds y negave hasta fundcomponent para agregarlo en html de sidebar
            { path: 'payments', component: PaymentsComponent },
            { path: 'profile', component: ProfileComponent },
        ]

        // cada ruta que tiene un canActivate : [AuthGuard] es una ruta protegida, pide bearer token
    },
    { path: 'logout', component: LogoutComponent, canActivate: [AuthGuard] },
    { path: 'funds', component: FundsComponent, canActivate: [AuthGuard] },
    { path: 'login', component: LoginComponent },
    { path: 'login-example', component: LoginExampleComponent },
    { path: 'signup', component: SignupExampleComponent },
    { path: 'reset-password', component: ResetPasswordComponent, canActivate: [UrlTokenGuard]},
    { path: 'gestionDispositivo', component: GestionDispositivosComponent, canActivate: [AuthGuard] },
    { path: 'vincular-dispositivo', component: VincularDispositivoComponent },
    { path: 'desvincular-dispositivo', component: DesvincularDispositivoComponent },
    { path: 'codigo-vincular', component: CodigoVincularComponent },
    { path: 'codigo-desvincular', component: CodigoDesvincularComponent },
    { path: 'dispositivo-vinculado', component: DispositivoVinculadoComponent, canActivate: [AuthGuard] },
    { path: 'dispositivo-desvinculado', component: DispositivoDesvinculadoComponent, canActivate: [AuthGuard] },
    { path: 'ingresar-dispositivo', component: IngresarDispositivoComponent, canActivate: [AuthGuard] },
    { path: 'form-registration', component: FormRegistrationComponent },
    { path: 'verify-identity', component: VerifyIdentityComponent, canActivate: [FormRegistrationCompletedGuard] },
    { path: 'web-key/setup', component: WebKeySetupComponent, canActivate: [IdentityVerifiedGuard] },
    { path: 'forgot-password', component:ForgotPasswordComponent},
    { path: 'display-keys', component: KeyDisplayComponent, canActivate: [WebKeySetupGuard] },
    { path: 'success', component: RegistrationSuccessComponent, canActivate: [KeyDisplayGuard] },
    { path: 'not-found', component: PageNotFoundComponent },
    { path: 'admin', component: AdminDashboardComponent, canActivate: [AuthGuard], data: { roles: ['ADMIN'] } },
    { path: 'access-denied', component: AccessDeniedComponent },
    { path: '**', component: PageNotFoundComponent }
]
