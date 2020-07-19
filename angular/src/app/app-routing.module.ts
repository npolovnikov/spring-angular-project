import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RoomComponent} from './report/room/room.component';
import {InstrumentComponent} from './report/instrument/instrument.component';
import {StudentComponent} from './report/student/student.component';
import {LoginComponent} from './login/login.component';
import {CourseComponent} from './report/course/course.component';
import {UsersComponent} from './report/users/users.component';


const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'room', component: RoomComponent},
  {path: 'instrument', component: InstrumentComponent},
  {path: 'student', component: StudentComponent},
  {path: 'course', component: CourseComponent},
  {path: 'users', component: UsersComponent},

  {path: '**', redirectTo: '/login'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
