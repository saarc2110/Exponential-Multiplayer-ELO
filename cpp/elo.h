#pragma once

#include <string>
#include <vector>
#include <cmath>

class ELOPlayer
{
public:
    std::string name;
    
    int place     { 0 };
    int eloPre    { 0 };
    int eloPost   { 0 };
    int eloChange { 0 };
};

class ELOMatch
{
private:
    std::vector<ELOPlayer> players;

public:
    void addPlayer(std::string name, int place, int elo)
    {
        ELOPlayer player;
        
        player.name    = name;
        player.place   = place;
        player.eloPre  = elo;
        
        players.push_back(player);
    }

    int getELO(std::string name)
    {
        for (ELOPlayer& p : players)
        {
            if (p.name == name)
                return p.eloPost;
        }
        return 1500;
    }

    int getELOChange(std::string name)
    {
        for (ELOPlayer& p : players)
        {
            if (p.name == name)
                return p.eloChange;
        }
        return 0;
    }

    void calculateELOs()
    {
        int n = int(players.size());
        float k = 32 / float(n - 1);
        
        for (int i = 0; i < n; i++)
        {
            int curPlace = players[i].place;
            int curELO   = players[i].eloPre;
            double numerator = 0;
            double den = 0;
            float alpha = 1.25;
            
            for (int j = 0; j < n; j++)
            {
            int opponentPlace = players[j].place;
            den += std::pow(alpha, n - opponentPlace) - 1;
                if (i != j)
                {
                    int opponentELO   = players[j].eloPre;
                    numerator += 1 / (1 + std::pow(10, (opponentELO - curELO) * 0.0025));
                   
                }
            }

            double ea = numerator/(n*(n-1)/2);
            double sa = (std::pow(alpha, n - curPlace) - 1) / den;
            int eloPost = std::round(curELO + k*(n-1)*(sa-ea));
            players[i].eloPost = eloPost;
            players[i].eloChange = std::round(eloPost-curELO);

        }
    }
};
