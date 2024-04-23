import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {MessageService} from "primeng/api";

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  loginForm: FormGroup;
  submitted: boolean = false;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router, private messageService: MessageService) {
    this.loginForm = this.fb.group({
      username: ["", [Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]],
      password: ["", Validators.required]
    });
  }

  onSubmit() {
    this.submitted = true;
    if (this.loginForm.invalid) return;
    this.authService.login(this.loginForm.value).subscribe({
      next: loginResponse => {
        console.log(loginResponse);
        this.authService.saveToken(loginResponse);
        this.messageService.add({severity: 'success', summary: 'Success', detail: 'Logowanie powiodło się'});
        this.router.navigate(['/manage/services']);
      },
      error: err => {
        this.messageService.add({severity: 'error', summary: 'Error', detail: err.message});
      }
    });
  }
}
