import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RoomComponent} from "./room/room.component";
import {InstrumentComponent} from "./instrument/instrument.component";
import {CourseComponent} from "./course/course.component";
import {LessonComponent} from "./lesson/lesson.component";
import {StudentComponent} from "./student/student.component";
import {TeacherComponent} from "./teacher/teacher.component";

const routes: Routes = [
  {path: 'room', component: RoomComponent},
  {path: 'instrument', component: InstrumentComponent},
  {path: 'course', component: CourseComponent},
  {path: 'lesson', component: LessonComponent},
  {path: 'student', component: StudentComponent},
  {path: 'teacher', component: TeacherComponent},

  {path: '**', redirectTo: '/room'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
