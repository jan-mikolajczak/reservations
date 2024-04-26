import {Component, OnInit} from '@angular/core';
import {DynamicDialogRef} from "primeng/dynamicdialog";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CalendarEvent} from "angular-calendar";
import {AppointmentService} from "../../../../services/appointment.service";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-add-appointment-dialog',
  templateUrl: './add-appointment-dialog.component.html',
  styleUrls: ['./add-appointment-dialog.component.css']
})

export class AddAppointmentDialogComponent implements OnInit {

  appointmentForm: FormGroup;

  constructor(protected ref: DynamicDialogRef,
              private fb: FormBuilder,
              private appointmentService: AppointmentService,
              private messageService: MessageService
  ) {
    this.appointmentForm = this.fb.group({
      start: ["", Validators.required],
      end: ["", Validators.required],
      title: ["", Validators.required]
    });
  }

  ngOnInit(): void {
  }

  // Metoda do zamykania modala i przekazywania danych z powrotem do komponentu nadrzędnego
  closeDialog(): void {
    this.ref.close(/* Przekazanie danych z powrotem */);
  }

  onSave(appointmentForm: FormGroup) {
    const startDate = new Date(appointmentForm.value.start);
    const endDate = new Date(appointmentForm.value.end);

    const newAppointment: CalendarEvent = {
      start: startDate,
      end: endDate,
      title: appointmentForm.value.title,
    };

    this.appointmentService.addAppointment(newAppointment)
      .subscribe({
        next: (data) => {
          this.messageService.add({severity: 'success', summary: 'Success', detail: 'Dodano: ' + data.title});
          this.closeDialog();
        },
        error: (err) => this.messageService.add({severity: 'error', summary: 'Błąd podczas dodawania terminu:', detail: err.message})
      });
  }
}
