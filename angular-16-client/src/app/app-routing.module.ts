import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TutorialsListComponent} from './components/tutorials-list/tutorials-list.component';
import {TutorialDetailsComponent} from './components/tutorial-details/tutorial-details.component';
import {AddTutorialComponent} from './components/add-tutorial/add-tutorial.component';
import {CompanyServicesBookingComponent} from "./components/company-services-booking/company-services-booking.component";
import {LoginComponent} from "./components/login-page/login.component";
import {RegisterPageComponent} from "./components/register-page/register-page.component";
import {
  CategoriesServicesEditComponent
} from "./components/management/services-manage/categories-services-edit/categories-services-edit.component";
import {CalendarComponent} from "./components/management/calendar/calendar/calendar.component";

const routes: Routes = [
  {path: '', redirectTo: 'book', pathMatch: 'full'},
  {path: 'tutorials', component: TutorialsListComponent},
  {path: 'tutorials/:id', component: TutorialDetailsComponent},
  {path: 'add', component: AddTutorialComponent},
  {path: 'book', component: CompanyServicesBookingComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterPageComponent},
  {path: 'manage/services', component: CategoriesServicesEditComponent},
  {path: 'manage/calendar', component: CalendarComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
