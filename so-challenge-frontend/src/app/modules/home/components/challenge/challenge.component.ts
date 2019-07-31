import {Component, OnInit} from '@angular/core';
import {EndpointService} from '../../../../shared/services/endpoint.service';
import {Challenge} from '../../../../shared/models/challenge';
import {SnackbarService} from "../../../../shared/services/snackbar.service";

@Component({
    selector: 'app-challenge',
    templateUrl: './challenge.component.html',
    styleUrls: ['./challenge.component.css']
})
export class ChallengeComponent implements OnInit {
    challenges: Challenge[];
    challengeToEdit: Challenge;

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

    refreshGUI() {
        this.ngOnInit();
    }
}
