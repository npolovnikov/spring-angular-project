import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {RoomComponent} from './room/room.component';
import {HttpClientModule} from "@angular/common/http";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatSortModule} from "@angular/material/sort";
import {MatTableModule} from "@angular/material/table";
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import { UserComponent } from './user/user.component';
import { StudentComponent } from './student/student.component';
import { TeacherComponent } from './teacher/teacher.component';
import { LessonComponent } from './lesson/lesson.component';
import { InstrumentComponent } from './instrument/instrument.component';
import { CourseComponent } from './course/course.component';
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import { RoomEditDialogComponent } from './room/room-edit-dialog/room-edit-dialog.component';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatDialogModule} from "@angular/material/dialog";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { LoginComponent } from './login/login.component';
import { signInComponent } from './sign-in/sign-in.component';
import { ChoiceComponent } from './choice/choice.component';
import {MatCheckboxModule} from "@angular/material/checkbox";
import { UserEditDialogComponent } from './user/user-edit-dialog/user-edit-dialog.component';
import { StudentEditDialogComponent } from './student/student-edit-dialog/student-edit-dialog.component';
import { TeacherEditDialogComponent } from './teacher/teacher-edit-dialog/teacher-edit-dialog.component';
import { CourseEditDialogComponent } from './course/course-edit-dialog/course-edit-dialog.component';
import { InstrumentEditDialogComponent } from './instrument/instrument-edit-dialog/instrument-edit-dialog.component';



@NgModule({
  declarations: [
    AppComponent,
    RoomComponent,
    UserComponent,
    StudentComponent,
    TeacherComponent,
    LessonComponent,
    InstrumentComponent,
    CourseComponent,
    RoomEditDialogComponent,
    LoginComponent,
    signInComponent,
    ChoiceComponent,
    UserEditDialogComponent,
    StudentEditDialogComponent,
    TeacherEditDialogComponent,
    CourseEditDialogComponent,
    InstrumentEditDialogComponent,
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatProgressSpinnerModule,
        MatPaginatorModule,
        MatSortModule,
        MatTableModule,
        MatButtonModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        MatDialogModule,
        FormsModule,
        ReactiveFormsModule,
        MatCheckboxModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
