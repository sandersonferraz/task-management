import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaskFormAdd } from './task-form-add';

describe('TaskFormAdd', () => {
  let component: TaskFormAdd;
  let fixture: ComponentFixture<TaskFormAdd>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TaskFormAdd]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TaskFormAdd);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
