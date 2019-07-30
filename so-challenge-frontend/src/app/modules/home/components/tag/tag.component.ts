import {Component, OnInit} from '@angular/core';
import {Participant} from "../../../../shared/models/participant";
import {EndpointService} from "../../../../shared/services/endpoint.service";
import {SnackbarService} from "../../../../shared/services/snackbar.service";
import {Tag} from "../../../../shared/models/tag";

@Component({
    selector: 'app-tag',
    templateUrl: './tag.component.html',
    styleUrls: ['./tag.component.css']
})
export class TagComponent implements OnInit {
    tags: Tag[];
    columnsToDisplay = ['name', 'action'];

    constructor(private endpointService: EndpointService, private snackBarService: SnackbarService) {
    }

    ngOnInit() {
        this.getTags();
    }

    getTags() {
        this.endpointService.getTags().subscribe(
            data => {
                this.tags = data;
            },
            error => {
                this.tags = null;
                this.snackBarService.open(`Es gibt noch keine Tags!`);
            }
        );
    }

    deleteTag(t: Tag) {
        if (confirm(`Wollen Sie den Tag '${t.name}' aus der Tagliste entfernen?`)) {
            this.endpointService.deleteTag(t.id).subscribe(
                data => {
                    this.ngOnInit();
                    this.snackBarService.open(`Tag '${t.name}' wurde aus der Tagliste gelöscht!`);
                },
                error => {
                    this.snackBarService.open(`Fehler beim Löschen des Tags '${t.name}'!`);
                }
            );
        }
    }

    addTag(name: string) {
        this.endpointService.addTag(name).subscribe(
            data => {
                this.ngOnInit();
            },
            error => {
                this.snackBarService.open('Fehler beim Hinzufügen: Dieser Tag existiert nicht oder ist bereits hinzugefügt!');
            }
        );
    }
}
