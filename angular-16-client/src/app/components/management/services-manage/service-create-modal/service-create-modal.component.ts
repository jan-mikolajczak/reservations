import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ServiceCategoryDTO} from "../../../../models/service-category.model";
import {ServiceCategoryService} from "../../../../services/service-category.service";
import {VendorService} from "../../../../services/vendor.service";
import {MessageService} from "primeng/api";
import {ServiceService} from "../../../../services/service.service";

@Component({
  selector: 'service-create-modal',
  templateUrl: './service-create-modal.component.html',
  styleUrls: ['./service-create-modal.component.css']
})
export class ServiceCreateModalComponent implements OnInit {

  dialogVisible: boolean = false;
  serviceForm: FormGroup;
  categories: ServiceCategoryDTO[] | undefined;

  constructor(private fb: FormBuilder,
              private serviceCategoryService: ServiceCategoryService,
              private vendorService: VendorService,
              private messageService: MessageService,
              private serviceService: ServiceService) {
    this.serviceForm = this.fb.group({
      name: ["", Validators.required],
      description: ["", Validators.required],
      price: [null, Validators.required],
      active: true,
      serviceCategory: [null, Validators.required]
    });
  }

  showDialog() {
    this.getCategories();
    this.dialogVisible = true;
  }

  ngOnInit(): void {
  }

  private getCategories() {
    this.serviceCategoryService.getCategoriesByVendorId(this.vendorService.vendorData?.vendorId)
      .subscribe({
        next: (data) => {
          this.categories = data;
          console.log(data);
        },
        error: (err) => this.messageService.add({severity: 'error', summary: 'Error', detail: err.error})
      });
  }

  onSave(serviceForm: FormGroup) {
    console.log(this.serviceForm);
    if (serviceForm.invalid) {
      this.markAllFieldsAsTouched(this.serviceForm);
      return;
    } else {
      this.serviceService.saveService(this.serviceForm.value)
        .subscribe({
          next: (data) => {
            this.messageService.add({severity: 'success', summary: 'Success', detail: 'Dodano ' + data.name});
            this.dialogVisible = false;
            this.serviceService.emitServiceAdded();
          },
          error: (err) => this.messageService.add({severity: 'error', summary: 'Error', detail: err.error})
        });
    }
  }

  private markAllFieldsAsTouched(serviceForm: FormGroup) {
    Object.values(serviceForm.controls).forEach(control => {
      control.markAsDirty();
      if (control instanceof FormGroup) {
        this.markAllFieldsAsTouched(control);
      }
    });
  }
}
