import {Component, OnInit} from '@angular/core';
import {EndpointService} from '../../../../shared/services/endpoint.service';
import {Challenge} from '../../../../shared/models/challenge';

@Component({
    selector: 'app-challenge',
    templateUrl: './challenge.component.html',
    styleUrls: ['./challenge.component.css']
})
export class ChallengeComponent implements OnInit {
    challenges: Challenge[];
    challengeToEdit: Challenge;

    columnsToDisplay = ['title', 'fromDate', 'toDate', 'status', 'action'];

    constructor(private service: EndpointService) {
    }

    ngOnInit() {
        this.service.getChallenges().subscribe(
            data => {
                console.log(data);
                this.challenges = data;
                console.log(this.challenges)
                console.log(this.challenges[0].tagSet.toString())
            },
            error => {
                console.log(error);
            }
        );
    }

    selectChallenge(c: Challenge) {
        this.challengeToEdit = c;
    }


    deleteChallenge(c: Challenge) {
        if (confirm(`Wollen Sie die Challenge '${c.title}' wirklich löschen?`)) {
            this.service.deleteChallenge(c.id).subscribe(
                data => {
                    // refreshing the UI
                    this.ngOnInit()
                },
                error => {
                    alert(`Die Challenge '${c.title}' konnte nicht gelöscht werden.`)
                }
            )
        }
    }
}
