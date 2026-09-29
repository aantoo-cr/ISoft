import { Component, EventEmitter, Output } from '@angular/core';
import { faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@Component({
  selector: 'app-nav-bar',
  standalone: true,
  imports: [FontAwesomeModule],
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.css'
})
export class NavBarComponent {
  faMagnifyingGlass = faMagnifyingGlass
  @Output() searchChanged = new EventEmitter<string>();

  onSearchChange(searchValue: string): void {
    this.searchChanged.emit(searchValue);
  }
}
