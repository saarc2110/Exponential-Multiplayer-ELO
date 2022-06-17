using System;
using System.Collections.Generic;

namespace ELO
{
    class ELOPlayer
    {
        public string name;
        
        public int place     = 0;
        public int eloPre    = 0;
        public int eloPost   = 0;
        public int eloChange = 0;
    }

    class ELOMatch
    {
        private List<ELOPlayer> players = new List<ELOPlayer>();
    
        public void addPlayer(string name, int place, int elo)
        {
            ELOPlayer player = new ELOPlayer();
            
            player.name    = name;
            player.place   = place;
            player.eloPre  = elo;
            
            players.Add(player);
        }
    
        public int getELO(string name)
        {
            foreach (ELOPlayer p in players)
            {
                if (p.name == name)
                    return p.eloPost;
            }
            return 1500;
        }
    
        public int getELOChange(string name)
        {
            foreach (ELOPlayer p in players)
            {
                if (p.name == name)
                    return p.eloChange;
            }
            return 0;
        }
    
        public void calculateELOs()
        {
            int n = players.Count;
            float k = 32 / (float)(n - 1);
            
            for (int i = 0; i < n; i++)
            {
                int curPlace = players[i].place;
                int curELO   = players[i].eloPre;
                double numerator = 0;
                double den = 0;
                double alpha = 1.25;
                
                for (int j = 0; j < n; j++)
                {
                    int opponentPlace = players[j].place;
                    den += (Math.Pow(alpha, n - opponentPlace) - 1);

                    if (i != j)
                    {
                        int opponentELO = players[j].eloPre;
                        numerator += 1 / (1 + Math.Pow(10, (opponentELO - curELO) *0.0025));                     
                    }
                }
                double ea = numerator/(n*(n-1)/2);
                double sa = (Math.Pow(alpha, n - curPlace) - 1) / den;
                int eloPost = (int)Math.Round(curELO + k*(n-1)*(sa-ea));
                int eloChange = (int)Math.Round((double)(eloPost-curELO));

                players[i].eloPost = eloPost;
                players[i].eloChange = eloChange;

            }
        }
        
    }
}

