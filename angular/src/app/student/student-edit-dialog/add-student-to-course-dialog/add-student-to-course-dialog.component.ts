import { Component, OnInit } from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {CourseList} from "../../../_model/course-list";
import {CourseService} from "../../../_service/course.service";

@Component({
  selector: 'app-add-student-to-course-dialog',
  templateUrl: './add-student-to-course-dialog.component.html',
  styleUrls: ['./add-student-to-course-dialog.component.scss']
})
export class AddStudentToCourseDialogComponent implements OnInit {
  courses: CourseList[] = [];
  selected:number;

  constructor(public dialogRef: MatDialogRef<AddStudentToCourseDialogComponent>, private _courseService: CourseService) { }

  ngOnInit(): void {
    this._courseService.getCourseList(null, null, 0, 1000)
      .pipe()
      .subscribe(res => this.courses = res.list)
  }

  onCancelClick() {
    this.dialogRef.close();
  }

  onSaveClick() {
    this.dialogRef.close(this.selected);
  }
}
