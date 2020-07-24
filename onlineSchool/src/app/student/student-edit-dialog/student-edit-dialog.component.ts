import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {student} from "../../_model/student";
import {StudentService} from "../../_sevice/student.service";

@Component({
  selector: 'app-student-edit-dialog',
  templateUrl: './student-edit-dialog.component.html',
  styleUrls: ['./student-edit-dialog.component.scss']
})
export class StudentEditDialogComponent implements OnInit {
  data:student = new student();
  historyDisplayedColumns: string[] = ['id', 'firstName', 'middleName',
    'lastName', 'passport', 'birthDate', 'deleteDate', 'status'];
  showHistoryTable:boolean = false;
  constructor(
    private _studentService:StudentService,
    public dialogRef: MatDialogRef<StudentEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public idd: number) {}

  ngOnInit(): void {
    if(this.idd){
      this._studentService.getStudentByIdd(this.idd)
        .pipe()
        .subscribe(student=>this.data = student);
    }
  }

  setShowHistoryTable() {
    this.showHistoryTable = !this.showHistoryTable;
  }

  onCancelClick() {
    this.dialogRef.close();
  }

  onSaveClick() {
    if(this.idd){
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
