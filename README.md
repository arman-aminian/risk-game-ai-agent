# Implementation of the Risk Game and Artificial Intelligence Agent Player

## 1. Abstract
### 1.1. Game Description
Risk is a strategy board game of diplomacy, conflict and conquest for two to
six players.
Turn rotates among players who control armies of playing pieces
with which they attempt to capture territories from other players, with results
determined by dice rolls. The goal of the game is to occupy every territory on
the board and in doing so, eliminate the other players.
Each turn consists of two actions:
1) Draft: Player gets to add certain amount of army units to any territory they own.
2) Attack: Player can choose to attack neighbor enemy territories with the number of armies they prefer in order to conquer the land.

### 1.2. Project Goals
The purpose of this project has been to implement the risk game with some changes 
made in the rules and to design an artificially intelligent agent that is able 
to play the game properly and have a very high chance of winning when facing other opponents 
including both actual human players or other agent players.

## 2. Game Implementation
The game is implemented using JAVA programming language. 
Controlling turns, performing player requested actions, updating the game map, checking if everything is happening according to the rules, etc., are the things that this part of the code does.
The game UI is also designed through .fxml files that draws the map and is the platform connecting player to the logic of the game.
