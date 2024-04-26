import {Component, OnInit} from '@angular/core';
import {CalendarEvent, CalendarEventTimesChangedEvent, CalendarView} from 'angular-calendar';
import {AppointmentService} from "../../../../services/appointment.service";
import {MessageService} from "primeng/api";
import {Subject} from "rxjs";
import {isSameDay, isSameMonth} from "date-fns";
import {AddAppointmentDialogComponent} from "../add-appointment-dialog/add-appointment-dialog.component";
import {DialogService} from "primeng/dynamicdialog";

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
    private dialogService: DialogService
  ) {}

  ngOnInit(): void {
    this.loadAppointments();
  }

  setView(view: CalendarView) {
    this.view = view;
  }

  dayClicked({ date, events }: { date: Date; events: CalendarEvent[] }): void {
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
    this.modalData = { event, action };
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
    const ref = this.dialogService.open(AddAppointmentDialogComponent, {
      // header: 'Dodaj nowy termin przez serwis', // Nagłówek modala
      // width: '400px', // Szerokość modala
      // height: '700px',
      // contentStyle: { 'max-height': '350px', 'overflow': 'auto' }, // Styl treści modala
      baseZIndex: 9999, // Bazowy indeks warstwy zabezpieczającej
      // closable: false, // Czy modala można zamknąć przez ikonę zamykania
      closeOnEscape: true, // Czy modala można zamknąć przez naciśnięcie klawisza Escape
      // dismissableMask: true // Czy modala można zamknąć przez kliknięcie na tło
      maximizable: true
    });
    ref.onClose.subscribe(() => {
      this.loadAppointments();
    })
    // this.addAppointmentDialogComponent.openDialog();
  }
}
