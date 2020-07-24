import { Component, OnInit } from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {LessonList} from "../../../_model/lesson-list";
import {LessonService} from "../../../_service/lesson.service";

@Component({
  selector: 'app-add-lesson-dialog',
  templateUrl: './add-lesson-dialog.component.html',
  styleUrls: ['./add-lesson-dialog.component.scss']
})
export class AddLessonDialogComponent implements OnInit {
  lessons: LessonList[] = [];
  selected:LessonList;

  constructor(public dialogRef: MatDialogRef<AddLessonDialogComponent>, private _lessonService:LessonService) { }

  ngOnInit(): void {
    this._lessonService.getLessonList(null, null, 0, 1000)
      .pipe()
      .subscribe(res => this.lessons = res.list)
  }

  onCancelClick() {
    this.dialogRef.close();
  }

  onSaveClick() {
    this.dialogRef.close(this.selected);
  }
}
