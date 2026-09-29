import { Component, OnInit } from '@angular/core';
import { CU5Service, JudgeHackathonReport, TeamRanking, JudgeGrade } from '../../service/cu5.service';
import { CommonModule } from '@angular/common';
import { Location } from '@angular/common';
import {ActivatedRoute} from "@angular/router"; // Importamos Location

@Component({
  selector: 'app-cu5',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cu5.component.html',
  styleUrls: ['./cu5.component.css']
})
export class Cu5Component implements OnInit {
  hackathonName: string = '';
  actorName: string = '';
  actorType: string = '';
  ranking: TeamRanking[] = [];
  grades: JudgeGrade[] = [];
  errorMessage: string = '';
  actorId: number | null = null;
  hackatonId: number | null = null;

  constructor(private cu5Service: CU5Service,
              private location: Location,
              private route: ActivatedRoute
  ) { } // Inyectamos Location

  ngOnInit(): void {
    const actorIdParam = this.route.snapshot.paramMap.get('actorId');
    const hackatonIdParam = this.route.snapshot.paramMap.get('hackatonId');

    this.actorId = actorIdParam ? Number(actorIdParam) : null;
    this.hackatonId = hackatonIdParam ? Number(hackatonIdParam) : null;
    this.loadHackathonReport(actorIdParam, hackatonIdParam); // Ejemplo con judgeId 9 y hackathonId 1
  }

  loadHackathonReport(judgeId: string | null, hackathonId: string | null): void {
    this.cu5Service.getHackathonReportByJudge(judgeId, hackathonId).subscribe({
      next: (response: JudgeHackathonReport) => {
        this.hackathonName = response.hackatonName;
        this.actorName = response.actorName;
        this.actorType = response.actorType;
        this.ranking = response.ranking;
        this.grades = response.grades;
      },
      error: (err) => {
        this.errorMessage = 'No se pudo obtener el reporte del hackathon. Intenta nuevamente más tarde.';
        console.error(err);
      }
    });
  }

  goBack(): void {
    this.location.back(); // Navega hacia la página anterior
  }
}
