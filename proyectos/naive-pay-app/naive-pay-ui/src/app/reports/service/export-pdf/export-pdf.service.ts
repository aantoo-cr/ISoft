import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { jsPDF } from 'jspdf';

@Injectable({
  providedIn: 'root'
})
export class ExportPdfService {
  constructor(private http: HttpClient) {}
  
  exportPdf(accountNumber: string, startDate: string, endDate: string, minAmount: number, maxAmount: number, estadoFiltro: string | null) {
    const url = `http://localhost:8080/report/export/${accountNumber}/${startDate}/${endDate}/${minAmount}/${maxAmount}/${estadoFiltro}`;
    this.http.get(url, { responseType: 'blob' }).subscribe({
      next: (response: Blob) => {
        const file = new Blob([response], { type: 'application/pdf' });
        const fileURL = URL.createObjectURL(file);
        const link = document.createElement('a');
        link.href = fileURL;
        link.download = 'reporte-transacciones.pdf';
        link.click();
      },
      error: (error) => {
        console.error('Error al generar el PDF', error);
      }
    });
  }
}
