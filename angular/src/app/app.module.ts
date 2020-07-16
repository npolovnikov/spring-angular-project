import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RoomComponent } from './room/room.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatSortModule} from "@angular/material/sort";
import {MatTableModule} from "@angular/material/table";
import {MatPaginatorModule} from "@angular/material/paginator";
import { InstrumentComponent } from './instrument/instrument.component';
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import { RoomEditDialogComponent } from './room/room-edit-dialog/room-edit-dialog.component';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatDialogModule} from "@angular/material/dialog";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatCheckboxModule} from "@angular/material/checkbox";
import { AddInstrumentDialogComponent } from './room/room-edit-dialog/add-instrument-dialog/add-instrument-dialog.component';
import {MatSelectModule} from "@angular/material/select";
import { ErrorDialogComponent } from './error-dialog/error-dialog.component';
import { LoginComponent } from './login/login.component';
import {AuthInterceptor} from "./_service/auth.interceptor";

@NgModule({
  declarations: [
    AppComponent,
    RoomComponent,
    InstrumentComponent,
    RoomEditDialogComponent,
    AddInstrumentDialogComponent,
    ErrorDialogComponent,
    LoginComponent
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
    ReactiveFormsModule,
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
