import {Component, OnInit} from '@angular/core';
import {House} from "../../model/house";
import { CommonModule } from '@angular/common';                                 // importar common module
import {SearchServiceService} from "../../services/search-service.service";
import {HttpClientModule} from "@angular/common/http";
import {HousesListComponent} from "../houses-list/houses-list.component";
import {FormsModule} from "@angular/forms";


@Component({
  selector: 'app-search-bar',
  standalone: true,
  imports: [CommonModule, HttpClientModule, HousesListComponent, FormsModule],      // obvio agregar aquí también
  providers: [SearchServiceService],
  templateUrl: './search-bar.component.html',
  styleUrl: './search-bar.component.css'
})

export class SearchBarComponent implements OnInit {

  houses: House[] = [];
  searchTerm: string = '';

//para el forms de nueva casa
  showNewHouseForm: boolean = false;

  newHouse: House = {
    title: '',
    address: '',
    description: '',
    photo: '',
    available: true
  };

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

// nuevo
  toggleNewHouseForm(): void {this.showNewHouseForm = !this.showNewHouseForm;
  }

  onSubmitNewHouse(): void {
    const title = this.newHouse.title?.trim();
    const address = this.newHouse.address?.trim();
    console.log('newHouse al enviar:', this.newHouse);  //prueba de fucnionamiento

    if (!title || !address) {
      alert('El título y la dirección son obligatorios');
      return;
    }

    this.searchService.createHouse(this.newHouse).subscribe(
      (created: House) => { this.loadHouses();
        this.newHouse = {
        title: '',
        address: '',
        description: '',
        photo: '',
        available: true
        };
        this.showNewHouseForm = false;
      },
      (error:any)=> {
        console.error('Error creating house', error);
        alert('Ocurrió un error al crear la casa.');
      }
    );
  }

}
