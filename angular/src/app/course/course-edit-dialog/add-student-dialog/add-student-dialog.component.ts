import { Component, OnInit } from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {StudentList} from "../../../_model/student-list";
import {StudentService} from "../../../_service/student.service";

@Component({
  selector: 'app-add-student-dialog',
  templateUrl: './add-student-dialog.component.html',
  styleUrls: ['./add-student-dialog.component.scss']
})
export class AddStudentDialogComponent implements OnInit {
  students: StudentList[] = [];
  selected:StudentList;

  constructor(public dialogRef: MatDialogRef<AddStudentDialogComponent>, private _studentService:StudentService) { }

  ngOnInit(): void {
    this._studentService.getStudentList(null, null, 0, 1000)
      .pipe()
      .subscribe(res => this.students = res.list)
  }

  onCancelClick() {
    this.dialogRef.close();
  }

  onSaveClick() {
    this.dialogRef.close(this.selected);
  }
}
