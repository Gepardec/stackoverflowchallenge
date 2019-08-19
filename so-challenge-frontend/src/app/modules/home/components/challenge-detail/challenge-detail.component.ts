import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Challenge} from '../../../../shared/models/challenge';
import {State} from '../../../../shared/models/state';
import {EndpointService} from '../../../../shared/services/endpoint.service';
import {Participant} from '../../../../shared/models/participant';
import {SnackbarService} from '../../../../shared/services/snackbar.service';

@Component({
    selector: 'app-challenge-detail',
    templateUrl: './challenge-detail.component.html',
    styleUrls: ['./challenge-detail.component.css']
})
export class ChallengeDetailComponent implements OnInit {
    @Input() challenge: Challenge; // Challenge to be edited, copy from Parent component
    @Output() onSuccessfulEditing = new EventEmitter<boolean>();

    states: State[];
    participants: Participant[];

    tempIndex = -1;

    constructor(private endpointService: EndpointService, private snackBarService: SnackbarService) {
    }

    ngOnInit() {
        this.endpointService.getStates().subscribe(
            data => {
                this.states = data;
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
        this.tempIndex = this.challenge.state == null ? -1 : this.challenge.state.id;
    }

    okClicked() {
        if (this.tempIndex != -1) {
            this.challenge.state = this.states[this.states.map(el => el.id).indexOf(this.tempIndex)];
        } else {
            this.challenge.state = null;
        }

        console.log(this.challenge);

        this.endpointService.updateChallenge(this.challenge).subscribe(
            data => {
                console.log(this.challenge);
                // the response was successful
                this.snackBarService.success(`challenge '${this.challenge.title}' was successfully edited`);
                this.challenge = null;
                this.onSuccessfulEditing.emit(true);
            },
            error => {
                // something went wrong
                this.snackBarService.error('something went wrong while editing');
                this.challenge = null;
            }
        );
    }

    cancelClicked() {
        this.challenge = null;
    }
}
