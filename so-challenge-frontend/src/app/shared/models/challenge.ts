import { Participant } from './participant';

export class Challenge {
    id: number;
    title: string;
    begindate: Date;
    enddate: Date;
    participantList: Participant[];
}
