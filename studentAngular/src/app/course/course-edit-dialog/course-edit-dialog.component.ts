import {Component, Inject, OnInit} from '@angular/core';
import {OneRoom} from "../../_model/one-room";
import {SelectionModel} from "@angular/cdk/collections";
import {RoomService} from "../../_service/room.service";
import {InstrumentService} from "../../_service/instrument.service";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {AddInstrumentDialogComponent} from "../../room/room-edit-dialog/add-instrument-dialog/add-instrument-dialog.component";
import {OneCourse} from "../../_model/one-course";
import {CourseService} from "../../_service/course.service";

@Component({
  selector: 'app-course-edit-dialog',
  templateUrl: './course-edit-dialog.component.html',
  styleUrls: ['./course-edit-dialog.component.scss']
})
export class CourseEditDialogComponent implements OnInit {

  data: OneCourse = new OneCourse();
  //
  // instrumentDisplayedColumns: string [] = ['select', 'idd', 'name', 'number', 'createDate'];
  selection = new SelectionModel(false, []);
  // showInstrumentTable: boolean = false;

  historyDisplayedColumns: string [] = [ 'id', 'name','description',
    'maxCountStudent', 'startDate', 'endDate', 'deleteDate'];
  showHistoryTable: boolean = false;

  constructor(
    private _courseService: CourseService,
    public dialogRef: MatDialogRef<CourseEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public idd: number,
    public dialog: MatDialog) {}

  ngOnInit(): void {
    if (this.idd) {
      this._courseService.getCourseByIdd(this.idd)
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

  // onDeleteInstrument() {
  //   this.data.instruments
  //     = this.data.instruments.filter(obj => obj.idd !== this.selection.selected[0].idd);
  //   this.selection.clear();
  // }

  // onAddInstrument() {
  //   const dialogRef = this.dialog.open(AddInstrumentDialogComponent, {
  //     width: '750px',
  //   });

    // dialogRef.afterClosed().subscribe(result => {
    //   this.data.instruments.push(result);
    // });
  // }
}

