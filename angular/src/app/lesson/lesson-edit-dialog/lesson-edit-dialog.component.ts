import {Component, Inject, OnInit} from '@angular/core';
import {Instrument} from "../../_model/instrument";
import {InstrumentService} from "../../_service/instrument.service";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {InstrumentEditDialogComponent} from "../../instrument/instrument-edit-dialog/instrument-edit-dialog.component";
import {Lesson} from "../../_model/lesson";
import {LessonService} from "../../_service/lesson.service";

@Component({
  selector: 'app-lesson-edit-dialog',
  templateUrl: './lesson-edit-dialog.component.html',
  styleUrls: ['./lesson-edit-dialog.component.scss']
})
export class LessonEditDialogComponent implements OnInit {

  data:Lesson = new Lesson();

  historyDisplayedColumns: string[] = ['id', 'name', 'description', 'deleteDate'];
  showHistoryTable:boolean = false;


  constructor(
    private _lessonService:LessonService,
    public dialogRef: MatDialogRef<LessonEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public idd: number,
    public dialog: MatDialog) {}

  ngOnInit(): void {
    if (this.idd) {
      this._lessonService.getLessonByIdd(this.idd)
        .pipe()
        .subscribe(lesson => {this.data = lesson});
    } else {
      //TODO тут должна быть очистка history?
    }
  }

  setShowHistoryTable() {
    this.showHistoryTable = !this.showHistoryTable;
  }

  onCancelClick() {
    this.dialogRef.close();
  }

  onSaveClick() {
    if (this.idd){
      this._lessonService.updateLesson(this.idd, this.data)
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
