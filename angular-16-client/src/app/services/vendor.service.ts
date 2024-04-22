import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {URL} from "../app.constants";
import {VendorDTO} from "../models/vendor.model";

@Injectable({
  providedIn: 'root'
})
export class VendorService {

  constructor(private http: HttpClient) {
  }

  getVendorByUserLogin(userLogin: string | undefined): Observable<VendorDTO> {
    return this.http.get<VendorDTO>(`${URL}/get-vendor?userLogin=` + userLogin);
  }
}

