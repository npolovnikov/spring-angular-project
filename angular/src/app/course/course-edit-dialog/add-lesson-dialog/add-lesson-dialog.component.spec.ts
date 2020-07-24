import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddLessonDialogComponent } from './add-lesson-dialog.component';

describe('AddLessonDialogComponent', () => {
  let component: AddLessonDialogComponent;
  let fixture: ComponentFixture<AddLessonDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddLessonDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddLessonDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
