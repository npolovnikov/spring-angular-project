import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {SelectionModel} from "@angular/cdk/collections";
import {NativeDateAdapter, DateAdapter, MAT_DATE_FORMATS} from '@angular/material/core';
import {formatDate} from '@angular/common';
import {Instrument} from "../../_model/Instrument";
import {InstrumentService} from "../../_service/instrument.service";

export const PICK_FORMATS = {
  parse: {dateInput: {month: 'short', year: 'numeric', day: 'numeric'}},
  display: {
    dateInput: 'input',
    monthYearLabel: {year: 'numeric', month: 'short'},
    dateA11yLabel: {year: 'numeric', month: 'long', day: 'numeric'},
    monthYearA11yLabel: {year: 'numeric', month: 'long'}
  }
};

class PickDateAdapter extends NativeDateAdapter {
  format(date: Date, displayFormat: Object): string {
    if (displayFormat === 'input') {
      return formatDate(date, 'yyyy-MM-dd', this.locale);
    } else {
      return date.toDateString();
    }
  }
}

@Component({
  selector: 'app-instrument-edit-dialog',
  templateUrl: './instrument-edit-dialog.component.html',
  styleUrls: ['./instrument-edit-dialog.component.scss'],
  providers: [
    {provide: DateAdapter, useClass: PickDateAdapter},
    {provide: MAT_DATE_FORMATS, useValue: PICK_FORMATS}
  ]
})
export class InstrumentEditDialogComponent implements OnInit {

  data: Instrument = new Instrument();
  selection = new SelectionModel(false, []);

  birthDate: Date;

  historyDisplayedColumns: string[] = ['id', 'name', 'number', 'createDate'];
  showHistoryTable:boolean = false;

  constructor(
    private _instrumentService: InstrumentService,
    public dialogRef: MatDialogRef<InstrumentEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public idd: number,
    public dialog: MatDialog) {
  }


  ngOnInit(): void {
    if (this.idd) {
      this._instrumentService.getInstrumentByIdd(this.idd)
        .pipe()
        .subscribe(instrument => {
          this.data = instrument;
        });
    }
  }

  onCancelClick() {
    this.dialogRef.close();
  }

  onSaveClick() {
    if (this.idd) {
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

  setShowHistoryTable() {
    this.showHistoryTable = !this.showHistoryTable;
  }
}
