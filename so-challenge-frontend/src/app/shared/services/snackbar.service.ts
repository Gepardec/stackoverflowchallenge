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

    public open(message, action = 'OK', duration = 5000, type = 'error-snackbar') {
        this.zone.run(() => {
            this.snackBar.open(message, action, { duration, panelClass: [type] });
        });
    }

    public warning(message, action = 'OK', duration = 5000) {
        this.zone.run(() => {
            this.snackBar.open(message, action, { duration, panelClass: ['warning-snackbar'] });
        });
    }

    public success(message, action = 'OK', duration = 5000) {
        this.zone.run(() => {
            this.snackBar.open(message, action, { duration, panelClass: ['success-snackbar'] });
        });
    }

    public error(message, action = 'OK', duration = 5000) {
        this.zone.run(() => {
            this.snackBar.open(message, action, { duration, panelClass: ['error-snackbar'] });
        });
    }

}
