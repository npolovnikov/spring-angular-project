import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {Course} from "../../_model/course";
import {CourseService} from "../../_service/course.service";
import {SelectionModel} from "@angular/cdk/collections";
import {MatTable} from "@angular/material/table";
import {CourseList} from "../../_model/course-list";

@Component({
  selector: 'app-course-edit-dialog',
  templateUrl: './course-edit-dialog.component.html',
  styleUrls: ['./course-edit-dialog.component.scss']
})
export class CourseEditDialogComponent implements OnInit {
  @ViewChild(MatTable) courseTable: MatTable<CourseList>;

  data:Course = new Course();

  /*
  courseDisplayedColumns: string [] = ['select', 'idd', 'name', 'description', 'startDate', 'endDate', 'createDate'];
  selection = new SelectionModel(false, []);
  showCourseTable:boolean = false;
*/

  historyDisplayedColumns: string[] = ['select', 'idd', 'name', 'description', 'startDate', 'endDate', 'createDate'];
  showHistoryTable:boolean = false;

  constructor(
    private _courseService:CourseService,
    public dialogRef: MatDialogRef<CourseEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public idd: number,
    public dialog: MatDialog) {}

  ngOnInit(): void {
    if (this.idd) {
      this._courseService.getCourseByIdd(this.idd)
        .pipe()
        .subscribe(course => {this.data = course});
//    } else {
  //    this.data.courses = [];
    }
  }


/*
  setShowCourseTable() {
    this.showCourseTable = !this.showCourseTable;
  }
*/

  setShowHistoryTable() {
    this.showHistoryTable = !this.showHistoryTable;
  }

  onCancelClick() {
    this.dialogRef.close();
  }

  onSaveClick() {
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

  /*
  onDeleteCourse() {
    this.data.courses
      = this.data.courses.filter(obj => obj.idd !== this.selection.selected[0].idd);
    this.selection.clear();
    this.courseTable.renderRows();
  }

  onAddCourse() {
    const dialogRef = this.dialog.open(AddCourseDialogComponent, {
      width: '750px'
    });

    dialogRef.afterClosed().subscribe(result => {
      this.data.courses.push(result);
      this.courseTable.renderRows();
    });
  }
   */
}
