import { Component, OnInit } from '@angular/core';
import { FooterComponent } from "../../../shared/components/footer/footer.component";
import { HeaderComponent } from "../../../shared/components/header/header.component";
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule } from "@angular/forms";
import { avance} from "../../domain/avance";
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
  templateUrl: './avances.component.html',
  styleUrls: ['./avances.component.css']
})
export class AvancesComponent implements OnInit {
  avances: avance[] = [];
  xFormulario: FormGroup;

  constructor(private avanceService: AvanceService, private fb: FormBuilder) {
    this.xFormulario = this.fb.group({
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

  crearAvances() {
    const avance: avance = {
      urlAvance: this.xFormulario.value.urlAvance,
      description: this.xFormulario.value.description,
      date: new Date()
    };

    this.avanceService.crearAvance(avance).subscribe({
      next: (respuesta: avance) => {
        console.log("Respuesta del Servidor", respuesta);
        this.obtenerAvances();
        this.cancelar();
      },
      error: (error: Error) => console.log("error al enviar datos", error)
    });
  }

  cancelar() {
    this.xFormulario.reset();
  }
}
