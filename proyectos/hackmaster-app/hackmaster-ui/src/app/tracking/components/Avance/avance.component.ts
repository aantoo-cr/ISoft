import { Component, OnInit } from '@angular/core';
import { FooterComponent } from "../../../shared/components/footer/footer.component";
import { HeaderComponent } from "../../../shared/components/header/header.component";
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule } from "@angular/forms";
import { avance } from "../../domain/avance";
import { AvanceService } from "../../service/avance.service";
import { CommonModule } from "@angular/common";
import { HttpClientModule } from "@angular/common/http";

@Component({
  selector: 'app-tracking',
  standalone: true,
  imports: [
    FooterComponent,
    HeaderComponent,
    CommonModule, FormsModule, HttpClientModule, ReactiveFormsModule
  ],
  providers: [AvanceService],
  templateUrl: './avance.component.html',
  styleUrls: ['./avance.component.css']
})
export class AvanceComponent implements OnInit {
  avances: avance[] = [];
  miFormulario: FormGroup;

  constructor(private avanceService: AvanceService, private fb: FormBuilder) {
    this.miFormulario = this.fb.group({
      urlAvance: new FormControl(''),
      description: new FormControl('')
    });
  }

  ngOnInit(): void {
    this.obtenerAvances();
  }

  public obtenerAvances() {
    this.avanceService.obtenerAvances().subscribe((avances: avance[]) => {
      this.avances = avances;
    });
  }

  crearAvance() {
    const miAvance = new avance(this.miFormulario.value.urlAvance);
    miAvance.description = this.miFormulario.value.description;

    this.avanceService.crearAvance(miAvance).subscribe({
      next: (respuesta: avance) => {
        console.log("Respuesta del Servidor", respuesta);
        this.obtenerAvances();
      },
      error: (error: Error) => console.log("error al enviar datos", error)
    });
  }

  cancelar() {
    this.miFormulario.reset();
  }
}
