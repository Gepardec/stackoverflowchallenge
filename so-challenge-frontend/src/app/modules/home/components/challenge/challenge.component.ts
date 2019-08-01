import {Component, Input, OnInit} from '@angular/core';
import {EndpointService} from '../../../../shared/services/endpoint.service';
import {Challenge} from '../../../../shared/models/challenge';
import {SnackbarService} from "../../../../shared/services/snackbar.service";
import {AddChallengeComponent} from "../add-challenge/add-challenge.component";
import {Participant} from "../../../../shared/models/participant";
import {Tag} from "../../../../shared/models/tag";
import {Status} from "../../../../shared/models/status";

@Component({
    selector: 'app-challenge',
    templateUrl: './challenge.component.html',
    styleUrls: ['./challenge.component.css']
})
export class ChallengeComponent implements OnInit {
    challenges: Challenge[];
    challengeToEdit: Challenge;
    challengePrototype: Challenge;

    public showAddChallenge = false;

    columnsToDisplay = ['title', 'fromDate', 'toDate', 'status', 'action'];

    constructor(private endpointService: EndpointService, private snackBarService: SnackbarService) {
    }

    ngOnInit() {
        this.endpointService.getChallenges().subscribe(
            data => {
                this.challenges = data;
            },
            error => {
                this.challenges = null;
                this.snackBarService.warning('Es gibt noch keine Challenges!');
            }
        );
    }

    selectChallenge(c: Challenge) {
        let copy: Challenge;
        if (c !== null) {
            copy = JSON.parse(JSON.stringify(c));
        }

        this.challengeToEdit = copy;
    }

    deleteChallenge(c: Challenge) {
        console.log(c)
        if (confirm(`Wollen Sie die Challenge '${c.title}' wirklich löschen?`)) {
            this.endpointService.deleteChallenge(c.id).subscribe(
                data => {
                    this.refreshGUI();
                    this.snackBarService.success(`Die Challenge '${data['title']}' wurde erfolgreich gelöscht!`);
                },
                error => {
                    this.snackBarService.error(`Die Challenge '${c.title}' konnte nicht gelöscht werden.`)
                }
            )
        }
    }

    openAddChallengeComponent() {
        this.showAddChallenge = true;
        this.challengePrototype = new class implements Challenge {
            award1: string;
            award2: string;
            award3: string;
            description: string;
            fromDate: Date;
            id: number;
            participantSet: Participant[];
            status: Status;
            tagSet: Tag[];
            title: string;
            toDate: Date;
        }
    }

    refreshGUI() {
        this.ngOnInit();
    }
}
