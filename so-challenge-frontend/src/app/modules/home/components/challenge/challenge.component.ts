import {Component, Input, OnInit} from '@angular/core';
import {MatListModule} from '@angular/material/list';
import {EndpointService} from '../../../../shared/services/endpoint.service';
import {Challenge} from '../../../../shared/models/challenge';
import {SnackbarService} from '../../../../shared/services/snackbar.service';
import {AddChallengeComponent} from '../add-challenge/add-challenge.component';
import {Participant} from '../../../../shared/models/participant';
import {Tag} from '../../../../shared/models/tag';
import {State} from '../../../../shared/models/state';


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

    columnsToDisplay = ['title', 'fromDate', 'toDate', 'state', 'action'];

    constructor(private endpointService: EndpointService, private snackBarService: SnackbarService) {
    }

    ngOnInit() {
        this.endpointService.getChallenges().subscribe(
            data => {
                this.challenges = data;
                console.log('show challenges');
            },
            error => {
                console.log(error);
                this.snackBarService.warning('there are no challenges so far!');
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
        console.log(c);
        if (confirm(`do you want to delete the challenge '${c.title}'?`)) {
            this.endpointService.deleteChallenge(c.id).subscribe(
                data => {
                    this.refreshGUI();
                    this.snackBarService.success(`the challenge '${data['title']}' was successfully added`);
                },
                error => {
                    this.snackBarService.error(`something went wrong - the challenge '${c.title}' was not deleted`);
                }
            );
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
            state: State;
            tagSet: Tag[];
            title: string;
            toDate: Date;
        };
    }

    refreshGUI() {
        this.ngOnInit();
    }
}
