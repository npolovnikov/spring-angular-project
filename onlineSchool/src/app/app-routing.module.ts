import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RoomComponent} from "./room/room.component";
import {HelpPageComponent} from "./help/helpPage.component";
// @ts-ignore
import {MainPageComponent} from './mainPage/mainPage.component';
import {TeacherComponent} from './teacher/teacher.component';


const routes: Routes = [
  {path:'main', component:MainPageComponent},
  {path:'room', component:RoomComponent},
  {path:'help', component:HelpPageComponent},
  {path:'teacher', component:TeacherComponent},

{path: '**', redirectTo:'/main'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
