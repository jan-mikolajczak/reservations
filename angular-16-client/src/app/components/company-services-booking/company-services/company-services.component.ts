import {Component, OnInit} from '@angular/core';
import {ServiceService} from "../../../services/service.service";
import {ServiceDTO} from "../../../models/service.model";

@Component({
  selector: 'app-company-services',
  templateUrl: './company-services.component.html',
  styleUrls: ['./company-services.component.css']
})

export class CompanyServicesComponent implements OnInit {
  services?: ServiceDTO[];

  constructor(private productService: ServiceService) {}

  ngOnInit() {
    this.retrieveProducts();
  }

  private retrieveProducts() {
    this.productService.getAll().subscribe({
      next: (data) => {
        this.services = data;
        console.log(data);
      },
      error: (e) => console.error(e)
    });
  }
}
