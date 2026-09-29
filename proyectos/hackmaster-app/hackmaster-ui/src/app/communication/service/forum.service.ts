import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ForumStructure } from '../domain/ForumStructure';
import { ForumReplyStructure } from '../domain/ForumReplyStructure';

@Injectable({
  providedIn: 'root'
})
export class ForumService {
  private apiUrl = 'http://localhost:8080/api/forums';

  constructor(private client: HttpClient) {}

  // Obtener todos los temas del foro
  public getAllTopics(): Observable<ForumStructure[]> {
    return this.client.get<ForumStructure[]>(`${this.apiUrl}/topics`);
  }

  // Obtener un tema específico por ID
  public getTopicById(id: number): Observable<ForumStructure> {
    return this.client.get<ForumStructure>(`${this.apiUrl}/topics/id/${id}`);
  }

  // Obtener un tema específico por slug
  public getTopicBySlug(slug: string): Observable<ForumStructure> {
    return this.client.get<ForumStructure>(`${this.apiUrl}/topics/slug/${slug}`);
  }

  // Agregar una respuesta a un tema
  public addReply(topicId: number, content: string, userId: number, userType: string): Observable<ForumReplyStructure> {
    const replyPayload = { content };
    const params = {
      userId: userId.toString(),
      userType: userType
    };

    return this.client.post<ForumReplyStructure>(`${this.apiUrl}/topics/${topicId}/replies`, replyPayload, { params });
  }

  // Agregar una sub-respuesta a una respuesta existente
  public addSubReply(replyId: number, content: string, userId: number, userType: string): Observable<ForumReplyStructure> {
    const subReplyPayload = { content };
    const params = {
      userId: userId.toString(),
      userType: userType
    };

    return this.client.post<ForumReplyStructure>(`${this.apiUrl}/replies/${replyId}/subreplies`, subReplyPayload, { params });
  }

  // Método para crear un nuevo tópico
  public createTopic(topicData: { title: string; description: string }, userId: number, userType: string): Observable<ForumStructure> {
    // Configura los parámetros de la URL
    const params = new HttpParams()
      .set('userId', userId.toString())
      .set('userType', userType);

    // Envía la solicitud POST con el cuerpo y los parámetros
    return this.client.post<ForumStructure>(`${this.apiUrl}/topics`, topicData, { params });
  }
}
