package Modules;

import Models.FinalValues;
import Models.Map;
import Models.Player;

public class HeuristicFeatures {
    public static double[] features(int turnNumber, Player[] players, Map map) {
        double homeRatio = 0;
        double soliderRatio = 0;
        double totalSoldiers = 0;
        double totalRatioOfHouseEnemyNeighbours = 0;
        double totalRatioOfSoldiersEnemeyNeighbours = 0;

        for (int i = 0; i < FinalValues.MAP_SIZE; i++) {
            for (int j = 0; j < FinalValues.MAP_SIZE; j++) {
                if (players[turnNumber].getName().equals(map.getMap()[i][j].getOwner())) {
                    homeRatio++;
                    soliderRatio += map.getMap()[i][j].getUnits();
                    double[] temp = neighbourIsEnemy(turnNumber, i, j, players, map);
//                    System.out.println("ij " + i + " " + j + " ");

//                    for (double v : temp) {
//                        System.out.print(v + " ");
//                    }
//                    System.out.println("");
//                    System.out.println(tempp);
                    totalRatioOfHouseEnemyNeighbours += temp[0];
                    totalRatioOfSoldiersEnemeyNeighbours += temp[1];
                }

                totalSoldiers += map.getMap()[i][j].getUnits();

            }
        }


        double[] features = {homeRatio / 100, soliderRatio / totalSoldiers,
                totalRatioOfHouseEnemyNeighbours / homeRatio, totalRatioOfSoldiersEnemeyNeighbours / (double) homeRatio};

        return features;
    }


    public static double[] neighbourIsEnemy(int turnNumber, int i, int j, Player[] players, Map map) {

        String houseOwner = players[turnNumber].getName();
        int unitsOfHouse = map.getMap()[i][j].getUnits();

        int neighbours = 0;
        double enemyNeighbours = 0;
        double ratio = 0;
        double[] answers = new double[2];

        if (i - 1 > 0) {
            ++neighbours;
            if (!map.getMap()[i - 1][j].getOwner().equals(houseOwner)) {
                ++enemyNeighbours;
                ratio += (double) unitsOfHouse / (double) (unitsOfHouse + map.getMap()[i - 1][j].getUnits());
//                System.out.println("ratioio1 " + ratio);
            }
        }
        if (i + 1 < FinalValues.MAP_SIZE) {
            ++neighbours;
            if (!map.getMap()[i + 1][j].getOwner().equals(houseOwner)) {
                ++enemyNeighbours;
                ratio += (double) unitsOfHouse / (double) (unitsOfHouse + map.getMap()[i + 1][j].getUnits());
//                System.out.println("ratio2 " + ratio);

            }
        }

        if (j - 1 > 0) {
            ++neighbours;
            if (!map.getMap()[i][j - 1].getOwner().equals(houseOwner)) {
                ++enemyNeighbours;
                ratio += (double) unitsOfHouse / (double) (unitsOfHouse + map.getMap()[i][j - 1].getUnits());
//                System.out.println("ratio3 " + ratio);

            }
        }
        if (j + 1 < FinalValues.MAP_SIZE) {
            ++neighbours;
            if (!map.getMap()[i][j + 1].getOwner().equals(houseOwner)) {
                ++enemyNeighbours;
                ratio += (double) unitsOfHouse / (double) (unitsOfHouse + map.getMap()[i][j + 1].getUnits());
//                System.out.println("ratio4 " + ratio);
            }
        }

        answers[0] = enemyNeighbours / neighbours;
        if (enemyNeighbours == 0)
            answers[1] = 1;
        else {

            answers[1] = ratio / enemyNeighbours;
        }
        return answers;

    }
}
