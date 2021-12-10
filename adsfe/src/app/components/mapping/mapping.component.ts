import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {Router} from "@angular/router";
import {FileService} from "../../core/file.service";
import {FormBuilder, FormControl, Validators} from "@angular/forms";
import {tap} from "rxjs";

@Component({
  selector: 'app-mapping',
  templateUrl: './mapping.component.html',
  styleUrls: ['./mapping.component.css']
})
export class MappingComponent implements OnInit, OnChanges {
  @Input() headers: String[] = [];
  @Output() mappingValue: EventEmitter<any> = new EventEmitter<any>();
  @Output() isValid: EventEmitter<boolean> = new EventEmitter<boolean>();
  @Input("excelHeaders") excelHeaders: String[] | null = [];
  @Output() excelHeaderOut: EventEmitter<any> = new EventEmitter<any>();
  classFile: File | undefined;
  timetableFile: File | undefined;
  formGroup = this.fb.group({});

  constructor(private router: Router,
              private fileService: FileService,
              private fb: FormBuilder) {
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.loadControllers();
  }

  ngOnInit(): void {
    this.isValid.emit(false);
    this.loadControllers();
    this.formGroup.valueChanges.pipe(tap(value => this.changedValues()));
  }

  private loadControllers() {
    if (this.excelHeaders && this.excelHeaders.length > 0) {
      this.excelHeaderOut.emit(this.excelHeaders);
      this.formGroup = this.fb.group({});
      // initialization of form controls
      for (let i = 0; i < this.excelHeaders.length; i++) {
        this.formGroup.setControl(i + '', new FormControl(this.defaultValueClass(this.excelHeaders[i], i), Validators.required))
      }
      if (this.formGroup.valid) {
        this.isValid.emit(true);
        this.mappingValue.emit(this.formGroup.value);
      }
    }
  }

  changedValues() {
    if (this.formGroup.valid) {
      this.isValid.emit(true);
      this.mappingValue.emit(this.formGroup.value);
      console.log(this.formGroup.value)
    } else {
      this.isValid.emit(false);
    }
  }

  changeValue(value: any, i: number) {
    let controller = this.formGroup.get(i+'');
    if(controller){
      console.log(value);
      controller.setValue(value);
    }
    this.changedValues();
  }

  defaultValueClass(header: any, i: number) {
    if (typeof header === 'string') {
      if (i < this.headers.length) {
        return this.headers[i] === header ? this.headers[i] : '';
      }
    }
    return '';
  }
}
