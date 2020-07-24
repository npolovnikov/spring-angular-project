import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Teacher} from "../../_model/teacher";
import {TeacherService} from "../../_sevice/teacher.service";
import {errorObject, subscribeToPromise} from "rxjs/internal-compatibility";

@Component({
  selector: 'app-teacher-edit-dialog',
  templateUrl: './teacher-edit-dialog.component.html',
  styleUrls: ['./teacher-edit-dialog.component.scss']
})
export class TeacherEditDialogComponent implements OnInit {
  data:Teacher = new Teacher();
  historyDisplayedColumns: string[] = ['id', 'firstName', 'middleName',
    'lastName', 'passport', 'birthDate', 'deleteDate', 'status'];
  showHistoryTable:boolean = false;
  constructor(
    private _teacherService:TeacherService,
    public dialogRef: MatDialogRef<TeacherEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public idd: number) {}

  ngOnInit(): void {
    if(this.idd){
      this._teacherService.getTeacherByIdd(this.idd)
        .pipe()
        .subscribe(user=>this.data = user);
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
}
