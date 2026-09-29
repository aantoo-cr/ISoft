import {Component, Input} from '@angular/core';

import {House} from "../../model/house";

@Component({
  selector: 'app-houses-list',
  standalone: true,
  imports: [],
  templateUrl: './houses-list.component.html',
  styleUrl: './houses-list.component.css'
})
export class HousesListComponent {
  @Input() houses: House[] = [];
}
