import { Component } from '@angular/core';
import {CalendarView, CalendarEvent} from 'angular-calendar';
import {startOfDay} from "date-fns";

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent {

  viewDate: Date = new Date();
  view: CalendarView = CalendarView.Month;
  CalendarView = CalendarView;
  monday = 1;

  events: CalendarEvent[] = [
    {
      start: startOfDay(new Date()),
      title: 'First event',
    },
    {
      start: startOfDay(new Date()),
      title: 'Second event',
    }
  ];

  setView(view: CalendarView) {
    this.view = view;
  }


  dayClicked({date, events}: { date: Date; events: CalendarEvent[] }): void {
    console.log(date);
    console.log(events);
    // let x=this.adminService.dateFormat(date)
    // this.openAppointmentList(x)
  }

}
