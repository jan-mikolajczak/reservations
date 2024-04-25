import {Component, OnInit} from '@angular/core';
import {CalendarEvent, CalendarView} from 'angular-calendar';
import {AppointmentService} from "../../../../services/appointment.service";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements OnInit {

  viewDate: Date = new Date();
  view: CalendarView = CalendarView.Month;
  CalendarView = CalendarView;
  monday = 1;

  events: CalendarEvent[] = [];

  constructor(private appointmentService: AppointmentService, private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.loadAppointments();
  }

  setView(view: CalendarView) {
    this.view = view;
  }

  dayClicked({date}: { date: Date }): void {
    const currentDate = new Date();
    const selectedDate = new Date(date);

    selectedDate.setHours(currentDate.getHours());
    selectedDate.setMinutes(currentDate.getMinutes());

    const startTime = selectedDate;
    const endTime = new Date(startTime.getTime() + (60 * 60 * 1000)); // Czas zakończenia: 1 godzina po czasie rozpoczęcia

    const newAppointment: CalendarEvent = {
      start: startTime,
      end: endTime,
      title: 'New Appointment',
    };

    this.appointmentService.addAppointment(newAppointment)
      .subscribe({
        next: (data) => {
          const endValue = newAppointment.end ? new Date(newAppointment.end) : undefined;

          const addedAppointment: CalendarEvent = {
            start: new Date(newAppointment.start),
            end: endValue,
            title: newAppointment.title,
          };
          this.events.push(addedAppointment);
          this.messageService.add({severity: 'success', summary: 'Success', detail: 'Dodano: ' + data.title});
          this.loadAppointments();
        },
        error: (err) => this.messageService.add({severity: 'error', summary: 'Błąd podczas dodawania terminu:', detail: err.message})
      });
  }

  private loadAppointments(): void {
    this.appointmentService.getAppointments()
      .subscribe({
        next: (appointments) => {
          this.events = appointments.map(appointment => ({
            ...appointment,
            start: new Date(appointment.start),
            end: appointment.end ? new Date(appointment.end) : undefined
          }));
        },
        error: (err) => this.messageService.add({severity: 'error', summary: 'Błąd podczas pobierania terminów:', detail: err.message})
      });
  }


  /*
  dayClicked({date, events}: { date: Date; events: CalendarEvent[] }): void {
    console.log(date);
    console.log(events);
    // let x=this.adminService.dateFormat(date)
    // this.openAppointmentList(x)
  }
  */

}
