import {Component, OnInit, ViewChild} from '@angular/core';
import {Participant} from '../../../../shared/models/participant';
import {EndpointService} from '../../../../shared/services/endpoint.service';
import {SnackbarService} from '../../../../shared/services/snackbar.service';
import {MatPaginator} from '@angular/material';

@Component({
    selector: 'app-participant',
    templateUrl: './participant.component.html',
    styleUrls: ['./participant.component.css']
})
export class ParticipantComponent implements OnInit {
    participants: Participant[];
    columnsToDisplay = ['imageURL', 'profileId', 'username', 'link', 'action'];

    constructor(private endpointService: EndpointService, private snackBarService: SnackbarService) {
    }

    ngOnInit() {
        this.endpointService.getParticipants().subscribe(
            data => {
                this.participants = data;
            },
            error => {
                this.participants = null;
                this.snackBarService.warning(`no users registered so far`);
            }
        );
    }

    deleteParticipant(p: Participant) {
        if (confirm(`do you want to remove user '${p.username}'?`)) {

            this.endpointService.deleteParticipant(p.profileId).subscribe(
                data => {
                    this.ngOnInit();
                    this.snackBarService.success(`user '${p.username}' was successfully deleted`);
                },
                error => {
                    this.snackBarService.error(`error while deleting user '${p.username}'!`);
                }
            );
        }
    }

    addParticipant(id: string) {
        this.endpointService.addParticipant((+id)).subscribe(
            data => {
                this.ngOnInit();
                this.snackBarService.success(`the user '${id}' was successfully registered`);
            },
            error => {
                this.snackBarService.error('something went wrong - user was not registered');
            }
        );
    }

}
