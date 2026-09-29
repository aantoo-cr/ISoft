import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminService } from '../../../service/admin.service';
import { Organization } from '../../../domain/Organization';

@Component({
  selector: 'app-requests-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './requests-list.component.html',
  styleUrls: ['./requests-list.component.css'],
})
export class RequestsListComponent implements OnInit {
  pendingRequests: Organization[] = [];

  constructor(private adminService: AdminService) {}

  ngOnInit(): void {
    this.adminService.pendingRequests$.subscribe((requests) => {
      this.pendingRequests = requests;
    });
    this.adminService.getPendingRequests().subscribe();
  }

  reloadRequests(): void {
    this.adminService.getPendingRequests().subscribe();
  }

  approveRequest(id: number): void {
    if (id === undefined) {
      console.error('[request-list] Error: id es undefined');
      return;
    }

    this.adminService.approveRequest(id).subscribe({
      next: (response) => {
        console.log('[request-list] Solicitud aprobada: ', response);
      },
      error: (error) => {
        console.error(
          '[request-list] Error, no se pudo aprobar la solicitud',
          error,
        );
      },
    });
  }

  rejectRequest(id: number): void {
    if (id === undefined) {
      console.error('[request-list] Error: id es undefined');
      return;
    }

    this.adminService.rejectRequest(id).subscribe({
      next: (response) => {
        console.log('[request-list] Solicitud rechazada: ', response);
      },
      error: (error) => {
        console.error(
          '[request-list] Error, no se pudo rechazar la solicitud',
          error,
        );
      },
    });
  }
}
