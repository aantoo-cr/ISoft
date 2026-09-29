import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ForumService } from '../../../service/forum.service';
import { ForumStructure } from '../../../domain/ForumStructure';
import { ForumReplyStructure } from '../../../domain/ForumReplyStructure';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from '../../../../shared/components/header/header.component';
import { FooterComponent } from '../../../../shared/components/footer/footer.component';

@Component({
  selector: 'app-forum-detail',
  standalone: true,
  templateUrl: './forum-detail.component.html',
  styleUrls: ['./forum-detail.component.css'],
  imports: [CommonModule, FormsModule, HeaderComponent, FooterComponent],
})
export class ForumDetailComponent {
  topic: ForumStructure | null = null;
  newReplyContent: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private forumService: ForumService
  ) {
    this.loadTopicDetails();
  }

  /**
   * Carga los detalles del tema basado en el slug proporcionado en la URL.
   */
  private loadTopicDetails(): void {
    const slug = this.route.snapshot.paramMap.get('slug');
    if (slug) {
      this.forumService.getTopicBySlug(slug).subscribe(
        (data) => {
          this.topic = data;
        },
        (error) => {
          console.error('Error al cargar el detalle del foro:', error);
        }
      );
    }
  }

  /**
   * Agrega una nueva respuesta al tema actual.
   */
  addReply(): void {
    if (this.topic && this.newReplyContent.trim()) {
      this.forumService.addReply(
        this.topic.id,
        this.newReplyContent,
        1, // Ejemplo de userId
        'participant' // Ejemplo de userType
      ).subscribe(
        (newReply: ForumReplyStructure) => {
          this.topic?.replies.push(newReply);
          this.newReplyContent = ''; // Limpia el campo después de agregar la respuesta
        },
        (error) => {
          console.error('Error al añadir la respuesta:', error);
        }
      );
    }
  }

  /**
   * Regresa al listado de foros.
   */
  clearSelectedForum(): void {
    this.router.navigate(['/foros']);
  }
}
