import { RouterModule, Router } from "@angular/router";
import { Component, OnInit } from '@angular/core';
import { PrizeService } from '../../service/PrizeService';
import { Prize } from '../../domain/prize';
import { CommonModule } from "@angular/common";
import { HeaderComponent } from "../../../shared/components/header/header.component";
import { ActivatedRoute, RouterLink } from '@angular/router';
import { BannerComponent } from "../../../shared/components/banner/banner.component";
import { FooterComponent } from "../../../shared/components/footer/footer.component";
import { PrizeFormComponent } from "../prize-form/prize-form.component";
import { ViewChild } from "@angular/core";

@Component({
  selector: 'app-prize-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FooterComponent, HeaderComponent, PrizeFormComponent],
  templateUrl: './prize-list.component.html',
})
export class PrizeListComponent implements OnInit {
  prizes: Prize[] = [];
  challengeId!: number;
  isFormOpen = false; // Nueva propiedad para controlar el estado del formulario

  constructor(private prizeService: PrizeService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.loadPrizes();
  }

  loadPrizes(): void {
    this.challengeId = Number(this.route.snapshot.paramMap.get('challengeId'));
    this.prizeService.getAllPrizes(this.challengeId).subscribe(
      (data) => {
        this.prizes = data.sort((a, b) => a.position - b.position);
      },
      (error) => {
        console.error('Error al cargar los premios', error);
      }
    );
  }

  deletePrize(prizeId: number): void {
    this.prizeService.deletePrize(this.challengeId, prizeId).subscribe(
      () => {
        this.prizes = this.prizes.filter(prize => prize.id !== prizeId);
      },
      (error) => {
        console.error('Error al eliminar el premio', error);
      }
    );
  }

  openForm(): void {
    this.isFormOpen = true;
  }

  closeForm(): void {
    this.isFormOpen = false;
  }
}
