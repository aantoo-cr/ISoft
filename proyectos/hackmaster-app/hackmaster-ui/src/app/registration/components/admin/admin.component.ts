import { Component } from '@angular/core';
import { RequestsListComponent } from './requests-list/requests-list.component';
import { AdminService } from '../../service/admin.service';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [RequestsListComponent],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css',
})
export class AdminComponent {
  constructor(private adminService: AdminService) {}

  reloadRequests(): void {
    this.adminService.getPendingRequests().subscribe();
  }
}
