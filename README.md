# Exponential-Multiplayer-ELO
Exponential Multiplayer ELO calculations

Based on the math presented here: https://towardsdatascience.com/developing-a-generalized-elo-rating-system-for-multiplayer-games-b9b495e87802 

Based on Multiplayer ELO https://github.com/FigBug/Multiplayer-ELO (which is a linear version)

Simple way to calculate ELO for multiplayer games. Currently only Javascript version. Slowly doing more.

Usage:

```js
const match = new ELOMatch();

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
