import {Component, Inject, OnInit} from '@angular/core';
import {OneLesson} from "../../_model/one-lesson";
import {SelectionModel} from "@angular/cdk/collections";
import {LessonService} from "../../_service/lesson.service";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {OneStudent} from "../../_model/one-student";
import {StudentService} from "../../_service/student.service";

@Component({
  selector: 'app-student-edit-dialog',
  templateUrl: './student-edit-dialog.component.html',
  styleUrls: ['./student-edit-dialog.component.scss']
})
export class StudentEditDialogComponent implements OnInit {
  data: OneStudent = new OneStudent();
  selection = new SelectionModel(false, []);
  historyDisplayedColumns: string [] = ['idd', 'firstName','middleName',
    'lastName', 'passport', 'status', 'birthDate', 'createDate'];
  showHistoryTable: boolean = false;

  constructor(private _studentService: StudentService,
              public dialogRef: MatDialogRef<StudentEditDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public idd: number,
              public dialog: MatDialog) { }

  ngOnInit(): void {
    if (this.idd) {
      this._studentService.getStudentByIdd(this.idd)
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
  }

}
