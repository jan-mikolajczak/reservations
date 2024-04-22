import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {URL} from "../app.constants";
import {ServiceCategoryDTO} from "../models/service-category.model";

@Injectable({
  providedIn: 'root'
})
export class ProductCategoryService {

  constructor(private http: HttpClient) {}

  getCategoriesByVendorId(vendorId: number | undefined): Observable<ServiceCategoryDTO[]> {
    return this.http.get<ServiceCategoryDTO[]>(`${URL}/product-categories?vendorId=` + vendorId);
  }

}
