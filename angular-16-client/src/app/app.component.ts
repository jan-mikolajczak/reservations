import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from "./services/auth.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {

  title = 'No Reservations';
  userSub!: Subscription;
  isAuthenticated = false;

  constructor(protected authService: AuthService) {
  }

  ngOnInit(): void {
        this.userSub = this.authService.user.subscribe(loggedUser => {
          this.isAuthenticated = !!loggedUser;
        });
        this.authService.autoLogin();
    }

  logout() {
    this.authService.logout();
  }

  ngOnDestroy(): void {
    this.userSub.unsubscribe();
  }

}
