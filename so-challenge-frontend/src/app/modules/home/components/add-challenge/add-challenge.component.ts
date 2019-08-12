import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Challenge} from '../../../../shared/models/challenge';
import {EndpointService} from '../../../../shared/services/endpoint.service';
import {SnackbarService} from '../../../../shared/services/snackbar.service';
import {State} from '../../../../shared/models/state';
import {Participant} from '../../../../shared/models/participant';
import {Tag} from '../../../../shared/models/tag';
import {MatFormFieldModule} from '@angular/material';
import {MatInputModule} from '@angular/material/typings/input';
import {MatChipList} from '@angular/material/typings/chips';
import {ReactiveFormsModule} from '@angular/forms';

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
    selectedParticipants: Participant[];
    tags: Tag[];
    selectedTags = this.tags;
    participantsString: string;
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

    addClicked() {
        if (this.tempIndex != -1) {
            this.challenge.state = this.states[this.states.map(el => el.id).indexOf(this.tempIndex)];
        } else {
            this.challenge.state = null;
        }

        console.log(this.challenge);

        this.endpointService.addChallenge(this.challenge).subscribe(
            data => {
                console.log(data);
                // ${data.title} throws an error but compiles and works
                this.snackBarService.success(`the challenge '${data}' was successfully added`);
                this.onSuccessfulAdding.emit(true);
                this.challenge = null;
            }, error => {
                console.log(error);
                this.snackBarService.error(`something went wrong`);
                this.challenge = null;
            }
        );
        // TODO call addparticipants to challenge function + concat participants id into ;-separeted strings
        // for (const p in this.selectedParticipants) {
        //     if (this.selectedParticipants.hasOwnProperty(p)) {
        //         //  this.participantsString = ''.concat(, ';');
        //     }
        // }
        // this.endpointService.addParticipantsToChallenge(this.challenge.id, this.participantsString);
        // TODO add selected tags to challenge
    }

    cancelClicked() {
        this.challenge = null;
    }

}
