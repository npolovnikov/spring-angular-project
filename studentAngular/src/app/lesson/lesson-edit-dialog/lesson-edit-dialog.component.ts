import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {LessonService} from "../../_service/lesson.service";
import {SelectionModel} from "@angular/cdk/collections";
import {OneLesson} from "../../_model/one-lesson";

@Component({
  selector: 'app-lesson-edit-dialog',
  templateUrl: './lesson-edit-dialog.component.html',
  styleUrls: ['./lesson-edit-dialog.component.scss']
})
export class LessonEditDialogComponent implements OnInit {
  data: OneLesson = new OneLesson();
  selection = new SelectionModel(false, []);
  historyDisplayedColumns: string [] = ['id', 'name','description',
    'extraInstruments', 'lessonDateStart', 'lessonDateEnd'];
  showHistoryTable: boolean = false;

  constructor(private _lessonService: LessonService,
              public dialogRef: MatDialogRef<LessonEditDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public id: number,
              public dialog: MatDialog) { }

  ngOnInit(): void {
    if (this.id) {
      this._lessonService.getLessonByIdd(this.id)
        .pipe()
        .subscribe(room => {this.data = room});
    } else {
      this.data.history = [];
    }

  }

  setShowHistoryTable() {
    this.showHistoryTable = !this.showHistoryTable;
  }

  onCancelClick() {
    this.dialogRef.close();
  }

  onSaveClick() {
    if (this.id) {
      this._lessonService.updateLesson(this.id, this.data)
        .toPromise()
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    } else {
      this._lessonService.createLesson(this.data)
        .toPromise()
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    }
  }

}
