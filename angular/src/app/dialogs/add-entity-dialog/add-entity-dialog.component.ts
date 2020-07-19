import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-add-entity-dialog',
  templateUrl: './add-entity-dialog.component.html',
  styleUrls: ['./add-entity-dialog.component.scss']
})
export class AddEntityDialogComponent implements OnInit {

  optionList: any [] = [];
  selected: number;

  constructor(public dialogRef: MatDialogRef<AddEntityDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
  ) {
  }

  ngOnInit(): void {
    this.data.service.getList(null, null, 0, 1000)
      .pipe()
      .subscribe(res => this.optionList = res.list);
  }

  onCancelClick() {
    this.dialogRef.close();
  }

  onSaveClick() {
    this.dialogRef.close(this.selected);
  }
}
