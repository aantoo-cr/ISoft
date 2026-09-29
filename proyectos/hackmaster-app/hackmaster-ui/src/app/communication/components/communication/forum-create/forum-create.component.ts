import { Component } from '@angular/core';
import { ForumService } from '../../../service/forum.service';
import { Router } from '@angular/router';
import { FormsModule } from "@angular/forms";
import { HeaderComponent } from "../../../../shared/components/header/header.component";
import { FooterComponent } from "../../../../shared/components/footer/footer.component";

@Component({
  selector: 'app-forum-create',
  templateUrl: './forum-create.component.html',
  standalone: true,
  styleUrls: ['./forum-create.component.css'],
  imports: [FormsModule, HeaderComponent, FooterComponent]
})
export class ForumCreateComponent {
  newTopic = { title: '', description: '' }; // Definir el objeto newTopic con los campos title y description

  constructor(private forumService: ForumService, private router: Router) {}

  publishTopic() {
    if (this.newTopic.title && this.newTopic.description) {
      const userId = 1; // Cambia esto al userId real del usuario
      const userType = 'participant'; // Cambia esto al tipo real del usuario

      this.forumService.createTopic(this.newTopic, userId, userType).subscribe(() => {
        this.router.navigate(['/foros']); // Redirige a la lista de foros después de publicar
      }, (error) => {
        console.error('Error al crear el tópico:', error);
        alert(`Error al crear el tópico: ${error.message}`);
      });
    } else {
      alert('Por favor, completa ambos campos.');
    }
  }

}
