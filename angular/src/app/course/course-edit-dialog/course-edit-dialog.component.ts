import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {Course} from "../../_model/course";
import {CourseService} from "../../_service/course.service";
import {SelectionModel} from "@angular/cdk/collections";
import {TeacherService} from "../../_service/teacher.service";
import {TeacherList} from "../../_model/teacher-list";
import { NativeDateAdapter, DateAdapter, MAT_DATE_FORMATS } from '@angular/material/core';
import { formatDate } from '@angular/common';
import {Teacher} from "../../_model/teacher";
import {Observable} from "rxjs";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

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
      return formatDate(date,'yyyy-MM-dd', this.locale);
    } else {
      return date.toDateString();
    }
  }
}

@Component({
  selector: 'app-course-edit-dialog',
  templateUrl: './course-edit-dialog.component.html',
  styleUrls: ['./course-edit-dialog.component.scss'],
  providers: [
    {provide: DateAdapter, useClass: PickDateAdapter},
    {provide: MAT_DATE_FORMATS, useValue: PICK_FORMATS}
  ]
})
export class CourseEditDialogComponent implements OnInit {

  data:Course = new Course();
  teachers: TeacherList[] = [];
  selectedTeacher: TeacherList;
  selection = new SelectionModel(false, []);

  startDate: Date;
  endDate: Date;

  constructor(
    private _courseService:CourseService,
    private _teacherService:TeacherService,
    public dialogRef: MatDialogRef<CourseEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public idd: number,
    public dialog: MatDialog) {}



  ngOnInit(): void {
    this._teacherService.getTeacherList(null, null, 0, 1000)
      .pipe()
      .subscribe(res => this.teachers = res.list);
    if (this.idd) {
      this._courseService.getCourseByIdd(this.idd)
        .pipe()
        .subscribe(course => {
          this.data = course;
          this.selectedTeacher = this.teachers.find(c => c.idd == this.data.teacherIdd);
          this.startDate = new Date(this.data.startDate);
          this.endDate = new Date(this.data.endDate);
        });
    }
  }

  onCancelClick() {
    this.dialogRef.close();
  }

  onSaveClick() {
    this.data.startDate = formatDate(this.startDate,'yyyy-MM-dd HH:mm', 'en-US').toString().substr(0,16);
    this.data.endDate = formatDate(this.endDate,'yyyy-MM-dd HH:mm', 'en-US').toString().substr(0,16);
    this.data.teacherIdd = this.selectedTeacher.idd;
    if (this.idd){
      this._courseService.updateCourse(this.idd, this.data)
        .toPromise()
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    } else {
      this._courseService.createCourse(this.data)
        .toPromise()
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    }
  }
}
