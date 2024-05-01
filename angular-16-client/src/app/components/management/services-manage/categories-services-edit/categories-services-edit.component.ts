import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from "../../../../services/auth.service";
import {ServiceService} from "../../../../services/service.service";
import {VendorService} from "../../../../services/vendor.service";
import {ServiceDTO} from "../../../../models/service.model";
import {Subscription} from "rxjs";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-categories-services-edit',
  templateUrl: './categories-services-edit.component.html',
  styleUrls: ['./categories-services-edit.component.css']
})
export class CategoriesServicesEditComponent implements OnInit, OnDestroy {
  private serviceAddedSubscription: Subscription | undefined;
  services: ServiceDTO[] = [];

  constructor(private productService: ServiceService,
              private vendorService: VendorService,
              private authService: AuthService,
              private messageService: MessageService) {
  }

  ngOnInit() {
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
    if (this.vendorService.vendorData === undefined)
      this.vendorService.getVendorByUserLogin(this.authService.getLoggedUser()?.username)
        .subscribe({
          next: (data) => {
            this.vendorService.vendorData = data;
          },
          error: (err) => this.messageService.add({severity: 'error', summary: 'Coś poszło nie tak', detail: err}),
          complete: () => {
            this.getServices();
          }
        })
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
