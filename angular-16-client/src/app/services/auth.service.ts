import {Injectable, OnDestroy} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject, Observable, Subscription} from "rxjs";
import {URL} from "../app.constants";
import {LoginRequest, LoginResponse} from "../models/login.model";
import {JwtHelperService} from "@auth0/angular-jwt";
import {LoggedUser} from "../models/logged-user.model";
import {UserRole} from "../constants/role.constants";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService implements OnDestroy {

  jwtHelperService = new JwtHelperService();
  user = new BehaviorSubject<LoggedUser | null>(null);
  tokenExpirationTimer: any;
  isManager = false;
  isEmployee = false;
  userSub!: Subscription;
  isAuthenticated = false;

  constructor(private http: HttpClient, private router: Router) {
    this.initRoles();
  }

  ngOnDestroy() {
    if (this.userSub) {
      this.userSub.unsubscribe();
    }
  }

  public login(user: LoginRequest): Observable<LoginResponse> {
    const formData = new FormData();
    formData.append('username', user.username);
    formData.append('password', user.password);
    return this.http.post<LoginResponse>(`${URL}/login`, formData)
  }

  saveToken(jwtTokens: LoginResponse) {
    const decodedAccessToken = this.jwtHelperService.decodeToken(jwtTokens.accessToken);
    const loggedUser = new LoggedUser(decodedAccessToken.sub, decodedAccessToken.roles, jwtTokens.accessToken, this.getExpirationDate(decodedAccessToken.exp));
    localStorage.setItem('userData', JSON.stringify(loggedUser));
    console.log(loggedUser);
    this.user.next(loggedUser);
    this.redirectLoggedInUser(decodedAccessToken, jwtTokens.accessToken);
    this.autoLogout(this.getExpirationDate(decodedAccessToken.exp).valueOf() - new Date().valueOf());
    // this.redirectLoggedInUser(decodedAccessToken, jwtTokens.accessToken)
  }

  getExpirationDate(exp: number) {
    const date = new Date(0);
    date.setUTCSeconds(exp)
    return date;
  }

  redirectLoggedInUser(decodedToken: any, accessToken: string) {
    if (decodedToken.roles.includes(UserRole.MANAGER)) this.router.navigate(['book']);
    /*    else if (decodedToken.roles.includes("Instructor"))
          this.instructorService.loadInstructorByEmail(decodedToken.sub).subscribe(instructor => {
            const loggedUser = new LoggedUser(decodedToken.sub, decodedToken.roles, accessToken, this.getExpirationDate(decodedToken.exp), undefined, instructor);
            this.user.next(loggedUser);
            localStorage.setItem('userData', JSON.stringify(loggedUser));
            this.router.navigateByUrl("/instructor-courses/" + instructor.instructorId)
          })*/
  }

  autoLogin() {
    const userData: {
      username: string,
      roles: string[],
      _token: string,
      _expiration: Date,
    } = JSON.parse(localStorage.getItem('userData')!);
    if (!userData) return;
    const loadedUser = new LoggedUser(userData.username, userData.roles, userData._token, new Date(userData._expiration))
    if (loadedUser.token) {
      this.user.next(loadedUser);
      this.autoLogout(loadedUser._expiration.valueOf() - new Date().valueOf());
    }
  }

  logout() {
    localStorage.removeItem('userData');
    this.user.next(null);
    this.router.navigate(['/'])
    if (this.tokenExpirationTimer) {
      clearTimeout(this.tokenExpirationTimer);
    }
    this.tokenExpirationTimer = null;
  }

  autoLogout(expirationDuration: number) {
    this.tokenExpirationTimer = setTimeout(() => {
      this.logout();
    }, expirationDuration)
  }

  getLoggedUser() {
    const userData: {
      username: string,
      roles: string[],
      _token: string,
      _expiration: Date,
    } = JSON.parse(localStorage.getItem('userData')!);
    const loadedUser = new LoggedUser(userData.username, userData.roles, userData._token, new Date(userData._expiration))
    if (loadedUser.token) {
      return loadedUser;
    } else {
      return null;
    }
  }

  initRoles(): void {
    this.userSub = this.user.subscribe(data => {
      this.isAuthenticated = !!data;
      if (!this.isAuthenticated) {
        this.initializeState();
      } else if (!!data)
        this.setRole(data);
    })
  }

  initializeState() {
    this.isEmployee = false;
    this.isManager = false;
  }

  setRole(loggedUser: LoggedUser) {
    if (loggedUser?.roles.includes(UserRole.MANAGER)) {
      this.isManager = true;
    } else if (loggedUser?.roles.includes(UserRole.EMPLOYEE)) {
      this.isEmployee = true;
    }
  }
}
