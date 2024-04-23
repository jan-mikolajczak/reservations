import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, Subject} from "rxjs";
import {URL} from "../app.constants";
import {ServiceDTO} from "../models/service.model";
import {ServiceCategoryDTO} from "../models/service-category.model";

@Injectable({
  providedIn: 'root',
})
export class ServiceService {

  private serviceAddedSubject = new Subject<void>();

  serviceAdded$ = this.serviceAddedSubject.asObservable();

  constructor(private http: HttpClient) {}

  getAll(): Observable<ServiceDTO[]> {
    return this.http.get<ServiceDTO[]>(`${URL}/service/all`);
  }

  getServicesByVendorId(vendorId: number | undefined): Observable<ServiceDTO[]> {
    return this.http.get<ServiceDTO[]>(`${URL}/service/byVendorId?vendorId=` + vendorId);
  }

  saveService(service: ServiceDTO): Observable<ServiceDTO> {
    return this.http.post<ServiceDTO>(URL + "/service", service)
  }

  emitServiceAdded() {
    this.serviceAddedSubject.next();
  }
}
