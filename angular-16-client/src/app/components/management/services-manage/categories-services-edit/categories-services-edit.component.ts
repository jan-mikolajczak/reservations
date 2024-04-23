import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from "../../../../services/auth.service";
import {LoggedUser} from "../../../../models/logged-user.model";
import {ServiceService} from "../../../../services/service.service";
import {VendorService} from "../../../../services/vendor.service";
import {ServiceDTO} from "../../../../models/service.model";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-categories-services-edit',
  templateUrl: './categories-services-edit.component.html',
  styleUrls: ['./categories-services-edit.component.css']
})
export class CategoriesServicesEditComponent implements OnInit, OnDestroy {
  private serviceAddedSubscription: Subscription | undefined;
  services: ServiceDTO[] = [];
  loggedUser?: LoggedUser | null = null;

  constructor(private productService: ServiceService,
              private vendorService: VendorService,
              private authService: AuthService) {
  }

  ngOnInit() {
    this.getLoggedUser();
    this.getVendor();
    this.serviceAddedSubscription = this.productService.serviceAdded$.subscribe(() => {
      this.getServices();
    });
  }

  ngOnDestroy() {
    this.serviceAddedSubscription?.unsubscribe();
  }

  private getServices() {
    this.productService.getServicesByVendorId(this.vendorService.vendorData?.vendorId)
      .subscribe({
        next: (data) => {
          this.services = data || [];
          console.log(data);
        },
        error: (e) => console.error(e)
      });
  }

  private getVendor() {
    if (this.loggedUser) {
      this.vendorService.getVendorByUserLogin(this.loggedUser.username).subscribe({
        next: (data) => {
          this.vendorService.vendorData = data;
          console.log('Current vendor: ', JSON.stringify(this.vendorService.vendorData))
        },
        error: (e) => console.error(e),
        complete: () => {
          this.getServices();
          console.info('complete')
        }
      })
    }
  }

  private getLoggedUser() {
    this.loggedUser = this.authService.getLoggedUser();
  }

  calculateTotalProducts(name: string) {
    let total = 0;

    this.services.forEach(product => {
      if (product.serviceCategory?.categoryName === name) {
        total++;
      }
    });
    return total;
  }


  editService(service: ServiceDTO) {
    console.log(service);
  }
}
