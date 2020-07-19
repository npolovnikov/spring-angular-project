import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RoomComponent } from './report/room/room.component';
import {MatPaginatorModule} from '@angular/material/paginator';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatSortModule} from "@angular/material/sort";
import {MatTableModule} from "@angular/material/table";
import { InstrumentComponent } from './report/instrument/instrument.component';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import { RoomEditDialogComponent } from './report/room/room-edit-dialog/room-edit-dialog.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatDialogModule} from "@angular/material/dialog";
import { AddInstrumentDialogComponent } from './report/room/room-edit-dialog/add-instrument-dialog/add-instrument-dialog.component';
import {MatSelectModule} from '@angular/material/select';
import { StudentComponent } from './report/student/student.component';
import {MatTabsModule} from '@angular/material/tabs';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ErrorDialogComponent } from './dialogs/error-dialog/error-dialog.component';
import { LoginComponent } from './login/login.component';
import {AuthInterceptor} from "./_service/auth.interceptor";
import { StudentEditDialogComponent } from './report/student/student-edit-dialog/student-edit-dialog.component';
import { CourseComponent } from './report/course/course.component';
import { CourseEditDialogComponent } from './report/course/course-edit-dialog/course-edit-dialog.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';
import { AddEntityDialogComponent } from './dialogs/add-entity-dialog/add-entity-dialog.component';
import { UsersComponent } from './report/users/users.component';
import { UserAddDialogComponent } from './report/users/user-add-dialog/user-add-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    RoomComponent,
    InstrumentComponent,
    RoomEditDialogComponent,
    AddInstrumentDialogComponent,
    InstrumentComponent,
    StudentComponent,
    ErrorDialogComponent,
    LoginComponent,
    StudentEditDialogComponent,
    CourseComponent,
    CourseEditDialogComponent,
    AddEntityDialogComponent,
    UsersComponent,
    UserAddDialogComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatPaginatorModule,
    MatSortModule,
    MatTableModule,
    MatProgressSpinnerModule,
    HttpClientModule,
    MatIconModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatDialogModule,
    FormsModule,
    MatCheckboxModule,
    MatSelectModule,
    MatTabsModule,
    NgbModule,
    ReactiveFormsModule,
    MatDatepickerModule,
    MatNativeDateModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi:true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
