import { Component } from '@angular/core';
import { ForumService } from '../../../service/forum.service';
import { ForumStructure } from '../../../domain/ForumStructure';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from '../../../../shared/components/header/header.component';
import { FooterComponent } from '../../../../shared/components/footer/footer.component';

@Component({
  selector: 'app-forum-list',
  templateUrl: './forum-list.component.html',
  styleUrls: ['./forum-list.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule, HeaderComponent, FooterComponent]
})
export class ForumListComponent {
  forums: ForumStructure[] = [];
  selectedForum: ForumStructure | null = null;
  newReplyContent: string = '';

  constructor(
    private forumService: ForumService,
    private router: Router
  ) {
    this.loadForumTopics();
  }

  private loadForumTopics(): void {
    this.forumService.getAllTopics().subscribe(
      (data) => {
        this.forums = data;
        console.log("Foros cargados:", data);
      },
      (error) => console.error('Error al cargar los tópicos del foro:', error)
    );
  }

  navigateToForumDetail(slug: string): void {
    this.router.navigate(['/foros', 'tema', slug]);
  }

  loadForumDetail(slug: string): void {
    this.forumService.getTopicBySlug(slug).subscribe(
      (data) => {
        console.log('Detalle del foro:', data);
        this.selectedForum = data;
      },
      (error) => console.error('Error al cargar el detalle del foro:', error)
    );
  }

  createTopic(): void {
    this.router.navigate(['/foro/crear-topico']);
  }

  addReply(): void {
    if (this.selectedForum && this.newReplyContent.trim()) {
      this.forumService.addReply(
        this.selectedForum.id,
        this.newReplyContent,
        1, // Ejemplo de userId
        'participant' // Ejemplo de userType
      ).subscribe(
        (newReply) => {
          this.selectedForum?.replies.push(newReply);
          this.newReplyContent = '';
        },
        (error) => console.error('Error al añadir la respuesta:', error)
      );
    }
  }

  clearSelectedForum(): void {
    this.selectedForum = null;
  }
}
