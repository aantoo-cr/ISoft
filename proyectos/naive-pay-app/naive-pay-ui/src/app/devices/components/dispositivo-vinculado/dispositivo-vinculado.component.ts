import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-dispositivo-vinculado',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './dispositivo-vinculado.component.html',
  styleUrls: ['./dispositivo-vinculado.component.css']
})
export class DispositivoVinculadoComponent implements OnInit {
  email: string = '';
  deviceName: string = '';
  deviceModel: string = '';

  constructor(private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.email = this.route.snapshot.queryParamMap.get('email') || '';
    this.deviceName = this.route.snapshot.queryParamMap.get('deviceName') || '';
    this.deviceModel = this.route.snapshot.queryParamMap.get('deviceModel') || '';
  }

  volverInicio(): void {
    this.router.navigate(['/dashboard']);
  }
}

