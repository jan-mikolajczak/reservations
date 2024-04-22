import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {AddTutorialComponent} from './components/add-tutorial/add-tutorial.component';
import {TutorialDetailsComponent} from './components/tutorial-details/tutorial-details.component';
import {TutorialsListComponent} from './components/tutorials-list/tutorials-list.component';
import {CompanyServicesBookingComponent} from './components/company-services-booking/company-services-booking.component';
import {DataViewModule} from "primeng/dataview";
import {RatingModule} from "primeng/rating";
import {TagModule} from "primeng/tag";
import {ServiceService} from "./services/service.service";
import {ButtonModule} from "primeng/button";
import {CompanyServicesComponent} from './components/company-services-booking/company-services/company-services.component';
import {GalleriaModule} from "primeng/galleria";
import {CompanyBrandingService} from "./services/company-branding.service";
import {CardModule} from "primeng/card";
import {AvatarModule} from "primeng/avatar";
import {WorkersComponent} from './components/company-services-booking/company-branding/workers/workers.component';
import {AboutCompanyComponent} from './components/company-services-booking/company-branding/about-company/about-company.component';
import {
  ContactAndWorkingHoursComponent
} from './components/company-services-booking/company-branding/contact-and-working-hours/contact-and-working-hours.component';
import {GaleriaComponent} from './components/company-services-booking/company-branding/galeria/galeria.component';
import {LoginComponent} from './components/login-page/login.component';
import {PasswordModule} from "primeng/password";
import {RippleModule} from "primeng/ripple";
import {InputTextModule} from "primeng/inputtext";
import {CheckboxModule} from "primeng/checkbox";
import {MessageService} from "primeng/api";
import {ToastModule} from "primeng/toast";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RegisterPageComponent} from './components/register-page/register-page.component';
import {AuthInterceptorService} from "./services/auth.interceptor.service";
import {TableModule} from "primeng/table";
import {DividerModule} from "primeng/divider";
import { CategoriesServicesEditComponent } from './components/management/services-manage/categories-services-edit/categories-services-edit.component';

@NgModule({
  declarations: [
    AppComponent,
    AddTutorialComponent,
    TutorialDetailsComponent,
    TutorialsListComponent,
    CompanyServicesBookingComponent,
    CompanyServicesComponent,
    WorkersComponent,
    AboutCompanyComponent,
    ContactAndWorkingHoursComponent,
    GaleriaComponent,
    LoginComponent,
    RegisterPageComponent,
    CategoriesServicesEditComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        HttpClientModule,
        DataViewModule,
        RatingModule,
        TagModule,
        ButtonModule,
        GalleriaModule,
        CardModule,
        AvatarModule,
        ReactiveFormsModule,
        PasswordModule,
        RippleModule,
        InputTextModule,
        CheckboxModule,
        ToastModule,
        BrowserAnimationsModule,
        TableModule,
        DividerModule
    ],
  providers: [ServiceService, CompanyBrandingService, MessageService, {
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptorService,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
