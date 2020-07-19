import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MatTable} from '@angular/material/table';
import {SelectionModel} from '@angular/cdk/collections';
import {InstrumentService} from '../../../_service/instrument.service';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {RoomEditDialogComponent} from '../../room/room-edit-dialog/room-edit-dialog.component';
import {CourseService} from '../../../_service/course.service';
import {Course} from '../../../_model/course';

@Component({
  selector: 'app-course-edit-dialog',
  templateUrl: './course-edit-dialog.component.html',
  styleUrls: ['./course-edit-dialog.component.scss']
})
export class CourseEditDialogComponent implements OnInit {

  @ViewChild(MatTable) instrumentTable: MatTable<Course>;

  data: Course = new Course();

  instrumentsDisplayedColumns: string [] = ['select', 'idd', 'name', 'number', 'createDate'];
  selection = new SelectionModel(false, []);

  constructor(
    private courseService: CourseService,
    private instrumentService: InstrumentService,
    public dialogRef: MatDialogRef<RoomEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public idd: number,
    public dialog: MatDialog) {
  }


  ngOnInit(): void {
    if (this.idd) {
      this.courseService.getCourseByIdd(this.idd)
        .pipe()
        .subscribe(course => {
          this.data = course;
        });
    }
  }

  onCancelClick() {
    this.dialogRef.close();
  }

  onSaveClick() {
    if (this.idd) {
      this.courseService.updateCourse(this.idd, this.data)
        .toPromise()
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    } else {
      this.courseService.createCourse(this.data)
        .toPromise()
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    }
  }

  // onDeleteInstrument() {
  //   this.data.instruments
  //     = this.data.instruments.filter(obj => obj.idd !== this.selection.selected[0].idd);
  //   this.selection.clear();
  //   this.instrumentTable.renderRows();
  // }
  //
  // onAddInstrument() {
  //   const dialogRef = this.dialog.open(AddInstrumentDialogComponent, {
  //     width: '750px'
  //   });
  //
  //   dialogRef.afterClosed().subscribe(result => {
  //     if (result) {
  //       this.data.instruments.push(result);
  //       this.instrumentTable.renderRows();
  //     }
  //   });
  // }

}
