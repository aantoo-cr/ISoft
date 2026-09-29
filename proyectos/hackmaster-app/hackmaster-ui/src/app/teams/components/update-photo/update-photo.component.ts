import { Component, OnInit } from '@angular/core';
import { XButtonComponent } from '../commons/x-button/x-button.component';
import { UpdatePhotoService } from '../../service/update-photo-service';
import { ProjectsService } from '../../service/projects.service';
import { ActivatedRoute, Router } from '@angular/router';
import { HeaderComponent } from "../../../shared/components/header/header.component";
import { FooterComponent } from "../../../shared/components/footer/footer.component";

@Component({
  selector: 'app-update-photo',
  standalone: true,
  imports: [XButtonComponent, HeaderComponent, FooterComponent],
  templateUrl: './update-photo.component.html',
  styleUrl: './update-photo.component.css',
})
export class UpdatePhotoComponent implements OnInit {
  imageUrl: string | ArrayBuffer | null = null;
  selectedFile: File | null = null;
  projectId!: number;

  private readonly defaultPhotoUrl = 'assets/images/team/default-avatar.jpg';

  constructor(
    private updatePhotoService: UpdatePhotoService,
    private route: ActivatedRoute,
    private projectsService: ProjectsService,
    private router: Router
  ) {}

  async ngOnInit(): Promise<void> {
    this.projectId = Number(this.route.snapshot.paramMap.get('id'));
    this.selectedFile = await this.loadDefaultPhotoAsFile();
    this.imageUrl = this.defaultPhotoUrl;
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    const file = input?.files?.[0];

    if (!file) return;

    const fileType = file.type;

    if (fileType !== 'image/jpeg' && fileType !== 'image/png') {
      console.log('ERROR');
      this.updatePhotoService.emitFailWhenUpdatePhoto();
      return;
    }

    this.selectedFile = file;

    const reader = new FileReader();
    reader.onload = () => {
      this.imageUrl = reader.result;
      console.log(this.imageUrl);
    };
    reader.readAsDataURL(file);
  }

  onClose() {
    this.projectsService
      .updateTeamPhoto(this.projectId, this.selectedFile!)
      .subscribe({
        next: () => {
          this.router.navigate([`teams/info/${this.projectId}`]);
        },
        error: (error) => {
          console.error('Error al crear el equipo:', error);
        },
      });
  }

  async loadDefaultPhotoAsFile(): Promise<File> {
    const response = await fetch(this.defaultPhotoUrl);
    const blob = await response.blob();
    return new File([blob], 'default-avatar.jpg', { type: blob.type });
  }
}
