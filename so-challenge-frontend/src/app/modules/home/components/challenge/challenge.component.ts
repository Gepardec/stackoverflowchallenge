import {Component, OnInit} from '@angular/core';
import {Participant} from '../../../../shared/models/participant';
import {EndpointService} from '../../../../shared/services/endpoint.service';
import {Challenge} from '../../../../shared/models/challenge';

@Component({
    selector: 'app-challenge',
    templateUrl: './challenge.component.html',
    styleUrls: ['./challenge.component.css']
})
export class ChallengeComponent implements OnInit {
    ngOnInit() {

    }


    /*challenges: Challenge[];
    errorMessage: boolean;

    constructor(private service: EndpointService) {
    }

    ngOnInit() {
        this.service.getChallenges().subscribe(
            data => {
                this.challenges = data;
                this.errorMessage = false;
            },
            error => {
                console.log(error);
                this.errorMessage = true;
            }
        );
    }

    deleteChallenge(c: Challenge) {
        this.service.deleteChallenge(c.id).subscribe(
            data => {
                console.log(data);
                this.ngOnInit();
            },
            error => {
                console.log(error);
            }
        );
    }

    /*addChallenge(id: string) {
        /*if (id.trim().length === 0 || isNaN(+id)) {
          alert('check your input again please');
          return;
        }*/

        /*this.service.addParticipant((+id)).subscribe(
            data => {
                console.log(data);
                this.ngOnInit();
            },
            error => {
                console.log(error);
                alert('There was an error:\nTry to provide valid input.\nThis user does not exist or is already in the list.');
            }
        );
    }*/

}
