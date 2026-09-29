import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ThemeService {
  private darkMode: boolean = false;

  constructor() {
    // Leer la preferencia almacenada en localStorage para persistir el tema entre sesiones
    const storedTheme = localStorage.getItem('darkMode');
    this.darkMode = storedTheme === 'true';
    this.applyTheme();
  }

  toggleTheme(): void {
    this.darkMode = !this.darkMode;
    localStorage.setItem('darkMode', this.darkMode.toString());
    this.applyTheme();
  }

  applyTheme(): void {
    if (this.darkMode) {
      console.log('Applying dark mode');
      document.documentElement.classList.add('dark');
    } else {
      console.log('Removing dark mode');
      document.documentElement.classList.remove('dark');
    }
  }
  

  isDarkMode(): boolean {
    return this.darkMode;
  }
}
