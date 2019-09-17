import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Challenge} from '../../../../shared/models/challenge';
import {State} from '../../../../shared/models/state';
import {EndpointService} from '../../../../shared/services/endpoint.service';
import {Participant} from '../../../../shared/models/participant';
import {SnackbarService} from '../../../../shared/services/snackbar.service';
import {FormControl} from '@angular/forms';
import {Tag} from '../../../../shared/models/tag';
import {MatFormField} from '@angular/material';
import {constructExclusionsMap} from 'tslint/lib/rules/completed-docs/exclusions';
import {MatSelectionList, MatSelectionListChange} from '@angular/material';
import {MatInputModule} from '@angular/material';
import {SelectionModel} from '@angular/cdk/collections';

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
    tags: Tag[];

    constructor(private endpointService: EndpointService, private snackBarService: SnackbarService) {
    }

    ngOnInit() {
        this.endpointService.getAvailableStates(this.challenge.state).subscribe(
            data => {
                this.states = data;
            }
        );
        this.endpointService.getParticipantsOfChallenge(this.challenge.id).subscribe(
            data => {
                this.participants = data;
            }
        );
        this.endpointService.getTagsOfChallenge(this.challenge.id).subscribe(
            data => {
                this.tags = data;
            }
        );
    }

    okClicked() {

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
    onStateChange() {
        this.endpointService.updateChallenge(this.challenge).subscribe();
        this.endpointService.getAvailableStates(this.challenge.state).subscribe(
            data => {
                console.log(data);
                this.states = data;
            }
        );
    }

}
