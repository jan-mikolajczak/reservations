import {EmployeeDTO} from "./employee.model";

export class AppointmentDTO {
  constructor(public id: number | null,
              public start: Date,
              public end: Date,
              public title: string,
              public userId: number) {
  }
}
