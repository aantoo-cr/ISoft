import {Component, Input} from '@angular/core';
import {Transaction} from "../../domain/transaction";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-table-history',
  standalone: true,
  imports: [
    NgForOf
  ],
  templateUrl: './table-history.component.html',
  styleUrl: './table-history.component.css'
})
export class TableHistoryComponent {
  @Input() transactions!: Transaction[];
}
