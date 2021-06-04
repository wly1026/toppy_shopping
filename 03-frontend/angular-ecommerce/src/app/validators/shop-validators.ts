import { FormControl, ValidationErrors } from "@angular/forms";

export class ShopValidators {

    static notOnlyWhiteSpace(control: FormControl): ValidationErrors {
        
        if (control.value != null && control.value.trim().length === 0) {
            return {'onlyWhiteSpace': true};
        } else {
            return null;
        }
        
    }
}
