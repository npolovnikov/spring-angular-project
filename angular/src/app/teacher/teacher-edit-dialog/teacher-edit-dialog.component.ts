import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {SelectionModel} from "@angular/cdk/collections";
import {TeacherService} from "../../_service/teacher.service";
import {NativeDateAdapter, DateAdapter, MAT_DATE_FORMATS} from '@angular/material/core';
import {formatDate} from '@angular/common';
import {Teacher} from "../../_model/teacher";

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
  selector: 'app-teacher-edit-dialog',
  templateUrl: './teacher-edit-dialog.component.html',
  styleUrls: ['./teacher-edit-dialog.component.scss'],
  providers: [
    {provide: DateAdapter, useClass: PickDateAdapter},
    {provide: MAT_DATE_FORMATS, useValue: PICK_FORMATS}
  ]
})
export class TeacherEditDialogComponent implements OnInit {

  data: Teacher = new Teacher();
  selection = new SelectionModel(false, []);

  birthDate: Date;

  historyDisplayedColumns: string[] = ['id', 'lastName', 'firstName', 'middleName', 'passport', 'birthDate', 'createDate'];
  showHistoryTable:boolean = false;

  constructor(
    private _teacherService: TeacherService,
    public dialogRef: MatDialogRef<TeacherEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public idd: number,
    public dialog: MatDialog) {
  }


  ngOnInit(): void {
    if (this.idd) {
      this._teacherService.getTeacherByIdd(this.idd)
        .pipe()
        .subscribe(course => {
          this.data = course;
          this.birthDate = this.data.birthDate != null ? new Date(this.data.birthDate) : null;
        });
    }
  }

  onCancelClick() {
    this.dialogRef.close();
  }

  onSaveClick() {
    this.data.birthDate = formatDate(this.birthDate, 'yyyy-MM-dd HH:mm', 'en-US').toString().substr(0, 16);
    if (this.idd) {
      this._teacherService.updateTeacher(this.idd, this.data)
        .toPromise()
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    } else {
      this._teacherService.createTeacher(this.data)
        .toPromise()
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    }
  }

  setShowHistoryTable() {
    this.showHistoryTable = !this.showHistoryTable;
  }
}
