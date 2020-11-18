package Genetics;

import java.util.Arrays;
import java.util.Random;

public class Genetic {
    public static Gen[] gens;
    public static Gen[][] tournament;
    private static Random random = new Random();
    private static int i;
    private static int j;
    private static int t;
    private static int tournamentSize = 5;
    public static int generationNumber = 1;
    private static boolean iSeted;
    private static boolean finished;

    public static void addTotalGame(int i){
        gens[i].addTotalGame();
    }

    public static void addTotalWins(int i){
        gens[i].addTotalWins();
    }

    public static void init(){
        gens = new Gen[30];
        i = 0;
        j = 1;
        t = 0;
        iSeted = false;
        for (int j = 0; j < gens.length; j++) {
            gens[j] = new Gen(getRandomInt(30), getRandomInt(30), getRandomInt(30), getRandomInt(30));
        }
        makeTournaments();
    }

    public static void makeTournaments(){
        tournament = new Gen[30 / tournamentSize][tournamentSize];
        for (int i = 0; i < 30/ tournamentSize; i++) {
            for (int j = 0; j < tournamentSize; j++) {
                tournament[i][j] = new Gen(gens[getRandomInt(30)]);
            }
        }
    }

    private static double getRandom(){
        return random.nextDouble();
    }

    private static int getRandomInt(int i){
        Random random = new Random();
        int num = random.nextInt();
        if (num<0)
            num*=(-1);
        return num%i;
    }

    public static Gen getNextGen(){
        if (!iSeted) {
            System.out.println(t + " : " + i);
            System.out.println(tournament[t][i].toString());
            iSeted = true;
            return tournament[t][i];
        }
        else {
            System.out.println(t + " : " + j);
            System.out.println(tournament[t][j].toString());
            return tournament[t][j];
        }
    }

    public static void updateParameters(){
        iSeted = false;
        if (j < tournamentSize - 1){
            j++;
        } else if (i < tournamentSize - 2){
            i++;
            j = i+1;
        } else if ((t < tournamentSize - 1)){
            i = 0;
            j = 1;
            t++;
        } else {
            generationNumber++;

            if (generationNumber == 4){
                finished = true;
                return;
            }
            Gen[] newGens = new Gen[15];
            Arrays.sort(gens);

            newGens = generateNewGens(gens);
            for (int i1 = 0; i1 < 10; i1++) {
                gens[i1] = newGens[i1];
            }
            i = 0;
            j = 1;
            t = 0;
        }

        System.out.println("generation number : " + generationNumber);
        System.out.println("new i,j  ==>  " + i + " : " + j);
    }

    private static Gen[] generateNewGens(Gen[] gens) {
        Gen[] bests = new Gen[20];
        for (int i = 0; i < 20; i++) {
            bests[i] = gens[gens.length - 1 - i];
        }

        Gen[] newGens = new Gen[10];
        Gen[] crossOverTemp = crossOver(bests);
        for (int i = 0; i < 8; i++) {
            newGens[i] = crossOverTemp[i];
        }
        for (int i = 8; i < 10; i++) {
            newGens[i] = mutation(bests);
        }
        return newGens;
    }

    private static Gen mutation(Gen[] bests) {
        int randomIndex = getRandomInt(bests.length);
        int randomW = getRandomInt(4);
        Gen res = new Gen(bests[randomIndex]);

        double newValue = getRandom();
        if (randomW == 0)
            res.w1 = newValue;
        else if (randomW == 1)
            res.w2 = newValue;
        else if (randomW ==2)
            res.w3 = newValue;
        else res.w4 = newValue;

        return res;
    }

    private static Gen[] crossOver(Gen[] bests) {
        Gen[] res = new Gen[8];
        for (int i1 = 0; i1 < 8; i1++) {
            Gen firstRand = bests[getRandomInt(20)];
            Gen secondRand = bests[getRandomInt(20)];
            for (int i2 = 0; i2 < 4; i2++) {
                res[i1] = new Gen((firstRand.w1 + secondRand.w1) / 2, (firstRand.w2 + secondRand.w2) / 2, (firstRand.w3 + secondRand.w3) / 2, (firstRand.w4 + secondRand.w4) / 2);
            }
        }
        return res;
    }
}
