import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RoomComponent} from "./room/room.component";
import {HelpPageComponent} from "./help/helpPage.component";
import {MainPageComponent} from './mainPage/mainPage.component';
import {UserComponent} from "./user/user.component";
import {TeacherComponent} from "./teacher/teacher.component";
import {StudentComponent} from "./student/student.component";
import {CourseComponent} from "./course/course.component";
import {LoginComponent} from "./login/login.component";
import {signInComponent} from "./sign-in/sign-in.component";
import {ChoiceComponent} from "./choice/choice.component";



const routes: Routes = [
  {path:'main', component:MainPageComponent},
  {path:'room', component:RoomComponent},
  {path:'help', component:HelpPageComponent},
  {path:'users', component:UserComponent},
  {path:'teacher', component:TeacherComponent},
  {path:'student', component:StudentComponent},
  {path:'course', component:CourseComponent},
  {path:'login', component:LoginComponent},
  {path:'sign', component:signInComponent},
  {path:'choice', component:ChoiceComponent},

{path: '**', redirectTo:'/main'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
