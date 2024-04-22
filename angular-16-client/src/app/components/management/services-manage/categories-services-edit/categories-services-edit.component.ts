import {Component, OnInit} from '@angular/core';
import {VendorService} from "../../../../services/vendor.service";
import {AuthService} from "../../../../services/auth.service";
import {LoggedUser} from "../../../../models/logged-user.model";
import {VendorDTO} from "../../../../models/vendor.model";
import {ServiceService} from "../../../../services/service.service";
import {ServiceDTO} from "../../../../models/service.model";

@Component({
  selector: 'app-categories-services-edit',
  templateUrl: './categories-services-edit.component.html',
  styleUrls: ['./categories-services-edit.component.css']
})
export class CategoriesServicesEditComponent implements OnInit {

  services: ServiceDTO[] = [];
  loggedUser?: LoggedUser | null = null;
  vendorData?: VendorDTO | undefined;

  constructor(private productService: ServiceService,
              private vendorService: VendorService,
              private authService: AuthService) {
  }

  ngOnInit() {
    this.getLoggedUser();
    this.getVendor();
  }

  private getProducts() {
    this.productService.getServicesByVendorId(this.vendorData?.vendorId)
      .subscribe({
      next: (data) => {
        this.services = data || [];
        console.log(data);
      },
      error: (e) => console.error(e)
    });
  }

  private getVendor() {
    this.vendorService.getVendorByUserLogin(this.loggedUser?.username).subscribe({
      next: (data) => {
        this.vendorData = data;
        console.log('Current vendor: ', JSON.stringify(this.vendorData))
      },
      error: (e) => console.error(e),
      complete: () => {
        this.getProducts();
        console.info('complete')
      }
    })
  }

  private getLoggedUser() {
    this.loggedUser = this.authService.getLoggedUser();
  }

  calculateTotalProducts(name: string) {
    let total = 0;

    if (this.services) {
      for (let product of this.services) {
        if (product.serviceCategory?.categoryName === name) {
          total++;
        }
      }
    }

    return total;
  }

}
