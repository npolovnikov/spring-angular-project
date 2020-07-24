import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";

@Component({
  selector: 'app-sub-menu',
  templateUrl: './sub-menu.component.html',
  styleUrls: ['./sub-menu.component.scss']
})
export class SubMenuComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  showCourse() {
    this.router.navigate(['/course']);
  }

  showInstrument() {
    this.router.navigate(['/instrument']);
  }

  showLesson() {
    this.router.navigate(['/lesson']);
  }

  showRoom() {
    this.router.navigate(['/room']);
  }

  showStudent() {
    this.router.navigate(['/student']);
  }

  showTeacher() {
    this.router.navigate(['/teacher']);
  }
}
