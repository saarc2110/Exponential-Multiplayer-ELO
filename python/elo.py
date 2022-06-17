import math

class ELOPlayer:
    name      = ""
    place     = 0
    eloPre    = 0
    eloPost   = 0
    eloChange = 0
    
class ELOMatch:
    def __init__(self):
        self.players = []
    
    def addPlayer(self, name, place, elo):
        player = ELOPlayer()
        
        player.name    = name
        player.place   = place
        player.eloPre  = elo
        
        self.players.append(player)
        
    def getELO(self, name):
        for p in self.players:
            if p.name == name:
                return p.eloPost
            
        return 1500

    def getELOChange(self, name):
        for p in self.players:
            if p.name == name:
                return p.eloChange
                
        return 0
 
    def calculateELOs(self):
        n = len(self.players)
        k = 32 / (n - 1)

        for player in self.players:
            curPlace = player.place
            curELO   = player.eloPre
            numerator = 0
            den = 0
            alpha = 1.25

            for opponent in self.players:
                opponentPlace = opponent.place
                den += (math.pow(alpha, n - opponentPlace) - 1)

                if player != opponent:
                    opponentELO   = opponent.eloPre  
                    numerator += 1 / (1 + math.pow(10, (opponentELO - curELO) / 400))

                
            ea = numerator/(n*(n-1)/2)  
            sa = (math.pow(alpha, n - curPlace) - 1) / den
            eloPost = round(curELO + k*(n-1)*(sa-ea))

            player.eloPost = eloPost
            player.eloChange = round(eloPost-curELO)