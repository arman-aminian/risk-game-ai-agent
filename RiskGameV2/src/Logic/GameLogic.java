package Logic;

import Genetics.Gen;
import Genetics.Genetic;
import Models.*;
import Modules.Heuristics.DraftHeuristics;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

public class GameLogic {
    private static Map map;
    private static int playersNum;
    private static Player[] players;
    private static Scanner scanner = new Scanner(System.in);
    private static PrintWriter printWriter;

    static {
        try {
            printWriter = new PrintWriter("genetic-scoreboard.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void init() {

//        printWriter.println("hfd");
//        printWriter.flush();
//        printWriter.println("hfd");
//        printWriter.flush();

        AIPlayer.gen1 = Genetic.getNextGen();
        AIPlayer.gen2 = Genetic.getNextGen();


//        System.out.println("Enter number of players :");

        //todo is genetic
//        playersNum = scanner.nextInt();
        playersNum = 2;

        players = new Player[playersNum];
        map = new Map();

        boolean genericTemp = false;
        for (int i = 0; i < playersNum; i++) {
            //todo is genetic
//            System.out.println("Enter " + (i + 1) + "'st player name :");
//            String name = scanner.next();
            String name;
            if (!genericTemp)
            name = "A";
            else name = "B";
//            System.out.println(name + "is an AI player? (yes/no)");
//            String isAI = scanner.next();
            String isAI = "yes";
            boolean ai = (isAI.equals("yes"));
            players[i] = new Player(name, ai);
            genericTemp = true;
        }
        shuffle(players);

        int[] unitsTemp = new int[playersNum];

        Arrays.fill(unitsTemp, ((FinalValues.MAP_SIZE * FinalValues.MAP_SIZE) / playersNum));
        for (int i = 0; i < (FinalValues.MAP_SIZE * FinalValues.MAP_SIZE) % playersNum; i++) unitsTemp[i]++;

        for (int i = 0; i < FinalValues.MAP_SIZE; i++) {
            for (int j = 0; j < FinalValues.MAP_SIZE; j++) {
                int randomOwner = getRandomInt(players.length);
                while (unitsTemp[randomOwner] == 0)
                    randomOwner = getRandomInt(players.length);

                Territory territory = new Territory(players[randomOwner].getName(), FinalValues.INTI_UNIT_NUM, i, j);
                map.getMap()[i][j] = territory;
                players[randomOwner].addTerritory(territory);
                unitsTemp[randomOwner]--;
            }
        }

//        showMap();
    }

    private static void showMap() {
        for (int i = 0; i < FinalValues.MAP_SIZE; i++) {
            for (int j = 0; j < FinalValues.MAP_SIZE; j++) {
                System.out.print(map.getMap()[i][j].getOwner() + map.getMap()[i][j].getUnits() + "  ");
            }
            System.out.println("\n");
        }
    }

    private static void shuffle(Player[] players) {
        ArrayList<Player> temp = new ArrayList<>(Arrays.asList(players));
        for (int i = 0; i < players.length; i++) {
            int random = getRandomInt(temp.size());
            players[i] = temp.get(random);
            temp.remove(random);
        }
    }

    private static int getRandomInt(int i) {
        Random random = new Random();
        int num = random.nextInt();
        if (num < 0)
            num *= (-1);
        return num % i;
    }

    public static void main(String[] args) {
        try {
            printWriter = new PrintWriter("genetic-scoreboard.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Genetic.init();
        while (Genetic.generationNumber < 4) {

            int round = 1;
            Player currentPlayer;
            init();
            while (!gameFinished()) {
                System.out.println("************************     round" + round + "     ************************");
                for (int i = 0; i < playersNum; i++) {
                    currentPlayer = players[i];
//                System.out.print("\t******* turn ==> " + currentPlayer.getName());
//                if (currentPlayer.isAI()){
//                    System.out.println(" (AI Player) *******");
//                } else System.out.println(" (Human Player) *******");
                    draft(currentPlayer);
                    attack(currentPlayer, round);
                    if (gameFinished()) {
                        System.out.println("finished");
                        break;
                    }
                }
                round++;
                showMap();
            }
            showMap();

            for (int i = 0; i < Genetic.gens.length; i++) {
                if (Genetic.gens[i].toString().equals(AIPlayer.gen1.toString())){
                    if (getWinner().equals("A")) {
                        Genetic.addTotalWins(i);
                        printWriter.println(AIPlayer.gen1.toString() + "  wins  " + AIPlayer.gen2.toString());
                        printWriter.flush();
                    }
                    Genetic.addTotalGame(i);
                } else if (Genetic.gens[i].toString().equals(AIPlayer.gen2.toString())){
                    if (getWinner().equals("B")) {
                        Genetic.addTotalWins(i);
                        printWriter.println(AIPlayer.gen2.toString() + "  wins  " + AIPlayer.gen1.toString());
                        printWriter.flush();
                    }
                    Genetic.addTotalGame(i);
                }
            }

            Genetic.updateParameters();
            System.out.println("winner is  ==>  " + getWinner());
            System.out.println("on round " + round + " finiiisheeeed!!!!");
            System.out.println("on round " + round + " finiiisheeeed!!!!");
            System.out.println("on round " + round + " finiiisheeeed!!!!");
            System.out.println("on round " + round + " finiiisheeeed!!!!");
        }
        System.out.println("best gene is = " + Genetic.gens[Genetic.gens.length - 1]);
        printWriter.println("best gene is = " + Genetic.gens[Genetic.gens.length - 1]);
        printWriter.flush();
        Arrays.sort(Genetic.gens);
        printWriter.println("\nfinal genes : ");
        printWriter.flush();

        for (int i = 0; i < Genetic.gens.length; i++) {
            printWriter.println(i + " : " + Genetic.gens[i]);
        }


    }

    private static String getWinner() {
        return map.getMap()[2][2].getOwner();
    }

    private static void draft(Player currentPlayer) {
        System.out.println("\t\t*** draft on " + currentPlayer.getName() + " ***");
        if (currentPlayer.isAI()) {
            map = DraftHeuristics.draftPerformer(currentPlayer, map);
//            showMap();
        } else {

            int max = 0;
            for (int i1 = 0; i1 < FinalValues.MAP_SIZE; i1++) {
                for (int i2 = 0; i2 < FinalValues.MAP_SIZE; i2++) {
                    if (currentPlayer.getName().equals(map.getMap()[i1][i2].getOwner()))
                        max++;
                }
            }
            while (true) {
                System.out.println("\t\t\twant to draft?\n\t\t\tenter node coordinate(otherwise enter -1) :");
//                showMap();
                int iFrom = scanner.nextInt();
                if (iFrom == -1) break;
                int jFrom = scanner.nextInt();

                if (!map.getMap()[iFrom][jFrom].getOwner().equals(currentPlayer.getName())) {
                    System.out.println("Invalid territory!");
                    continue;
                }

                System.out.println("\t\t\tenter number of soldier to add on this territory :");
                int soldiers = scanner.nextInt();
                if (soldiers > max) {
                    System.out.println("\t\t\ttry again! max is " + max);
                    continue;
                }

                map.getMap()[iFrom][jFrom].setUnits(map.getMap()[iFrom][jFrom].getUnits() + soldiers);
                max -= soldiers;

//                showMap();
            }
        }
    }

    private static void attack(Player currentPlayer, int round) {
//        System.out.println("\t\t*** attack on " + currentPlayer.getName() + " ***");
        while (!gameFinished()) {
//            if (round > 30) {
//                break;
//            }
            System.out.println("\t\t\twant to attack?\n\t\t\tenter start node coordinate(otherwise enter -1) :");
//            showMap();

            int iFrom, jFrom, iTarget, jTarget, myArmySize;
            boolean fullForce = false;
            if (currentPlayer.isAI()) {
//                System.out.println("\n dskjsdv");
//                showMap();
                Map temp = new Map(map);

                Attack attack = AIPlayer.attack(currentPlayer, temp, players, round);
                if (attack == null) {
//                    System.out.println("attacks finished");
                    break;
                }
                iFrom = attack.getiStart();
                jFrom = attack.getjStart();
                iTarget = attack.getiTarget();
                jTarget = attack.getjTarget();
                myArmySize = attack.getArmySize();

                //todo check:
//                showMap();
                System.out.println("");
                //todo add this if line to moein
                if ((map.getMap()[iFrom][jFrom].getUnits() - (myArmySize - 1)) <= 0)
                    break;
//                System.out.println(iFrom + " : " + jFrom + "  ==>  " + (map.getMap()[iFrom][jFrom].getUnits() - (myArmySize - 1)));
                if (map.getMap()[iFrom][jFrom].getUnits() == (myArmySize)) {
                    fullForce = true;
                }
                map.getMap()[iFrom][jFrom].setUnits(map.getMap()[iFrom][jFrom].getUnits() - myArmySize);
//                showMap();
                System.out.println("end\n");

            } else {
                iFrom = scanner.nextInt();
                if (iFrom == -1) break;
                jFrom = scanner.nextInt();

                if (!map.getMap()[iFrom][jFrom].getOwner().equals(currentPlayer.getName())) {
                    System.out.println("Invalid territory!");
                    continue;
                }

//                System.out.println("\t\t\tenter number of your army :");
                myArmySize = scanner.nextInt();
                if (myArmySize > map.getMap()[iFrom][jFrom].getUnits())
                    continue;

                //todo check:
                if (map.getMap()[iFrom][jFrom].getUnits() == (myArmySize)) {
                    fullForce = true;
                }
                map.getMap()[iFrom][jFrom].setUnits(map.getMap()[iFrom][jFrom].getUnits() - myArmySize);

//                System.out.println("\t\t\tenter target node coordinate :");
                iTarget = scanner.nextInt();
                jTarget = scanner.nextInt();

                if (!((iFrom == iTarget && (jFrom == jTarget - 1 || jFrom == jTarget + 1)) || (jFrom == jTarget && (iFrom == iTarget - 1 || iFrom == iTarget + 1)))) {
                    System.out.println("invalid target territory!");
                }
            }
            int targetArmySize = map.getMap()[iTarget][jTarget].getUnits();

//            System.out.println("my army size : " + myArmySize + "  ,  enemy army size : " + targetArmySize);

            while (myArmySize > 1 && targetArmySize > 0) {
                int myDice = getRandomInt(6);
                int targetDice = getRandomInt(6);

                if (myDice > targetDice)
                    targetArmySize--;
                else if (myDice < targetDice)
                    myArmySize--;
            }

            if (myArmySize == 1) {
                map.getMap()[iFrom][jFrom].setUnits(map.getMap()[iFrom][jFrom].getUnits() + 1);
                map.getMap()[iTarget][jTarget].setUnits(targetArmySize);
            } else {
                getPlayer(map.getMap()[iTarget][jTarget].getOwner()).removeTerritory(map.getMap()[iTarget][jTarget]);
                //todo check:

                if (fullForce) {
                    map.getMap()[iFrom][jFrom].setUnits(map.getMap()[iFrom][jFrom].getUnits() + 1);
                    map.getMap()[iTarget][jTarget].setUnits(myArmySize - 1);
                    map.getMap()[iTarget][jTarget].setOwner(currentPlayer.getName());
                } else {
                    map.getMap()[iTarget][jTarget].setUnits(myArmySize);
                    map.getMap()[iTarget][jTarget].setOwner(currentPlayer.getName());
                }
                getPlayer(currentPlayer.getName()).addTerritory(map.getMap()[iTarget][jTarget]);
                //todo remove territory from target and add to another
            }
//            showMap();
        }
    }

    private static Player getPlayer(String name) {
        for (Player player : players) {
            if (player.getName().equals(name))
                return player;
        }
        return null;
    }

    private static boolean gameFinished() {
        String s = map.getMap()[0][0].getOwner();
        for (int i = 0; i < FinalValues.MAP_SIZE; i++) {
            for (int j = 0; j < FinalValues.MAP_SIZE; j++) {
                if (!map.getMap()[i][j].getOwner().equals(s))
                    return false;
            }
        }
        return true;
    }
}
