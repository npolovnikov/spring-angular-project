import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MatTable} from '@angular/material/table';
import {SelectionModel} from '@angular/cdk/collections';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {StudentService} from '../../../_service/student.service';
import {CourseService} from '../../../_service/course.service';
import {Course} from '../../../_model/course';
import {Student} from '../../../_model/student';
import {AddEntityDialogComponent} from '../../../dialogs/add-entity-dialog/add-entity-dialog.component';

@Component({
  selector: 'app-student-edit-dialog',
  templateUrl: './student-edit-dialog.component.html',
  styleUrls: ['./student-edit-dialog.component.scss']
})
export class StudentEditDialogComponent implements OnInit {
  @ViewChild(MatTable) courseTable: MatTable<Course>;

  data: Student = new Student();

  selection = new SelectionModel(false, []);
  showCourseTable: boolean = false;

  courseColumns: any[] = [
    {id: 'select', name: null},
    {id: 'idd', name: '#'},
    {id: 'name', name: 'Наименование'},
    {id: 'description', name: 'Описание'},
    {id: 'createDate', name: 'Дата обновления'},
  ];
  courseColumnsIds: string[] = this.courseColumns.map(obj => obj.id);

  historyColumns: any[] = [
    {id: 'idd', name: '#'},
    {id: 'lastName', name: 'Фамилия'},
    {id: 'firstName', name: 'Имя'},
    {id: 'middleName', name: 'Отчество'},
    {id: 'passport', name: 'Паспорт'},
  ];
  historyColumnsIds: string[] = this.historyColumns.map(obj => obj.id);
  showHistoryTable: boolean = false;

  constructor(
    private studentService: StudentService,
    private courseService: CourseService,
    public dialogRef: MatDialogRef<StudentEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public idd: number,
    public dialog: MatDialog) {
  }


  ngOnInit(): void {
    if (this.idd) {
      this.studentService.getStudentByIdd(this.idd)
        .pipe()
        .subscribe(student => {
          this.data = student;
        });
    } else {
      this.data.courses = [];
    }
  }

  setShowInstrumentTable() {
    this.showCourseTable = !this.showCourseTable;
  }

  setShowHistoryTable() {
    this.showHistoryTable = !this.showHistoryTable;
  }

  onCancelClick() {
    this.dialogRef.close();
  }

  onSaveClick() {
    if (this.idd) {
      this.studentService.updateStudent(this.idd, this.data)
        .toPromise()
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    } else {
      this.studentService.createStudent(this.data)
        .toPromise()
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    }
  }

  onDeleteCourse() {
    this.data.courses
      = this.data.courses.filter(obj => obj.idd !== this.selection.selected[0].idd);
    this.selection.clear();
    this.courseTable.renderRows();
  }

  onAddCourse() {
    const dialogRef = this.dialog.open(AddEntityDialogComponent, {
      width: '750px',
      data: {
        name: 'Добавление курса студенту',
        selectLabel: 'Курс',
        service: this.courseService
      },
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.data.courses.push(result);
        this.courseTable.renderRows();
      }
    });
  }

}
