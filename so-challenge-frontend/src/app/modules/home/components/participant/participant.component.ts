import { Component, OnInit } from '@angular/core';
import { Participant } from '../../../../shared/models/participant';
import { EndpointService } from '../../../../shared/services/endpoint.service';

@Component({
  selector: 'app-participant',
  templateUrl: './participant.component.html',
  styleUrls: ['./participant.component.css']
})
export class ParticipantComponent implements OnInit {
  participants: Participant[];
  errorMessage: boolean;

  constructor(private service: EndpointService) { }

  ngOnInit() {
    this.service.getParticipants().subscribe(
      data => {
        console.log(data);
        this.participants = data;
        this.errorMessage = false;
      },
      error => {
        console.log(error);
        this.errorMessage = true;
      }
    );
  }

  deleteParticipant(p: Participant) {
    this.service.deleteParticipant(p.profileId).subscribe(
      data => {
        console.log(data);
        this.ngOnInit();
      },
      error => {
        console.log(error);
      }
    );
  }

  addParticipant(id: string) {
    /*if (id.trim().length === 0 || isNaN(+id)) {
      alert('check your input again please');
      return;
    }*/

    this.service.addParticipant((+id)).subscribe(
      data => {
        console.log(data);
        this.ngOnInit();
      },
      error => {
        console.log(error);
        alert('There was an error:\nTry to provide valid input.\nThis user does not exist or is already in the list.');
      }
    );
  }

  onEnterPressed(event, id: string) {

    if (event.key === "Enter") {
      this.addParticipant(id);
    }
  }

}
