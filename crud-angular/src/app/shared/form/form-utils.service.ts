import { Injectable } from '@angular/core';
import { FormArray, FormGroup, UntypedFormArray, UntypedFormControl, UntypedFormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class FormUtilsService {

  constructor() { }

  validateAllFormField(formGroup: FormGroup | FormArray){
    Object.keys(formGroup.controls).forEach(field => {
      const control = formGroup.get(field);

      if (control instanceof UntypedFormControl){
        control.markAsTouched({onlySelf: true});
      } else if(control instanceof UntypedFormGroup || control instanceof UntypedFormArray){
        control.markAsTouched({onlySelf: true});
        this.validateAllFormField(control);
      }
  })
  }

  getErrorMessage(formGroup: UntypedFormGroup, fieldName: string){

    const field = formGroup.get(fieldName) as UntypedFormControl;

    return this.getErrorMessageFromField(field);
  }

  getErrorMessageFromField(field: UntypedFormControl){

    if (field?.hasError('required')){
      return 'Campo obrigatório';
    }

    if (field?.hasError('minlength')){
      const requiredLength: number = field.errors ? field.errors['minlength']['requiredLength']: 10;
      return `Tamanho mínimo precisa ser de ${requiredLength}`;
    }

    if (field?.hasError('maxlength')){
      const requiredLength: number = field.errors ? field.errors['maxlength']['requiredLength']: 100;
      return `Tamanho máxido excedido de ${requiredLength} caracteres`;
    }

    return 'Campo inválido';
  }

  getFormArrayFieldErrorMessage(formGroup: UntypedFormGroup, formArrayName: string, fieldName: string, index: number){
    const formArray = formGroup.get(formArrayName) as UntypedFormArray;

    const field = formArray.controls[index].get(fieldName) as UntypedFormControl;

    return this.getErrorMessageFromField(field);
  }

  isFormArrayRequired(formGroup: UntypedFormGroup, formArrayName: string){
    const formArray = formGroup.get(formArrayName) as UntypedFormArray;
    return !formArray.valid && formArray.hasError('required')  && formArray.touched;
  }

}
