import {Component, OnInit} from '@angular/core';
import {Participant} from "../../../../shared/models/participant";
import {EndpointService} from "../../../../shared/services/endpoint.service";
import {SnackbarService} from "../../../../shared/services/snackbar.service";
import {Tag} from "../../../../shared/models/tag";
import {Observable} from "rxjs";

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
                this.snackBarService.warning(`there are no tags so far`);
            }
        );
    }

    deleteTag(t: Tag) {
        if (confirm(`do you want to delete the tag'${t.name}'?`)) {
            this.endpointService.deleteTag(t.id).subscribe(
                data => {
                    this.ngOnInit();
                    this.snackBarService.success(`Tag '${t.name}' successfully deleted`);
                },
                error => {
                    this.snackBarService.error(`something went wrong while deleting '${t.name}'!`);
                }
            );
        }
    }

    addTag(name: string) {
        this.endpointService.addTag(name).subscribe(
            data => {
                this.snackBarService.success(`the tag '${name}' was successfully added`);
                this.ngOnInit();
            },
            error => {
                this.snackBarService.error(`something went wrong - tag '${name}' was not added`);
            }
        );
    }
}
