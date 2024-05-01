import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {URL} from "../app.constants";
import {EmployeeDTO} from "../models/employee.model";

@Injectable({
  providedIn: 'root'
})
export class EmployeesService {
  private _employees: EmployeeDTO[] = [];

  constructor(private http: HttpClient) { }

  getVendorEmployeesByVendorId(vendorId: number | undefined): Observable<EmployeeDTO[]> {
    return this.http.get<EmployeeDTO[]>(`${URL}/vendor-employees?vendorId=` + vendorId);
  }

  get employees(): EmployeeDTO[] {
    return this._employees;
  }

  set employees(value: EmployeeDTO[]) {
    this._employees = value;
  }
}
