import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Challenge} from "../../../../shared/models/challenge";
import {Status} from "../../../../shared/models/status";
import {EndpointService} from "../../../../shared/services/endpoint.service";
import {Participant} from "../../../../shared/models/participant";
import {SnackbarService} from "../../../../shared/services/snackbar.service";

@Component({
    selector: 'app-challenge-detail',
    templateUrl: './challenge-detail.component.html',
    styleUrls: ['./challenge-detail.component.css']
})
export class ChallengeDetailComponent implements OnInit {
    @Input() challenge: Challenge; // Challenge to be edited, copy from Parent component
    @Output() onSuccessfulEditing = new EventEmitter<boolean>();

    stati: Status[] = [];
    participants: Participant[];

    tempIndex: number = -1;

    constructor(private endpointService: EndpointService, private snackBarService: SnackbarService) {
    }

    ngOnInit() {
        this.endpointService.getStatuses().subscribe(
            data => {
                this.stati = data;
            }
        );
        this.endpointService.getParticipants().subscribe(
            data => {
                this.participants = data;
            }
        );
    }

    ngOnChanges() {
        this.tempIndex = -1;
        this.tempIndex = this.challenge.status == null ? -1 : this.challenge.status.id;
    }

    okClicked() {
        if (this.tempIndex != -1) {
            this.challenge.status = this.stati[this.stati.map(el => el.id).indexOf(this.tempIndex)];
        } else {
            this.challenge.status = null;
        }

        console.log(this.challenge)

        this.endpointService.updateChallenge(this.challenge).subscribe(
            data => {
                console.log(this.challenge)
                // the response was successful
                this.snackBarService.success(`Challenge '${this.challenge.title}' wurde aktualisiert.`);
                this.challenge = null;
                this.onSuccessfulEditing.emit(true);
            },
            error => {
                // something went wrong
                this.snackBarService.error('Fehler beim Bearbeiten... Versuchen Sie es später erneut!');
                this.challenge = null;
            }
        );
    }

    cancelClicked() {
        this.challenge = null;
    }
}
