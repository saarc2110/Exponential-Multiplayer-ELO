#include "elo.h"

#include <string.h>
#include <stdlib.h>
#include <math.h>

ELOMatch* elo_createMatch(int maxPlayers)
{
    ELOMatch* match = malloc(sizeof(ELOMatch));
    memset(match, 0, sizeof(*match));
    
    match->players = malloc(maxPlayers * sizeof(ELOPlayer));
    memset(match->players, 0, maxPlayers * sizeof(ELOPlayer));
    
    match->maxPlayers = maxPlayers;
    
    return match;
}

void elo_destroyMatch(ELOMatch* match)
{
    for (int i = 0; i < match->curPlayers; i++)
        free(match->players[i].name);
    
    free(match->players);
    free(match);
}

void elo_addPlayer(ELOMatch* match, const char* name, int place, int elo)
{
    if (match->curPlayers < match->maxPlayers)
    {
        ELOPlayer* player = &match->players[match->curPlayers];
        
        player->name    = malloc(strlen(name) + 1);
        strcpy(player->name, name);
        
        player->place   = place;
        player->eloPre  = elo;
        
        match->curPlayers++;
    }
}

int elo_getELO(ELOMatch* match, const char* name)
{
    for (int i = 0; i < match->curPlayers; i++)
    {
        if (!strcmp(match->players[i].name, name))
            return match->players[i].eloPost;
    }
    return 1500;
}

int elo_getELOChange(ELOMatch* match, const char* name)
{
    for (int i = 0; i < match->curPlayers; i++)
    {
        if (!strcmp(match->players[i].name, name))
            return match->players[i].eloChange;
    }
    return 0;
}

void elo_calculateELOs(ELOMatch* match)
{
    int n = match->curPlayers;
    float k = 32 / (float)(n - 1);
    
    for (int i = 0; i < n; i++)
    {
        int curPlace = match->players[i].place;
        int curELO   = match->players[i].eloPre;
        double numerator = 0;
        double den = 0;
        float alpha = 1.25;

        for (int j = 0; j < n; j++)
        {
            int opponentPlace = match->players[j].place;
            den += pow(alpha, n - opponentPlace) - 1;
                
            if (i != j)
            {
                int opponentELO   = match->players[j].eloPre;
                numerator += 1 / (1 + pow(10, (opponentELO - curELO) * 0.0025));

            }
        }

        double ea = numerator/(n*(n-1)/2);
        double sa = (pow(alpha, n - curPlace) - 1) / den;
        int eloPost = round(curELO + k*(n-1)*(sa-ea));

        match->players[i].eloPost = eloPost;
        match->players[i].eloChange = round(eloPost-curELO);
    }
}
