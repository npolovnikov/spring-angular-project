import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {RoomComponent} from "./room/room.component";
import {InstrumentComponent} from "./instrument/instrument.component";
import {TeacherComponent} from "./teacher/teacher.component";
import {StudentComponent} from "./student/student.component";
import {CourseComponent} from "./course/course.component";


const routes: Routes = [
  {path: 'room', component: RoomComponent},
  {path: 'instrument', component: InstrumentComponent},
  {path: 'teacher', component: TeacherComponent},
  {path: 'student', component: StudentComponent},
  {path: 'course', component: CourseComponent},

  {path: '**', redirectTo: '/room'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
