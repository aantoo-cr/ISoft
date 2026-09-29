import {Component, OnInit} from '@angular/core';
import {House} from "../../model/house";
import {SearchServiceService} from "../../services/search-service.service";
import {HttpClientModule} from "@angular/common/http";
import {HousesListComponent} from "../houses-list/houses-list.component";
import {FormsModule} from "@angular/forms";


@Component({
  selector: 'app-search-bar',
  standalone: true,
  imports: [HttpClientModule, HousesListComponent, FormsModule],
  providers: [SearchServiceService],
  templateUrl: './search-bar.component.html',
  styleUrl: './search-bar.component.css'
})

export class SearchBarComponent implements OnInit {

  houses: House[] = [];
  searchTerm: string = '';

  constructor(private searchService: SearchServiceService) {
  }

  ngOnInit(): void {
    this.loadHouses();
  }

  public loadHouses() {
    this.searchService.getHouses().subscribe((houses) => {
      this.houses = houses;
      console.log(this.houses);
    });
  }

  onSearch(): void {
    this.searchService.searchHouses(this.searchTerm).subscribe(
      (data: House[]) => {
        this.houses = data
        console.log(this.houses);
      },
      (error: any) => {
        console.error('Error fetching houses', error);
      }
    );
  }

}
