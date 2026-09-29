import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RouterModule } from '@angular/router';
import { HeaderComponent } from '../../../shared/components/header/header.component'; // Ajusta la ruta según sea necesario
import { SidebarComponent } from '../../../shared/components/sidebar/sidebar.component'; // Ajusta la ruta según sea necesario

@Component({
  selector: 'app-dispositivo-desvinculado',
  standalone: true,
  imports: [RouterModule, HeaderComponent, SidebarComponent],
  templateUrl: './dispositivo-desvinculado.component.html',
  styleUrls: ['./dispositivo-desvinculado.component.css']
})
export class DispositivoDesvinculadoComponent implements OnInit {
  email: string = '';
  deviceName: string = '';
  deviceModel: string = '';

  constructor(private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.email = this.route.snapshot.queryParamMap.get('email') || '';
    this.deviceName = this.route.snapshot.queryParamMap.get('deviceName') || '';
    this.deviceModel = this.route.snapshot.queryParamMap.get('deviceModel') || '';
    console.log('Email:', this.email);
    console.log('Device Name:', this.deviceName);
    console.log('Device Model:', this.deviceModel);
  }

  volverInicio(): void {
    this.router.navigate(['/dashboard']);
  }
}
