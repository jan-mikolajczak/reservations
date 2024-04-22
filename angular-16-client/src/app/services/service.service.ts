import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {URL} from "../app.constants";
import {ServiceDTO} from "../models/service.model";

@Injectable({
  providedIn: 'root',
})
export class ServiceService {

  constructor(private http: HttpClient) {}

  getAll(): Observable<ServiceDTO[]> {
    return this.http.get<ServiceDTO[]>(`${URL}/services/all`);
  }

  getServicesByVendorId(vendorId: number | undefined): Observable<ServiceDTO[]> {
    return this.http.get<ServiceDTO[]>(`${URL}/services/byVendorId?vendorId=` + vendorId);
  }
}
