import { Component, OnInit } from '@angular/core';
import { ParticipantService } from '../../service/participant.service';
import { ActivatedRoute, Router } from '@angular/router';
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {Participant} from "../../domain/Participant";

@Component({
  selector: 'app-edit-user',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css'],
})
export class EditUserComponent implements OnInit {
  user: Participant = {
    id: '',
    username: '',
    password: '',
    email: '',
    rut: '',
    name: '',
    lastname: '',
    city: '',
    country: '',
    phoneNumber: '',
  };

  password = '';
  errorMessage = '';

  constructor(
    private userService: ParticipantService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const email = this.route.snapshot.paramMap.get('email');
    if (email) {
      this.userService.getUserByEmail(email).subscribe({
        next: (data) => {
          this.user = data;
        },
        error: (err) => {
          console.error('Error al obtener los datos del usuario:', err);
          this.errorMessage = 'Error al cargar los datos del usuario.';
        },
      });
    }
  }

  updateUser() {
    if (!this.password) {
      this.errorMessage = 'Debes ingresar tu contraseña actual para confirmar los cambios.';
      return;
    }

    const payload = {
      ...this.user, // Incluye los datos del usuario
      password: this.password // Añade la contraseña como validación
    };

    this.userService.updateUser(payload).subscribe({
      next: () => {
        console.log('Usuario actualizado con éxito');
        this.router.navigate(['/home']); // Redirigir al home
      },
      error: (err) => {
        console.error('Error al actualizar el usuario:', err);
        this.errorMessage = 'Error al actualizar los datos. Verifica la contraseña ingresada.';
      },
    });
  }

}
