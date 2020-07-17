import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RoomComponent} from "./room/room.component";
import {InstrumentComponent} from "./instrument/instrument.component";
import {CourseComponent} from "./course/course.component";
import {TeacherComponent} from "./teacher/teacher.component";
import {StudentComponent} from "./student/student.component";
import {LessonComponent} from "./lesson/lesson.component";


const routes: Routes = [
  {path:'room', component:RoomComponent},
  {path:'course', component:CourseComponent},
  {path:'instrument', component:InstrumentComponent},
  {path:'teacher', component:TeacherComponent},
  {path:'student', component:StudentComponent},
  {path:'lesson', component:LessonComponent},

  {path:'**', redirectTo:'/course'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
