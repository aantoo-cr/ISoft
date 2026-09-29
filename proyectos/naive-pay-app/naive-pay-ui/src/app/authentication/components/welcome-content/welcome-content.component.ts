import { Component } from '@angular/core';
import {LoginFormComponent} from "../login-form/login-form.component";

@Component({
  selector: 'app-welcome-content',
  standalone: true,
    imports: [
        LoginFormComponent
    ],
  templateUrl: './welcome-content.component.html',
  styleUrl: './welcome-content.component.css'
})
export class WelcomeContentComponent {

}
