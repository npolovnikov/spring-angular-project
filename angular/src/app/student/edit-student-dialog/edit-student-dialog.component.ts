import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MatTable} from "@angular/material/table";
import {SelectionModel} from "@angular/cdk/collections";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {Student} from "../../_model/student/student";
import {StudentService} from "../../_service/student/student.service";
import {CourseService} from "../../_service/course/course.service";
import {AddCourseDialogComponent} from "../../add-course-dialog/add-course-dialog.component";
import {CourseList} from "../../_model/course/courseList";
import {formatDate} from "@angular/common";

@Component({
  selector: 'app-edit-student-dialog',
  templateUrl: './edit-student-dialog.component.html',
  styleUrls: ['./edit-student-dialog.component.scss']
})
export class EditStudentDialogComponent implements OnInit {
  @ViewChild(MatTable) courseTable: MatTable<CourseList>;

  data:Student = new Student();

  birthDate: Date;

  coursesDisplayedColumns: string [] = ['select', 'idd', 'name', 'description', 'maxCountStudent', 'startDate', 'endDate', 'createDate'];
  selection = new SelectionModel(false, []);
  showCourseTable:boolean = false;

  historyDisplayedColumns: string[] = ['id', 'firstName', 'middleName', 'lastName', 'passport', 'birthDate', 'deleteDate'];
  showHistoryTable:boolean = false;

  constructor(
    private _studentService:StudentService,
    private _courseService:CourseService,
    public dialogRef: MatDialogRef<EditStudentDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public idd: number,
    public dialog: MatDialog) {}


  ngOnInit(): void {
    if (this.idd) {
      this._studentService.getByIdd(this.idd)
        .pipe()
        .subscribe(student => {
          this.data = student;
          this.birthDate = this.data.birthDate != null ? new Date(this.data.birthDate) : null;
        });
    } else {
      this.data.courses = [];
    }
  }

  setShowCourseTable() {
    this.showCourseTable = !this.showCourseTable;
  }

  setShowHistoryTable() {
    this.showHistoryTable = !this.showHistoryTable;
  }

  onCancelClick() {
    this.dialogRef.close();
  }

  onSaveClick() {
    this.data.birthDate = formatDate(this.birthDate, 'yyyy-MM-dd HH:mm', 'en-US').toString().substr(0, 16);
    if (this.idd){
      this._studentService.update(this.idd, this.data)
        .toPromise()
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    } else {
      this._studentService.create(this.data)
        .toPromise()
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    }
  }

  onDeleteCourse() {
    this.data.courses
      = this.data.courses.filter(obj => obj.idd !== this.selection.selected[0].idd);
    this.selection.clear();
    this.courseTable.renderRows();
  }

  onAddInstrument() {
    const dialogRef = this.dialog.open(AddCourseDialogComponent, {
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
