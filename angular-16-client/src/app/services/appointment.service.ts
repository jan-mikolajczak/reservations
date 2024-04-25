import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CalendarEvent} from "angular-calendar";
import {Observable} from "rxjs";
import {URL} from "../app.constants";

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  constructor(private http: HttpClient) {}

  getAppointments(): Observable<CalendarEvent[]> {
    return this.http.get<CalendarEvent[]>(`${URL}/appointments`);
  }

  addAppointment(appointment: CalendarEvent): Observable<any> {
    return this.http.post<any>(`${URL}/appointments`, appointment);
  }
}
