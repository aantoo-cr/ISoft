import { Component } from '@angular/core';
import { HeaderComponent } from "../header/header.component";
import { BannerComponent } from "../banner/banner.component";
import { FooterComponent } from "../footer/footer.component";

@Component({
  selector: 'app-homepage',
  standalone: true,
  imports: [HeaderComponent, BannerComponent, FooterComponent],
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.css'
})
export class HomepageComponent {

}
