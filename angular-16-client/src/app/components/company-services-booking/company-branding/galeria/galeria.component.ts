import { Component } from '@angular/core';
import {CompanyBrandingService} from "../../../../services/company-branding.service";

@Component({
  selector: 'app-galeria',
  templateUrl: './galeria.component.html',
  styleUrls: ['./galeria.component.css']
})
export class GaleriaComponent {
  images: any[] | undefined;

  responsiveOptions: any[] = [
    {
      breakpoint: '1024px',
      numVisible: 5
    },
    {
      breakpoint: '768px',
      numVisible: 3
    },
    {
      breakpoint: '560px',
      numVisible: 1
    }
  ];

  constructor(private photoService: CompanyBrandingService) {}

  ngOnInit() {
    this.photoService.getImages().then((images) => {
      this.images = images;
    });
  }
}
