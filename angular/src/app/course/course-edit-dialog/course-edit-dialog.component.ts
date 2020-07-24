import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {Course} from "../../_model/course";
import {CourseService} from "../../_service/course.service";
import {SelectionModel} from "@angular/cdk/collections";
//import {AddInstrumentDialogComponent} from "./add-instrument-dialog/add-instrument-dialog.component";
import {InstrumentService} from "../../_service/instrument.service";
import {MatTable} from "@angular/material/table";
import {InstrumentList} from "../../_model/instrument-list";
import {TeacherList} from "../../_model/teacher-list";
import {TeacherService} from "../../_service/teacher.service";
import {MatDatepickerInputEvent} from "@angular/material/datepicker";
import {AddLessonDialogComponent} from "./add-lesson-dialog/add-lesson-dialog.component";
import {AddStudentDialogComponent} from "./add-student-dialog/add-student-dialog.component";
import {FormGroup} from "@angular/forms";
import {Teacher} from "../../_model/teacher";
import {StudentList} from "../../_model/student-list";
import {LessonList} from "../../_model/lesson-list";
import {formatDate} from "@angular/common";

@Component({
  selector: 'app-course-edit-dialog',
  templateUrl: './course-edit-dialog.component.html',
  styleUrls: ['./course-edit-dialog.component.scss']
})
export class CourseEditDialogComponent implements OnInit {
  @ViewChild(MatTable) studentTable: MatTable<StudentList>;
  @ViewChild(MatTable) lessonTable: MatTable<LessonList>;

  data:Course = new Course();

  lessonsDisplayedColumns: string [] = ['select', 'idd', 'name', 'description'];
  studentsDisplayedColumns: string [] = ['select', 'firstName', 'middleName', 'lastName'];
  selectionLesson = new SelectionModel(false, []);
  selectionStudent = new SelectionModel(false, []);
  showStudentsTable:boolean = false;
  showLessonsTable:boolean = false;

  historyDisplayedColumns: string[] = ['id', 'name', 'description', 'maxCountStudent', 'dateStart', 'dateEnd', 'createDate', 'status'];
  showHistoryTable:boolean = false;

  teachers: TeacherList[] = [];
  selectedTeacher: TeacherList;

  patientCategory: FormGroup;

  constructor(
    private _courseService:CourseService,
    private _teacherService:TeacherService,
    public dialogRef: MatDialogRef<CourseEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public idd: number,
    public dialog: MatDialog) {}
    private dateStart: Date;
    private dateEnd: Date;

  ngOnInit(): void {
    if (this.idd) {
      this._courseService.getCourseByIdd(this.idd)
        .pipe()
        .subscribe(course => {this.data = course;

          });

    } else {
      this.data.students = [];
      this.data.lessons = [];
    }
    this._teacherService.getTeacherList(null, null, 0, 1000)
      .pipe()
      .subscribe(res => {this.teachers = res.list;
        this.selectedTeacher = this.teachers.find(c => c.idd == this.data.teacher.idd);
        this.dateStart = this.data.dateStart != null ? new Date(this.data.dateStart) : null;
        this.dateEnd = this.data.dateEnd != null ? new Date(this.data.dateEnd) : null;})
  }

  setShowLessonsTable() {
    this.showLessonsTable = !this.showLessonsTable;
  }

  setShowStudentsTable() {
    this.showStudentsTable = !this.showStudentsTable;
  }

  setShowHistoryTable() {
    this.showHistoryTable = !this.showHistoryTable;
  }

  onCancelClick() {
    this.dialogRef.close();
  }

  onSaveClick() {
    this.data.teacher = this.selectedTeacher;
    this.data.dateStart = formatDate(this.dateStart,'yyyy-MM-dd HH:mm', 'en-US').toString().substr(0,16);
    this.data.dateEnd = formatDate(this.dateEnd,'yyyy-MM-dd HH:mm', 'en-US').toString().substr(0,16);
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
      this.data.lessons.forEach( e => this._courseService.setLesson(this.data.idd, e.idd));
      this.data.students.forEach( e => this._courseService.setStudent(this.data.idd, e.idd));
    }
  }

  onDeleteLesson() {
    this.data.lessons
      = this.data.lessons.filter(obj => obj.idd !== this.selectionLesson.selected[0].idd);
    this.selectionLesson.clear();
    this.lessonTable.renderRows();
  }

  onAddLesson() {
    const dialogRef = this.dialog.open(AddLessonDialogComponent, {
      width: '750px'
    });

    dialogRef.afterClosed().subscribe(result => {
      this.data.lessons.push(result);
      this.lessonTable.renderRows();
    });
  }

  onDeleteStudent() {
    this.data.students
      = this.data.students.filter(obj => obj.idd !== this.selectionStudent.selected[0].idd);
    this.selectionStudent.clear();
    this.studentTable.renderRows();
  }

  onAddStudent() {
    const dialogRef = this.dialog.open(AddStudentDialogComponent, {
      width: '750px'
    });

    dialogRef.afterClosed().subscribe( result => {
      this.data.students.push(result);
      this.studentTable.renderRows();
    });
  }

  addEvent(event: MatDatepickerInputEvent<unknown, unknown>) {
    this.data.dateStart = event.value.toString();
  }

  addEventEnd(event: MatDatepickerInputEvent<unknown, unknown>) {
    this.data.dateEnd = event.value.toString();
  }
}
