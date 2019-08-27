import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Challenge} from '../../../../shared/models/challenge';
import {EndpointService} from '../../../../shared/services/endpoint.service';
import {SnackbarService} from '../../../../shared/services/snackbar.service';
import {State} from '../../../../shared/models/state';
import {Participant} from '../../../../shared/models/participant';
import {Tag} from '../../../../shared/models/tag';
import {MatSelectionListChange} from '@angular/material';
import {SelectionModel} from '@angular/cdk/collections';
import {MatListOption} from '@angular/material/typings/list';
import {constructExclusionsMap} from 'tslint/lib/rules/completed-docs/exclusions';

@Component({
    selector: 'app-add-challenge',
    templateUrl: './add-challenge.component.html',
    styleUrls: ['./add-challenge.component.css']
})
export class AddChallengeComponent implements OnInit {

    @Input() challenge: Challenge;
    @Output() onSuccessfulAdding = new EventEmitter<boolean>();

    states: State[];
    participants: Participant[];
    selectedParticipants: Participant[] = [];
    tags: Tag[];
    selectedTags = this.tags;
    participantsString = '';
    removable = true;


    tempIndex = -1;

    constructor(private endpointService: EndpointService, private snackBarService: SnackbarService) {
    }
    removeTag(tag: Tag): void {
        const index = this.selectedTags.indexOf(tag);

        if (index >= 0) {
            this.selectedTags.splice(index, 1);
        }
    }
    ngOnInit() {
        this.endpointService.getCreateStates().subscribe(
            data => {
                this.states = data;
            }
        );

        this.endpointService.getParticipants().subscribe(
            data => {
                this.participants = data;
            }
        );
        this.endpointService.getTags().subscribe(
          data => {
              this.selectedTags = this.tags = data;
          }
        );
    }

    addClicked(selected: MatListOption[]) {
        console.log(this.challenge);

        this.endpointService.addChallenge(this.challenge).subscribe(
            data => {
                console.log(data);
                this.snackBarService.success(`the challenge '${this.challenge.title}' was successfully added`);
                this.onSuccessfulAdding.emit(true);
                this.challenge = null;
            }, error => {
                console.log(error);
                this.snackBarService.error(`something went wrong while creating the challenge`);
                this.challenge = null;
            }
        );
        for (const s of selected) {
            this.participantsString = this.participantsString.concat(s.value.valueOf().profileId, ':');
        }
        console.log(this.participantsString);

        this.endpointService.addParticipantsToChallenge(this.challenge.title, this.participantsString).subscribe(
            data => {
                console.log(data.toString());
                this.snackBarService.success(`the participants '${this.selectedParticipants.values()}' were added`);
                this.onSuccessfulAdding.emit(true);
                this.participantsString = null;
                this.selectedParticipants = null;
                this.participants = null;
            }, error => {
                console.log(error);
                this.snackBarService.error(`something went wrong while adding participants to challenge`);
                this.selectedParticipants = null;
            }
        );
         // TODO add selected tags to challenge the same way as participants
        this.endpointService.addTagsToChallenge(this.challenge.id, this.selectedTags);
    }

    cancelClicked() {
        this.challenge = null;
        this.participantsString = '';
    }

    onGroupsChange(selected: MatListOption[]) {
        console.log(selected.values().toString());
    }
}

