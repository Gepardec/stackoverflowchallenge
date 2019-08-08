import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Challenge} from '../../../../shared/models/challenge';
import {EndpointService} from '../../../../shared/services/endpoint.service';
import {SnackbarService} from '../../../../shared/services/snackbar.service';
import {Status} from '../../../../shared/models/status';
import {Participant} from '../../../../shared/models/participant';
import {MatFormField} from '@angular/material';

@Component({
    selector: 'app-add-challenge',
    templateUrl: './add-challenge.component.html',
    styleUrls: ['./add-challenge.component.css']
})
export class AddChallengeComponent implements OnInit {

    @Input() challenge: Challenge;
    @Output() onSuccessfulAdding = new EventEmitter<boolean>();

    states: Status[];
    participants: Participant[];

    tempIndex: number = -1;

    constructor(private endpointService: EndpointService, private snackBarService: SnackbarService) {
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
    }

    addClicked() {
        if (this.tempIndex != -1) {
            this.challenge.status = this.states[this.states.map(el => el.id).indexOf(this.tempIndex)];
        } else {
            this.challenge.status = null;
        }

        console.log(this.challenge);

        this.endpointService.addChallenge(this.challenge).subscribe(
            data => {
                console.log(data);
                this.snackBarService.success(`Die Challenge '${data['title']}' wurde erfolgreich hinzugefügt!`);
                this.onSuccessfulAdding.emit(true);
                this.challenge = null;
            }, error => {
                console.log(error);
                this.snackBarService.error(`Fehler beim hinzufügen der Challenge! Bitte Eingaben überprüfen!`);
                this.challenge = null;
            }
        );
        // TODO if participants not empty
        // TODO call addparticipants to challenge function + concat participants id into ;-separeted strings
    }

    cancelClicked() {
        this.challenge = null;
    }

}
