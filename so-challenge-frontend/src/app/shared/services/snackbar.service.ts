import {Injectable, NgZone} from "@angular/core";
import {MatSnackBar} from "@angular/material";

@Injectable({
    providedIn: 'root'
})
export class SnackbarService {
    constructor(
        public snackBar: MatSnackBar,
        private zone: NgZone
    ) {
    }

    public open(message, action = 'OK', duration = 5000) {
        this.zone.run(() => {
            this.snackBar.open(message, action, {duration});
        })
    }
}
