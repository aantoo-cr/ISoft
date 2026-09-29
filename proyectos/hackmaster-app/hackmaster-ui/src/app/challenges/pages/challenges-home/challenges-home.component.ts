// src/app/challenges/pages/challenges-home/challenges-home.component.ts
import { Component, OnInit } from '@angular/core';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChallengesService } from '../../service/challenges.service';
import {HeaderComponent} from "../../../shared/components/header/header.component";
import {FooterComponent} from "../../../shared/components/footer/footer.component";
import {ChallengesFormInicialComponent} from "../../components/challenges-form-inicial/challenges-form-inicial.component";

@Component({
  selector: 'app-challenges-home',
  templateUrl: './challenges-home.component.html',
  standalone: true,
  styleUrls: ['./challenges-home.component.css'],
  imports: [
    CommonModule,
    HeaderComponent,
    FooterComponent,
    ChallengesFormInicialComponent,
  ]
})
export class ChallengesHomeComponent implements OnInit {
  challenges: any[] = [];
  isLoading: boolean = true;
  isModalOpen = false;

  openModal() {
    this.isModalOpen = true;
  }

  closeModal() {
    this.isModalOpen = false;

  }

  constructor(private challengesService: ChallengesService) { }

  ngOnInit(): void {
    this.challengesService.getChallenges().subscribe(
      (data) => {
        this.challenges = data;  // Los datos pueden ser cualquier cosa
        this.isLoading = false;
      },
      (error) => {
        console.error('Error al obtener desafíos', error);
        this.isLoading = false;
      }
    );
  }
}
