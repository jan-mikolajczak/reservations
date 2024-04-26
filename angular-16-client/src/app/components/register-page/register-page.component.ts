import { Component } from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../services/auth.service";
import {MessageService} from "primeng/api";
import {RegisterService} from "../../services/register.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css']
})
export class RegisterPageComponent {
  registerForm: FormGroup;
  submitted: boolean = false;

  constructor(
    private fb: FormBuilder,
    private registerService: RegisterService,
    private messageService: MessageService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      companyName: ["", Validators.required],
      nip: ["", Validators.required],
      fullName: ["", Validators.required],
      phoneNumber: ["", Validators.required],
      email: ["", [Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]],
      password: ["", Validators.required],
      confirmPassword: ["", Validators.required]
    }, {validators: this.passwordMatchValidator});
  }

  onSubmit() {
    this.submitted = true;
    if (this.registerForm.invalid) return;
    this.registerService.registerUser(this.registerForm.value).subscribe({
      next: loginResponse => {
        this.messageService.add({severity: 'success', summary: 'Success', detail: 'Konto założone'});
        this.router.navigate(['/login']);
      },
      error: err => {
        this.messageService.add({severity: 'error', summary: 'Error', detail: err.error});
      }
    });
  }

  passwordMatchValidator(control: AbstractControl): { [key: string]: boolean } | null {
    const password = control.get('password');
    const confirmPassword = control.get('confirmPassword');

    if (password?.value !== confirmPassword?.value) {
      confirmPassword?.setErrors({ passwordMismatch: true });
      return { passwordMismatch: true };
    } else {
      confirmPassword?.setErrors(null);
      return null;
    }
  }

}
