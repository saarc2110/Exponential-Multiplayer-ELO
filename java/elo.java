import java.util.*;
import java.lang.*;

class ELOPlayer
{
    public String name;
    
    public int place     = 0;
    public int eloPre    = 0;
    public int eloPost   = 0;
    public int eloChange = 0;
}

class ELOMatch
{
    private ArrayList<ELOPlayer> players = new ArrayList<ELOPlayer>();

    public void addPlayer(String name, int place, int elo)
    {
        ELOPlayer player = new ELOPlayer();
        
        player.name    = name;
        player.place   = place;
        player.eloPre  = elo;
        
        players.add(player);
    }

    public int getELO(String name)
    {
        for (ELOPlayer p : players)
        {
            if (p.name == name)
                return p.eloPost;
        }
        return 1500;
    }

    public int getELOChange(String name)
    {
        for (ELOPlayer p : players)
        {
            if (p.name == name)
                return p.eloChange;
        }
        return 0;
    }

    public void calculateELOs()
    {
        int n = players.size();
        float k = 32 / (float)(n - 1);
        
        for (int i = 0; i < n; i++)
        {
            int curPlace = players.get(i).place;
            int curELO   = players.get(i).eloPre;
            double numerator = 0;
            double den = 0;
            double alpha = 1.25;
            
            for (int j = 0; j < n; j++)
            {
                int opponentPlace = players.get(j).place;
                den += Math.pow(alpha, n - opponentPlace) - 1;

                if (i != j)
                {
                    int opponentELO   = players.get(j).eloPre;
                    numerator += 1 / (1 + Math.pow(10, (opponentELO - curELO) *0.0025));                     
                }
            }
            double ea = numerator/(n*(n-1)/2);
            double sa = (Math.pow(alpha, n - curPlace) - 1) / den;
            int eloPost = (int)Math.round(curELO + k*(n-1)*(sa-ea));
            int eloChange = (int)Math.round((double)(eloPost-curELO));
            //add accumulated change to initial ELO for final ELO   
            players.get(i).eloPost = eloPost;
            players.get(i).eloChange = eloChange;
        }
    }
}