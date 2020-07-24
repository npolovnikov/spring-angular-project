import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Course} from "../../_model/course";
import {CourseService} from "../../_sevice/course.service";

@Component({
  selector: 'app-course-edit-dialog',
  templateUrl: './course-edit-dialog.component.html',
  styleUrls: ['./course-edit-dialog.component.scss']
})
export class CourseEditDialogComponent implements OnInit {
  data:Course = new Course();
  historyDisplayedColumns: string[] = ['id', 'name', 'description',
    'teacherId', 'maxCountOfStudent', 'startDate', 'endDate', 'deleteDate', 'status'];
  showHistoryTable:boolean = false;
  constructor(
    private _courseService:CourseService,
    public dialogRef: MatDialogRef<CourseEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public idd: number) {}

  ngOnInit(): void {
    if(this.idd){
      this._courseService.getCourseByIdd(this.idd)
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
