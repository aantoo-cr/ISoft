import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ChatStructure } from '../domain/ChatStructure';
import { UserStructure } from '../../shared/domain/UserStructure';
import { MessageStructure } from '../domain/MessageStructure';
import { ParticipantService } from '../../registration/service/participant.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ChatsService {

  private apiChatUrl = 'http://localhost:8080/api/chats';

  constructor(private client: HttpClient, private participatService: ParticipantService, private router: Router) { }

  public getCurrectUser(): Observable<UserStructure> {
    const email = localStorage.getItem('email');
    const token = localStorage.getItem('token');

    if (!email || !token) {
      this.router.navigate(['login']);
      throw new Error('No hay usuario logueado');
    }
    
    return this.participatService.getUserByEmail(email).pipe(
      map(user => {
        return new UserStructure(Number(user.id), user.name);
      })
    );
  }

  public getUsersChats(userId: number): Observable<ChatStructure[]> {
    return this.client.get<any[]>(`${this.apiChatUrl}/${userId}/list`).pipe(
      map(chats => chats.map(chat => new ChatStructure(
        chat.id,
        chat.name,
        chat.avatar,
        chat.users = [],
        chat.messages = []
        ))
      )
    );
  }

  public getChatMessages(chatId: number, user: UserStructure): Observable<ChatStructure> {
    return this.client.post<any>(`${this.apiChatUrl}/${chatId}/join/${user.id}`, "").pipe(
      map(chat => new ChatStructure(
        chat.id,
        chat.name,
        chat.avatar,
        chat.users.map((user: any) => new UserStructure(user.id, user.name)),
        chat.messages.map((message: any) => new MessageStructure(
          message.id,
          message.content,
          message.timestamp,
          new UserStructure(message.sender.id, message.sender.name)
        ))
      ))
    );
  }

  public sendMessageToBD(chatId: number, userIdSender: number, content: string): Observable<MessageStructure> {
    if (!content || content.trim().length === 0) {
      throw new Error("El mensaje no puede estar vacío");
    }
    return this.client.post<any>(`${this.apiChatUrl}/${chatId}/messages/${userIdSender}`, content).pipe(
      map(message => new MessageStructure(
        message.id,
        message.content,
        message.timestamp,
        new UserStructure(
          message.sender.id,
          message.sender.name
        )
      ))
    );
  }

  public pollChatMessages(chatId: number, user: UserStructure, intervalMs: number): Observable<ChatStructure> {
    return this.client.post<any>(`${this.apiChatUrl}/${chatId}/join/${user.id}`, "").pipe(
      map(chat => new ChatStructure(
        chat.id,
        chat.name,
        chat.avatar,
        chat.users.map((user: any) => new UserStructure(user.id, user.name)),
        chat.messages.map((message: any) => new MessageStructure(
          message.id,
          message.content,
          message.timestamp,
          new UserStructure(message.sender.id, message.sender.name)
        ))
      ))
    );
  }
}
