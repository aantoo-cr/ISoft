import { Component, OnDestroy, OnInit } from '@angular/core';
import { faCircleExclamation } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { Subscription } from 'rxjs';
import { UpdatePhotoService } from '../../../service/update-photo-service';

@Component({
  selector: 'app-update-photo-toast',
  standalone: true,
  imports: [FontAwesomeModule],
  templateUrl: './update-photo-toast.component.html',
  styleUrl: './update-photo-toast.component.css'
})
export class UpdatePhotoToastComponent implements OnInit, OnDestroy {
  faCircleExclamation = faCircleExclamation;
  private subscription: Subscription = new Subscription();
  private toastTimer: any;
  showToast: boolean = false;

  constructor(private updatePhotoService: UpdatePhotoService) {}

  ngOnInit(): void {
    this.subscription.add(
        this.updatePhotoService.fileErrorSubject.subscribe(() => {
          console.log("TOAST");
          this.showToast = true;
            if (this.toastTimer) {
              clearTimeout(this.toastTimer);
            }
            this.toastTimer = setTimeout(() => {
              this.showToast = false;
            }, 2000);
        })
      );
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
    if (this.toastTimer) {
      clearTimeout(this.toastTimer);
    }
  }
}
