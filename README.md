# Exponential-Multiplayer-ELO
Exponential Multiplayer ELO calculations

Based on the algorithm / code presented here: http://elo-norsak.rhcloud.com/index.php 

Based on Multiplayer ELO https://github.com/FigBug/Multiplayer-ELO (which is linear)

Simple way to calculate ELO for multiplayer games. Currently only Javascript.

Usage:

```js
match = new ELOMatch();

match.addPlayer("Joe", 1, 1600);
match.addPlayer("Sam", 2, 1550);
match.addPlayer("Ted", 3, 1520);
match.addPlayer("Rex", 4, 1439);

match.calculateELOs();

match.getELO("Joe");
match.getELO("Sam");
match.getELO("Ted");
match.getELO("Rex");
```
