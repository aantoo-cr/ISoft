import { Component } from '@angular/core';
import { ChatListComponent } from './chat-list/chat-list.component';
import { HeaderComponent } from "../../../shared/components/header/header.component";
import { FooterComponent } from "../../../shared/components/footer/footer.component";

@Component({
  selector: 'app-communication',
  standalone: true,
  imports: [
    ChatListComponent,
    HeaderComponent,
    FooterComponent,
  ],
  templateUrl: './communication.component.html',
  styleUrls: ['./communication.component.css']
})
export class CommunicationComponent {
}
