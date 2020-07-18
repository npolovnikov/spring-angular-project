import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RoomComponent} from "./room/room.component";
import {InstrumentComponent} from "./instrument/instrument.component";


const routes: Routes = [
  {path:'room', component:RoomComponent},
  {path:'instrument', component:InstrumentComponent},

  {path:'**', redirectTo:'/room'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
