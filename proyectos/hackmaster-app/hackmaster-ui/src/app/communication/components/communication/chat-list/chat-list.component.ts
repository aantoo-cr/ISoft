import { Component, OnDestroy } from '@angular/core';
import { ChatsService } from '../../../service/chats.service';
import { ChatStructure } from '../../../domain/ChatStructure';
import { UserStructure } from '../../../../shared/domain/UserStructure';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { interval, Subscription } from 'rxjs';

@Component({
  selector: 'app-chat-list',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './chat-list.component.html',
  styleUrls: ['./chat-list.component.css']
})
export class ChatListComponent implements OnDestroy {

  public chatsList: ChatStructure[] = [];
  public selectedChatUser: ChatStructure | null = null;
  public selectedChatId: number = 0;
  public newMessage: string = '';
  private messageSubscription: Subscription | null = null;
  public currentUser: UserStructure | null = null;

  constructor(private chatsService: ChatsService) {
    this.chatsService.getCurrectUser().subscribe({
      next: (user) => {
        this.currentUser = user;
        this.loadUserChats();
      },
      error: (error) => {
        console.error('Error fetching current user', error);
      }
    });
  }

  private loadUserChats(): void {
    if (this.currentUser) {
      this.chatsService.getUsersChats(this.currentUser.id).subscribe({
        next: (data) => {
          this.chatsList = data;
          console.log('Chats fetched', this.chatsList);
        },
        error: (error) => {
          console.error('Error fetching chatsList', error);
        }
      });
    }
  }

  public selectChat(chatUser: ChatStructure): void {
    if (this.currentUser) {
      this.selectedChatUser = chatUser;
      console.log('Selected chat', chatUser);

      if (this.messageSubscription) {
        this.messageSubscription.unsubscribe();
      }
      this.messageSubscription = interval(5000).subscribe(() => {
        if (this.currentUser) {
          this.chatsService.pollChatMessages(chatUser.id, this.currentUser, 5000).subscribe({
            next: (chat) => {
              this.selectedChatUser!.messages = chat.messages;
              this.selectedChatUser!.users = chat.users;
              this.selectedChatId = chat.id;
              console.log('Chat actualizado: id:', chat.id, "messages:", chat.messages);
            },
            error: (error) => {
              console.error('Error al actualizar mensajes', error);
            }
          });
        }
      });

      this.chatsService.getChatMessages(chatUser.id, this.currentUser).subscribe({
        next: (chat) => {
          this.selectedChatUser!.messages = chat.messages;
          this.selectedChatUser!.users = chat.users;
          this.selectedChatId = chat.id;
          console.log('Chat fetched: id: ',chat.id,"ChatName: ",chat.name, "messages: ", chat.messages,"users: " ,chat.users);
        },
        error: (error) => {
          console.error('Error fetching messages', error);
        }
      });
    }
  }

  public sendMessage(): void {
    if (this.selectedChatUser && this.newMessage.trim() && this.currentUser) {
      console.log('Sending message:', this.newMessage, 'to chat:', this.selectedChatId, 'from user:', this.currentUser.id);
      this.chatsService.sendMessageToBD(
                  this.selectedChatId,
                  this.currentUser.id,
                  this.newMessage)
          .subscribe({
          next: (newMsg) => {
            this.selectedChatUser!.messages.push(newMsg);
            this.newMessage = '';
          },
          error: (err) => {
            console.error('Error al enviar el mensaje:', err);
          },
          complete: () => {
            console.log('Mensaje enviado exitosamente');
          }
      });
    }
  }

  public ngOnDestroy(): void {
    if (this.messageSubscription) {
      this.messageSubscription.unsubscribe();
    }
  }
}
