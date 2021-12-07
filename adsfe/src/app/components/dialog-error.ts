import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';

@Component({
  selector: 'dialog-elements-example-dialog',
  template: '<p *ngFor="let error of data.errors">{{error}}</p>',
  styles: ['.mat-dialog-container::ng-deep {background: #f8d7da !important;}']
})
export class DialogError {
  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {}
}
