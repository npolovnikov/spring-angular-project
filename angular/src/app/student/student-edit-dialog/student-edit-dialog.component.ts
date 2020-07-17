import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {SelectionModel} from "@angular/cdk/collections";
import {StudentService} from "../../_service/student.service";
import {NativeDateAdapter, DateAdapter, MAT_DATE_FORMATS} from '@angular/material/core';
import {formatDate} from '@angular/common';
import {Student} from "../../_model/student";
import {CourseList} from "../../_model/course-list";
import {MatTable} from "@angular/material/table";
import {AddStudentToCourseDialogComponent} from "./add-student-to-course-dialog/add-student-to-course-dialog.component";

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
  selector: 'app-student-edit-dialog',
  templateUrl: './student-edit-dialog.component.html',
  styleUrls: ['./student-edit-dialog.component.scss'],
  providers: [
    {provide: DateAdapter, useClass: PickDateAdapter},
    {provide: MAT_DATE_FORMATS, useValue: PICK_FORMATS}
  ]
})
export class StudentEditDialogComponent implements OnInit {
  @ViewChild(MatTable) courseTable: MatTable<CourseList>;

  data: Student = new Student();
  selection = new SelectionModel(false, []);

  birthDate: Date;

  // studentCourses: CourseList[];

  studentCoursesDisplayedColumns: string[] = ['select', 'idd', 'name', 'startDate', 'endDate', 'createDate'];
  showCourseTable:boolean = false;

  historyDisplayedColumns: string[] = ['id', 'lastName', 'firstName', 'middleName', 'passport', 'birthDate', 'createDate'];
  showHistoryTable:boolean = false;

  constructor(
    private _studentService: StudentService,
    public dialogRef: MatDialogRef<StudentEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public idd: number,
    public dialog: MatDialog) {
  }

  ngOnInit(): void {
    if (this.idd) {
      this._studentService.getStudentByIdd(this.idd)
        .pipe()
        .subscribe(course => {
          this.data = course;
          this.birthDate = this.data.birthDate != null ? new Date(this.data.birthDate) : null;
        });
    } else {
      this.data.courses = [];
    }
  }

  onCancelClick() {
    this.dialogRef.close();
  }

  onSaveClick() {
    this.data.birthDate = formatDate(this.birthDate, 'yyyy-MM-dd HH:mm', 'en-US').toString().substr(0, 16);
    if (this.idd) {
      this._studentService.updateStudent(this.idd, this.data)
        .toPromise()
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    } else {
      this._studentService.createStudent(this.data)
        .toPromise()
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    }
    // Сохранить инфу о записи на курсы
  }

  setShowHistoryTable() {
    this.showHistoryTable = !this.showHistoryTable;
  }

  setShowCourseTable() {
    this.showCourseTable = !this.showCourseTable;
  }

  onDeleteFromCourse() {
    this.data.courses
      = this.data.courses.filter(obj => obj.idd !== this.selection.selected[0].idd);
    this.selection.clear();
    this.courseTable.renderRows();
  }

  onAddToCourse() {
    const dialogRef = this.dialog.open(AddStudentToCourseDialogComponent, {
      width: '750px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.data.courses.push(result);
        this.courseTable.renderRows();
      }
    });
  }
}
