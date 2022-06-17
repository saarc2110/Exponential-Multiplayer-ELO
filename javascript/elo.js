class ELOMatch {

    constructor() {
        this.players = [];
    }

    addPlayer(name, place, elo) {
        let player = {
            name: name,
            place: place,
            eloPre: elo,
            eloChange: 0
        };

        this.players.push(player);
    }

    getELO(name) {
        for (let i = 0; i < this.players.length; i++) {
            if (this.players[i].name === name) {
                return this.players[i].eloPost;
            }
        }
    }

    getELOChange(name) {
        for (let i = 0; i < this.players.length; i++) {
            if (this.players[i].name === name) {
                return this.players[i].eloChange;
            }
        }
    }

    calculateELOs() {
        const n = this.players.length;
        const k = 32 / (n - 1);

        for (let i = 0; i < n; i++) {
            const curPlace = this.players[i].place;
            const curELO = this.players[i].eloPre;
            let numerator = 0;
            let den = 0;
            const alpha = 1.25;

            for (let j = 0; j < n; j++) {
                const oppPlace = this.players[j].place;
                den += (Math.pow(alpha, n - oppPlace) - 1);
                if (i !== j) {
                    const oppELO = this.players[j].eloPre;
                    numerator += 1 / (1 + Math.pow(10, (oppELO - curELO) / 400));

                }
            }
            const ea = numerator/(n*(n-1)/2);

            const sa = (Math.pow(alpha, n - curPlace) - 1) / den;

            const eloPost = Math.round(curELO + k*(n-1)*(sa-ea));

            this.players[i].eloPost = eloPost;
            this.players[i].eloChange = Math.round(eloPost-curELO);

        }
    }
}

exports.ELOMatch = ELOMatch;
