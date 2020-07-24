import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {Teacher} from "../../_model/teacher";
import {TeacherService} from "../../_service/teacher.service";
import {SelectionModel} from "@angular/cdk/collections";
import {InstrumentService} from "../../_service/instrument.service";
import {MatTable} from "@angular/material/table";
import {InstrumentList} from "../../_model/instrument-list";

@Component({
  selector: 'app-teacher-edit-dialog',
  templateUrl: './teacher-edit-dialog.component.html',
  styleUrls: ['./teacher-edit-dialog.component.scss']
})
export class TeacherEditDialogComponent implements OnInit {
  @ViewChild(MatTable) instrumentTable: MatTable<InstrumentList>;

  data:Teacher = new Teacher();

  instrumentsDisplayedColumns: string [] = ['select', 'idd', 'name', 'number', 'createDate'];
  selection = new SelectionModel(false, []);
  showInstrumentTable:boolean = false;

  historyDisplayedColumns: string[] = ['id', 'number', 'block', 'deleteDate'];
  showHistoryTable:boolean = false;

  constructor(
    private _teacherService:TeacherService,
    private _instrumentService:InstrumentService,
    public dialogRef: MatDialogRef<TeacherEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public idd: number,
    public dialog: MatDialog) {}


  ngOnInit(): void {
    if (this.idd) {
      this._teacherService.getTeacherByIdd(this.idd)
        .pipe()
        .subscribe(teacher => {this.data = teacher});
    } else {
      //this.data.instruments = [];
    }
  }

  setShowInstrumentTable() {
    this.showInstrumentTable = !this.showInstrumentTable;
  }

  setShowHistoryTable() {
    this.showHistoryTable = !this.showHistoryTable;
  }

  onCancelClick() {
    this.dialogRef.close();
  }

  onSaveClick() {
    if (this.idd){
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

  onDeleteInstrument() {
    /*this.data.instruments
      = this.data.instruments.filter(obj => obj.idd !== this.selection.selected[0].idd);
    this.selection.clear();
    this.instrumentTable.renderRows();*/
  }

  onAddInstrument() {
    /*const dialogRef = this.dialog.open(AddInstrumentDialogComponent, {
      width: '750px'
    });

    dialogRef.afterClosed().subscribe(result => {
      this.data.instruments.push(result);
      this.instrumentTable.renderRows();
    });*/
  }
}
