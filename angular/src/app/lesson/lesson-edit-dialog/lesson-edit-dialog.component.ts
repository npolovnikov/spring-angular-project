import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {SelectionModel} from "@angular/cdk/collections";
import {LessonService} from "../../_service/lesson.service";
import {NativeDateAdapter, DateAdapter, MAT_DATE_FORMATS} from '@angular/material/core';
import {formatDate, Time} from '@angular/common';
import {Lesson} from "../../_model/lesson";
import {RoomList} from "../../_model/room-list";
import {RoomService} from "../../_service/room.service";

export const PICK_FORMATS = {
  parse: {dateInput: {month: 'short', year: 'numeric', day: 'numeric'}},
  display: {
    dateInput: 'input',
    monthYearLabel: {year: 'numeric', month: 'short'},
    dateA11yLabel: {year: 'numeric', month: 'long', day: 'numeric'},
    monthYearA11yLabel: {year: 'numeric', month: 'long'}
  }
};

class PickDateAdapter extends NativeDateAdapter {
  format(date: Date, displayFormat: Object): string {
    if (displayFormat === 'input') {
      return formatDate(date, 'yyyy-MM-dd', this.locale);
    } else {
      return date.toDateString();
    }
  }
}

@Component({
  selector: 'app-lesson-edit-dialog',
  templateUrl: './lesson-edit-dialog.component.html',
  styleUrls: ['./lesson-edit-dialog.component.scss'],
  providers: [
    {provide: DateAdapter, useClass: PickDateAdapter},
    {provide: MAT_DATE_FORMATS, useValue: PICK_FORMATS}
  ]
})
export class LessonEditDialogComponent implements OnInit {

  data: Lesson = new Lesson();
  selection = new SelectionModel(false, []);

  rooms: RoomList[];
  selectedRoom: RoomList;

  lessonDateStart: Date;
  lessonTimeStart: Time;
  lessonDateEnd: Date;
  lessonTimeEnd: Time;

  historyDisplayedColumns: string[] = ['id', 'name', 'description', 'lessonDateStart', 'lessonDateEnd', 'createDate'];
  showHistoryTable:boolean = false;

  constructor(
    private _lessonService: LessonService,
    private _roomService: RoomService,
    public dialogRef: MatDialogRef<LessonEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public incomingData: { lessonIdd: number, courseIdd: number },
    public dialog: MatDialog) {
  }


  ngOnInit(): void {
    this._roomService.getRoomList(null, null, 0, 1000)
      .pipe()
      .subscribe(res => this.rooms = res.list);
    if (this.incomingData.lessonIdd) {
      this._lessonService.getLessonByIdd(this.incomingData.lessonIdd)
        .pipe()
        .subscribe(course => {
          this.data = course;
          this.selectedRoom = this.rooms.find(c => c.idd == this.data.roomIdd);
          this.lessonDateStart = this.data.lessonDateStart != null ? new Date(this.data.lessonDateStart) : null;
          this.lessonDateEnd = this.data.lessonDateEnd != null ? new Date(this.data.lessonDateEnd) : null;
        });
    }
  }

  onCancelClick() {
    this.dialogRef.close();
  }

  onSaveClick() {
    this.data.lessonDateStart = formatDate(this.lessonDateStart, 'yyyy-MM-dd ', 'en-US').toString().substr(0, 11) + this.lessonTimeStart;
    this.data.lessonDateEnd = formatDate(this.lessonDateEnd, 'yyyy-MM-dd HH:mm', 'en-US').toString().substr(0, 11) + this.lessonTimeEnd;
    this.data.courseIdd = this.incomingData.courseIdd;
    this.data.roomIdd = this.selectedRoom.idd;
    if (this.incomingData.lessonIdd) {
      this._lessonService.updateLesson(this.incomingData.lessonIdd, this.data)
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

  setShowHistoryTable() {
    this.showHistoryTable = !this.showHistoryTable;
  }
}
