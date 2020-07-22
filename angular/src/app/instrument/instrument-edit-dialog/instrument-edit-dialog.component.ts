import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MatTable} from "@angular/material/table";
import {InstrumentList} from "../../_model/instrument-list";
import {Room} from "../../_model/room";
import {Instrument} from "../../_model/instrument";
import {RoomService} from "../../_service/room.service";
import {InstrumentService} from "../../_service/instrument.service";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-instrument-edit-dialog',
  templateUrl: './instrument-edit-dialog.component.html',
  styleUrls: ['./instrument-edit-dialog.component.scss']
})
export class InstrumentEditDialogComponent implements OnInit {

  data:Instrument = new Instrument();

  historyDisplayedColumns: string[] = ['id', 'name', 'number', 'deleteDate'];
  showHistoryTable:boolean = false;


  constructor(
    private _instrumentService:InstrumentService,
    public dialogRef: MatDialogRef<InstrumentEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public idd: number,
    public dialog: MatDialog) {}

  ngOnInit(): void {
    if (this.idd) {
      this._instrumentService.getInstrumentByIdd(this.idd)
        .pipe()
        .subscribe(instrument => {this.data = instrument});
    } else {
      //TODO тут должна быть очистка history?
    }
  }

  setShowHistoryTable() {
    this.showHistoryTable = !this.showHistoryTable;
  }

  onCancelClick() {
    this.dialogRef.close();
  }

  onSaveClick() {
    if (this.idd){
      this._instrumentService.updateInstrument(this.idd, this.data)
        .toPromise()
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    } else {
      this._instrumentService.createInstrument(this.data)
        .toPromise()
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    }
  }



}
