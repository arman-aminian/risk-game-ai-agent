package Models;

import Genetics.Gen;
import Genetics.Genetic;
import Modules.HeuristicFeatures;
import Modules.Heuristics.DraftHeuristics;
import Modules.Minimax;

import java.util.ArrayList;
import java.util.Random;

public class AIPlayer {
    public static int nodeIndex = 0;
    private static int INFP = Integer.MAX_VALUE;
    private static int INFN = Integer.MIN_VALUE;
    static Player A;
    static Player B;
    public static Gen gen1;
    public static Gen gen2;


    public static void main(String[] args) {
        Map map = new Map();
        Territory[][] territories = new Territory[FinalValues.MAP_SIZE][FinalValues.MAP_SIZE];
        for (int i = 0; i < FinalValues.MAP_SIZE; i++) {
            for (int j = 0; j < FinalValues.MAP_SIZE; j++) {
                String name;
                if (getRandomInt(2) == 1)
                    name = "A";
                else name = "B";
                territories[i][j] = new Territory(name, getRandomInt(4) + 1);
            }
        }
        map.setMap(territories);
//        showMap(map);
        Player[] players = {A, B};
//        attack(A, map, players);
    }

    public static Attack attack(Player player, Map map, Player[] players, int round){
        A = player;
        for (Player player1 : players) {
            if (!player1.getName().equals(player.getName())){
                B = player1;
                break;
            }
        }
        TreeNode root = new TreeNode(true , 0 , FinalValues.TREE_MAX_LEVEL  , nodeIndex);
        nodeIndex++;
        Map newMap = new Map(map);

        int temp = 0;
        for (int i = 0; i < players.length; i++) {
            if (players[i].getName().equals(player.getName())){
                temp = i;
                break;
            }
        }
        root = makeTree(root, player, newMap, 2, players, round, temp);

       return Minimax.getNextAttack(root);
    }

    private static TreeNode makeTree(TreeNode root, Player player, Map map, int numberOfAttacksLeft, Player[] players, int round, int playerIndex) {  //works with DFS
//        System.out.println("createeeeeeeee");
        Map childMap = new Map(map);
        Player temp = player;
        boolean isChangingType = false;

        if (root.getDepth_node() == 0){
            root.setValue_node(calculateHeuristic(childMap, player, players));
            return root;
        }

        ArrayList<Attack> chances = attackChances(temp, childMap);
        for (int i = 0; i < chances.size(); i++) {
            childMap = new Map(map);
            //add childes of current node with attack
            int attacksLeft;
            boolean type;
            if (numberOfAttacksLeft == 0) {
                attacksLeft = 2;
                type = !root.isType_node();

                if (playerIndex == players.length-1) {
                    temp = players[0];
                    playerIndex = 0;
                }
                else {
                    temp = players[playerIndex + 1];
                    playerIndex++;
                }

                isChangingType = true;
            }
            else {
                attacksLeft = numberOfAttacksLeft-1;
                type = root.isType_node();
            }
            numberOfAttacksLeft--;

            TreeNode child = new TreeNode(type, 0, root.getDepth_node() - 1, chances.get(i), nodeIndex);
            nodeIndex++;
//            showMap(childMap);
            childMap = updateMap(childMap, chances.get(i));
//            System.out.println("d : " + child.getDepth_node() + " on  " + chances.get(i).toString());
//            showMap(childMap);

            if (isChangingType){
                childMap = DraftHeuristics.draftPerformer(player, childMap);
            }

            child = makeTree(child, temp, childMap, attacksLeft, players, round, playerIndex);
            root.addChild(child);
        }

        if (round < 30) {
            childMap = new Map(map);

            //add noOp TreeNode to root.child
            if (playerIndex == players.length-1) {
                temp = players[0];
                playerIndex = 0;
            }
            else {
                temp = players[playerIndex + 1];
                playerIndex++;
            }
            TreeNode noOpChild = new TreeNode(!root.isType_node(), 0, root.getDepth_node() - 1, nodeIndex);
            nodeIndex++;
//        showMap(childMap);
            childMap = DraftHeuristics.draftPerformer(player, childMap);
            noOpChild = makeTree(noOpChild, temp, childMap, 2, players, round,  playerIndex);
            root.addChild(noOpChild);
        }
        return root;
    }

    private static Map updateMap(Map map, Attack chance) {
//        System.out.println("was : " + chance.getiStart() + ":" + chance.getjStart() + "  =>  " + map.getMap()[chance.getiStart()][chance.getjStart()].getUnits()
//            + "  to" + "  " + chance.getiTarget() + ":" + chance.getjTarget() + "  =>  " + map.getMap()[chance.getiTarget()][chance.getjTarget()].getUnits()
//            + " by army = " + chance.getArmySize());
        map.getMap()[chance.getiStart()][chance.getjStart()]
                .setUnits(map.getMap()[chance.getiStart()][chance.getjStart()].getUnits() - chance.getArmySize());

        map.getMap()[chance.getiTarget()][chance.getjTarget()]
                .setOwner(chance.getAttacker());

        map.getMap()[chance.getiTarget()][chance.getjTarget()]
                .setUnits(chance.getArmySize() - map.getMap()[chance.getiTarget()][chance.getjTarget()].getUnits());

//        System.out.println("is : " + chance.getiStart() + ":" + chance.getjStart() + "  =>  " + map.getMap()[chance.getiStart()][chance.getjStart()].getUnits()
//                + "  to" + "  " + chance.getiTarget() + ":" + chance.getjTarget() + map.getMap()[chance.getiTarget()][chance.getjTarget()].getUnits());

        return map;
    }

    private static double calculateHeuristic(Map map, Player player, Player[] players) {
//        double res = 0;

//        double[] aWeights = {2.5, 4.25, 4.8, 3.5};
//        double[] bWeights = {8.15, 2.85, 2.45, 5.3};
//        double[] features = HeuristicFeatures.features(0, players, map);
//        if (player.getName().equals("A")) {
//            if (players[0].getName().equals("A")) {
//                for (int i = 0; i < features.length; i++) {
//                    res += (aWeights[i] * features[i]);
//                }
//            } else {
//                for (int i = 0; i < features.length; i++) {
//                    res += (aWeights[i] * features[i]);
//                }
//            }
//        } else {
//            if (players[0].getName().equals("B")) {
//                for (int i = 0; i < features.length; i++) {
//                    res += (bWeights[i] * features[i]);
//                }
//            } else {
//                for (int i = 0; i < features.length; i++) {
//                    res += (bWeights[i] * features[i]);
//                }
//            }
//        }
//
//        return (int) res;

        double[] features = HeuristicFeatures.features(0, players, map);
        Gen gen;
        if (players[0].getName().equals(player.getName()))
            gen = new Gen(gen1);
        else gen = new Gen(gen2);

        return ((gen.w1 * features[0]) + (gen.w2 * features[1]) + (gen.w3 * features[2]) + (gen.w4 * features[3]));
    }

    private static ArrayList<Attack> attackChances(Player player, Map map) {
        Territory[][] myMap = map.getMap();
        ArrayList<Attack> chances = new ArrayList<>();
        for (int i = 0; i < FinalValues.MAP_SIZE; i++) {
            for (int j = 0; j < FinalValues.MAP_SIZE; j++) {
//                System.out.println(i + " : " + j);
                if (!myMap[i][j].getOwner().equals(player.getName()))
                    continue;
                if (isOnBorder(myMap, player, i, j)){
                    int temp, max = FinalValues.MINIMUM_DIFFERENCE_TO_ATTACK;
                    Attack attack = new Attack(i, j);
                    attack.setAttacker(player.getName());

                    temp = i;
                    if (j - 1 >= 0) {
                        if (!myMap[temp][j - 1].getOwner().equals(player.getName()))
                            if (myMap[i][j].getUnits() - myMap[temp][j - 1].getUnits() > max) {
                                max = myMap[i][j].getUnits() - myMap[temp][j - 1].getUnits();
                                attack.setiTarget(temp);
                                attack.setjTarget(j - 1);
                                attack.setArmySize(myMap[temp][j - 1].getUnits() + (max+1)/2);
                            }
                    }
                    if (j + 1 <= myMap.length - 1) {
                        if (!myMap[temp][j + 1].getOwner().equals(player.getName()))
                            if (myMap[i][j].getUnits() - myMap[temp][j + 1].getUnits() > max) {
                                max = myMap[i][j].getUnits() - myMap[temp][j + 1].getUnits();
                                attack.setiTarget(temp);
                                attack.setjTarget(j + 1);
                                attack.setArmySize(myMap[temp][j + 1].getUnits() + (max+1)/2);
                            }
                    }

                    temp = j;
                    if (i - 1 >= 0) {
                        if (!myMap[i - 1][temp].getOwner().equals(player.getName()))
                            if (myMap[i][j].getUnits() - myMap[i - 1][temp].getUnits() > max) {
                                max = myMap[i][j].getUnits() - myMap[i - 1][temp].getUnits();
                                attack.setiTarget(i - 1);
                                attack.setjTarget(temp);
                                attack.setArmySize(myMap[i - 1][temp].getUnits() + (max+1)/2);
                            }
                    }
                    if (i + 1 <= myMap.length - 1) {
                        if (!myMap[i + 1][temp].getOwner().equals(player.getName()))
                            if (myMap[i][j].getUnits() - myMap[i + 1][temp].getUnits() > max) {
                                max = myMap[i][j].getUnits() - myMap[i + 1][temp].getUnits();
                                attack.setiTarget(i + 1);
                                attack.setjTarget(temp);
                                attack.setArmySize(myMap[i + 1][temp].getUnits() + (max+1)/2);
                            }
                    }

                    if (max > FinalValues.MINIMUM_DIFFERENCE_TO_ATTACK){
                        chances.add(attack);
//                        System.out.println(attack.toString());
                    }
                }
            }
        }

//        System.out.println("chance size : " + chances.size());
        return chances;
    }

    private static boolean isOnBorder(Territory[][] myMap, Player player, int i, int j) {
        int temp;

        temp = i;
        if (j - 1 >= 0) {
            if (!myMap[temp][j - 1].getOwner().equals(player.getName()))
                return true;
        }
        if (j + 1 <= myMap.length - 1) {
//                System.out.println(myMap[temp][j + 1].getOwner() + i + j);
            if (!myMap[temp][j + 1].getOwner().equals(player.getName()))
                return true;
        }

        temp = j;
        if (i - 1 >= 0) {
            if (!myMap[i - 1][temp].getOwner().equals(player.getName()))
                return true;
        }
        if (i + 1 <= myMap.length - 1) {
//                System.out.println(myMap[temp][j + 1].getOwner() + i + j);
            if (!myMap[i + 1][temp].getOwner().equals(player.getName()))
                return true;
        }

        return false;
    }

    private static int getRandomInt(int i){
        Random random = new Random();
        int num = random.nextInt();
        if (num<0)
            num*=(-1);
        return num%i;
    }

    private static void showMap(Map map) {
        for (int i = 0; i < FinalValues.MAP_SIZE; i++) {
            for (int j = 0; j < FinalValues.MAP_SIZE; j++) {
                System.out.print(map.getMap()[i][j].getOwner() + map.getMap()[i][j].getUnits() + "  ");
            }
            System.out.println("\n");
        }
    }
}
