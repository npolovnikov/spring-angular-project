import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RoomComponent} from "./room/room.component";
import {MainComponent} from "./mainPage/main.component";


const routes: Routes = [
  {path:'room', component:RoomComponent},
  {path:'main', component:MainComponent},

  {path: '**', redirectTo:'/main'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
