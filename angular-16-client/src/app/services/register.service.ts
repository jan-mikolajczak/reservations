import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {URL} from "../app.constants";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private http: HttpClient) {
  }

  registerUser(data: any): Observable<any> {
    return this.http.post(`${URL}/register-vendor`, data);
  }
}
