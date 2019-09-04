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
        if (Array.isArray(selected) && selected.length > 0) {
            for (const s of selected) {
                this.participantsString = this.participantsString.concat(s.value.valueOf().profileId, ':');
            }
            this.endpointService.addParticipantsToNewChallenge(this.challenge, this.participantsString).subscribe(
                data => {
                    console.log(data);
                    this.snackBarService.success(`the challenge '${this.challenge.title}' and it's participants were successfully added`);
                    this.onSuccessfulAdding.emit(true);
                    this.challenge = null;
                },
                error => {
                    console.log(error);
                    this.snackBarService.error(`something went wrong while creating the challenge and adding participants`);
                    this.challenge = null;
                }
            );
        } else {
            this.endpointService.addChallenge(this.challenge).subscribe(
                data => {
                    console.log(data);
                    this.snackBarService.success(`the challenge '${this.challenge.title}' was successfully added`);
                    this.snackBarService.warning('this challenge has no participants');
                    this.onSuccessfulAdding.emit(true);
                    this.challenge = null;
                }, error => {
                    console.log(error);
                    this.snackBarService.error(`something went wrong while creating the challenge`);
                    this.challenge = null;
                }
            );
        }

        // TODO add selected tags to challenge the same way as participants
        this.endpointService.addTagsToChallenge(this.challenge.id, this.selectedTags);
    }

    cancelClicked() {
        this.challenge = null;
        this.participantsString = '';
    }

    onGroupsChange(selected: MatListOption[]) {

    }
}

