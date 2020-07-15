import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RoomComponent} from "./room/room.component";
import {MainPageComponent} from "./mainPage/mainPage.component";


const routes: Routes = [
  {path:'main', component:MainPageComponent},
  {path:'room', component:RoomComponent},
  {path:'lesson', component:RoomComponent},
  {path:'instrument', component:RoomComponent},

{path: '**', redirectTo:'/main'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
