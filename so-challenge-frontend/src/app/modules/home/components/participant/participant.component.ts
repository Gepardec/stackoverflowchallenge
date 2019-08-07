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
                this.snackBarService.warning(`Es gibt noch keine Teilnehmer!`);
            }
        );
    }

    deleteParticipant(p: Participant) {
        if (confirm(`Wollen Sie den Teilnehmer '${p.username}' aus der Teilnehmerliste entfernen?`)) {

            this.endpointService.deleteParticipant(p.profileId).subscribe(
                data => {
                    this.ngOnInit();
                    this.snackBarService.success(`Teilnehmer '${p.username}' wurde aus der Teilnehmerliste gelöscht!`);
                },
                error => {
                    this.snackBarService.error(`Fehler beim Löschen des Teilnehmers '${p.username}'!`);
                }
            );
        }
    }

    addParticipant(id: string) {
        this.endpointService.addParticipant((+id)).subscribe(
            data => {
                this.ngOnInit();
                this.snackBarService.success(`Der Benutzer '${data}' wurde erfolgreich als Teilnehmer registriert!`);
            },
            error => {
                this.snackBarService.error('Fehler beim Hinzufügen: Dieser Teilnehmer existiert nicht oder ist bereits hinzugefügt!');
            }
        );
    }

}
