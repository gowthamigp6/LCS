import { Component, VERSION } from '@angular/core';
import { FormGroup, FormBuilder, Validators,FormControl } from '@angular/forms';
import { LcsRequest } from '../model/LcsRequest';
import { SetOfStrings } from '../model/SetOfStrings';
import { LcsService } from '../service/lcs-service';
import { LcsResponse } from 'src/model/LcsResponse';

@Component({
  selector: 'my-app',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  lcsForm: FormGroup;
  profileForm = new FormGroup({
    value: new FormControl(''),
  });

  errorMessage: string;
  //value: string;
  submitted = false;
  lcsRequest: LcsRequest;
  setOfStrings: Array<SetOfStrings>;

  constructor(
    private formBuilder: FormBuilder,
    private lcsService: LcsService
  ) {}

  ngOnInit() {
    this.lcsForm = this.formBuilder.group({
      value: ['', Validators.required],
    });
  }

  onReset(){
    this.lcsForm.reset();
  }
  onSubmit() {
    this.submitted = true;
    if (this.lcsForm.invalid) {
      this.errorMessage = 'Please enter comma separated value';
      return;
    } else if (!this.validateInput(this.lcsForm.get('value').value)) {
      this.errorMessage = 'Please enter comma separated value';
    } else {
      console.log(this.lcsForm.get('value').value);
      this.setOfStrings  = new Array<SetOfStrings>();
      this.lcsForm.get('value').value.split(',').forEach((x) => {
        console.log(x.value);
        let customObj = new SetOfStrings();
        customObj.value = x;
        this.setOfStrings.push(customObj);
      });

      this.lcsRequest = new LcsRequest();
      this.lcsRequest.setOfStrings = this.setOfStrings;
      this.submitted = false;
      this.lcsService.calculateLcs(this.lcsRequest).subscribe((data) => {
        console.log(data);
        let value = data as LcsResponse;
        console.log(data.lcs[0].value);
        this.errorMessage = 'The longest common string is '+ data.lcs[0].value;
      },error=>{
        console.log(error);
        this.errorMessage = 'All strings are not unique';
      });
      
      
    }
  }

  private validateInput(value: string) {
 
    let str: string[];
    str = value.split(',');
    console.log("value"+str);
    if (str.length >= 2) {
      return true;
    }
    return false;
  }
}
