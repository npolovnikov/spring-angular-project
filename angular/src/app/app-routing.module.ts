import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RoomComponent} from "./room/room.component";
import {InstrumentComponent} from "./instrument/instrument.component";
import {LoginComponent} from "./login/login.component";
import {LessonComponent} from "./lesson/lesson.component";

const routes: Routes = [
  {path:'room', component:RoomComponent},
  {path:'instrument', component:InstrumentComponent},
  {path:'login', component:LoginComponent},
  {path:'lesson', component:LessonComponent},

  {path:'**', redirectTo:'/login'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
