import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Instrument} from "../../_model/instrument";
import {InstrumentService} from "../../_sevice/instrument.service";
import {errorObject, subscribeToPromise} from "rxjs/internal-compatibility";

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
    @Inject(MAT_DIALOG_DATA) public idd: number) {}

  ngOnInit(): void {
    if(this.idd){
      this._instrumentService.getInstrumentByIdd(this.idd)
        .pipe()
        .subscribe(instrument=>this.data = instrument);
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
