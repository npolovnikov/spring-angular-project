import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RoomComponent} from "./room/room.component";
import {InstrumentComponent} from "./instrument/instrument.component";
import {StudentComponent} from "./student/student.component";
import {CourseComponent} from "./course/course.component";
import {LoginComponent} from "./login/login.component";


const routes: Routes = [
  {path:'student', component:StudentComponent},
  {path:'room', component:RoomComponent},
  {path:'instrument', component:InstrumentComponent},
  {path:'course', component:CourseComponent},
  {path:'login', component:LoginComponent},

  {path:'**', redirectTo:'/login'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
