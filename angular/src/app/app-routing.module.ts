import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RoomComponent} from "./room/room.component";
import {InstrumentComponent} from "./instrument/instrument.component";
import {StudentComponent} from "./student/student.component";



const routes: Routes = [
  {path:'room', component:RoomComponent},
  {path:'instrument', component:InstrumentComponent},
  {path:'student', component:StudentComponent},
  {path:'**', redirectTo:'/room'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
