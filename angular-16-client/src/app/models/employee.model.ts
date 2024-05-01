export class EmployeeDTO {
  constructor(public userId: number,
              public name: string,
              public companyName: string,
              public phoneNumber: string,
              public email: string) {
  }
}
