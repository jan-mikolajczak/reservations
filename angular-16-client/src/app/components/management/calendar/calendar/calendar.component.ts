import {Component, OnInit} from '@angular/core';
import {CalendarEvent, CalendarEventTimesChangedEvent, CalendarView} from 'angular-calendar';
import {AppointmentService} from "../../../../services/appointment.service";
import {MessageService} from "primeng/api";
import {Subject} from "rxjs";
import {isSameDay, isSameMonth} from "date-fns";
import {AddAppointmentDialogComponent} from "../add-appointment-dialog/add-appointment-dialog.component";
import {DialogService} from "primeng/dynamicdialog";
import {VendorService} from "../../../../services/vendor.service";
import {AuthService} from "../../../../services/auth.service";
import {EmployeesService} from "../../../../services/employees.service";

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
  activeDayIsOpen = false;
  events: CalendarEvent[] = [];
  refresh = new Subject<void>();
  modalData!: { action: string; event: CalendarEvent; };


  constructor(
    private appointmentService: AppointmentService,
    private messageService: MessageService,
    private dialogService: DialogService,
    private employeesService: EmployeesService,
    private vendorService: VendorService,
    private authService: AuthService
  ) {
  }

  ngOnInit(): void {
    this.loadAppointments();
  }

  setView(view: CalendarView) {
    this.view = view;
  }

  dayClicked({date, events}: { date: Date; events: CalendarEvent[] }): void {
    if (isSameMonth(date, this.viewDate)) {
      if (
        (isSameDay(this.viewDate, date) && this.activeDayIsOpen) ||
        events.length === 0
      ) {
        this.activeDayIsOpen = false;
      } else {
        this.activeDayIsOpen = true;
      }
      this.viewDate = date;
    }
  }

  private loadAppointments(): void {
    console.log("pobieranie terminów")
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

  closeOpenMonthViewDay() {
    this.activeDayIsOpen = false;
  }

  handleEvent(action: string, event: CalendarEvent): void {
    console.log("eventClicked");
    this.modalData = {event, action};
    // this.modal.open(this.modalContent, { size: 'lg' });
  }

  eventTimesChanged({event, newStart, newEnd,}: CalendarEventTimesChangedEvent): void {
    console.log("eventTimesChanged");
    console.log(event);
    console.log(newStart);
    console.log(newEnd);
    this.events = this.events.map((iEvent) => {
      if (iEvent === event) {
        return {
          ...event,
          start: newStart,
          end: newEnd,
        };
      }
      return iEvent;
    });
    this.handleEvent('Dropped or resized', event);
  }

  openAddAppointmentDialog(): void {
    if (this.vendorService.vendorData === undefined) {
      this.vendorService.getVendorByUserLogin(this.authService.getLoggedUser()?.username)
        .subscribe({
          next: (data) => {
            this.vendorService.vendorData = data;
            this.openModal();
          },
          error: (err) => this.messageService.add({severity: 'error', summary: 'Coś poszło nie tak', detail: err})
        });
    } else {
      this.openModal();
    }
  }

  private openModal() {
    if (this.authService.isManager) {
      this.employeesService.getVendorEmployeesByVendorId(this.vendorService.vendorData?.vendorId)
        .subscribe({
          next: (data) => {
            this.employeesService.employees = data;
            this.viewModal();
          },
          error: (err) => this.messageService.add({severity: 'error', summary: 'Nie udało się pobrać pracowników', detail: err.error})
        });
    } else {
      this.viewModal();
    }
  }

  private viewModal() {
    const ref = this.dialogService.open(AddAppointmentDialogComponent, {
      baseZIndex: 9999,
      closeOnEscape: true,
      maximizable: true
    });
    ref.onClose.subscribe(() => {
      this.loadAppointments();
    });
  }

}
