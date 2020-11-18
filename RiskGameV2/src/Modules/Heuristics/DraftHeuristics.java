package Modules.Heuristics;
import Models.Map;
import Models.Player;
import Models.Territory;

import java.util.*;

import static Models.FinalValues.MAP_SIZE;


public class DraftHeuristics {

    private static class Pair
    {
        double xBSR;
        int xIndex;

        private Pair (double xBSR , int xIndex)
        {
            this.xBSR = xBSR;
            this.xIndex = xIndex;
        }
    }
    private static class sortArrDesc implements Comparator<Pair>
    {
        // Used for sorting in ascending order of
        // roll number
        public int compare(Pair a, Pair b)
        {
//            if(a.xBSR > b.xBSR)
//                return 1;
//            return 0;

            return (int)(b.xBSR-a.xBSR);
        }
    }

    private static ArrayList<Pair> NBSRarr1;
    private static ArrayList<Pair> NBSRarr2;
    private static ArrayList<Integer> neighbourOwned;
    private static ArrayList<Pair> BSRXarr;

    private static int DRAFT_TRESHOLD = 2;

    public static Map draftPerformer(Player currentPlayer , Map currentMap)
    {
        int unitsToAdd = currentPlayer.getTerritories().size();

        NBSRarr1 = new ArrayList<>(0);
        NBSRarr2 = new ArrayList<>(0);
        neighbourOwned = new ArrayList<>(0);
        BSRXarr = new ArrayList<>(0);

        int BSTx = 0;
        int neighbors = 0;
        double BSRx = 0;
        double sumBSRz = 0;
        double sumNBSRz = 0;

        for (int i = 0; i<unitsToAdd ; i++) {
            BSTx = 0;
            BSRx = 0;
            neighbors = 0;

            Territory tx = currentPlayer.getTerritories().get(i);

            if(tx.getX()-1 >=0 && !currentMap.getTerritoryMap(tx.getX()-1 , tx.getY()).getOwner().equals(currentPlayer.getName())
            )
                BSTx+=currentMap.getTerritoryMap(tx.getX()-1 , tx.getY()).getUnits();
            else if(tx.getX()-1 >=0)
                neighbors++;

            if(tx.getX()+1 < MAP_SIZE && !currentMap.getTerritoryMap(tx.getX()+1 , tx.getY()).getOwner().equals(currentPlayer.getName()))
                BSTx+=currentMap.getTerritoryMap(tx.getX()+1 , tx.getY()).getUnits();
            else if(tx.getX()+1 < MAP_SIZE )
                neighbors++;

            if(tx.getY()-1 >=0 && !currentMap.getTerritoryMap(tx.getX() , tx.getY()-1).getOwner().equals(currentPlayer.getName()))
                BSTx+=currentMap.getTerritoryMap(tx.getX() , tx.getY()-1).getUnits();
            else if(tx.getY()-1 >=0 )
                neighbors++;

            if(tx.getY()+1 < MAP_SIZE && !currentMap.getTerritoryMap(tx.getX() , tx.getY()+1).getOwner().equals(currentPlayer.getName()))
                BSTx+=currentMap.getTerritoryMap(tx.getX() , tx.getY()+1).getUnits();
            else if(tx.getY()+1 < MAP_SIZE )
                neighbors++;


            BSRx = BSTx / tx.getUnits();
            BSRXarr.add(new Pair(BSRx , i));
//            System.out.println("bstx at  " + tx.getX() + " and " + tx.getY() +" = "+ BSTx +" and BSRx = " + BSRx + "  neighbours owned = " + neighbors);
            neighbourOwned.add(neighbors);

        }

        Collections.sort(BSRXarr, new sortArrDesc());

//        for (Pair p:BSRXarr
//             ) {
//            System.out.println("BSRx at  " + currentPlayer.getTerritories().get(p.xIndex).getX()+" and " + currentPlayer.getTerritories().get(p.xIndex).getY() +" = "+ p.xBSR );
//        }

        // Filtering BSRs and use threshold
        for (int i = 0; i < BSRXarr.size(); i++) {
            Pair tempPair = BSRXarr.get(i);
            sumBSRz+=tempPair.xBSR;

            if(NBSRarr1.size()<BSRXarr.size()/DRAFT_TRESHOLD)
            {
                NBSRarr1.add(tempPair);
            }
        }
//        for (Pair p:NBSRarr1
//        ) {
//            System.out.println("trimmed BSRx at  " + currentPlayer.getTerritories().get(p.xIndex).getX()+" and " + currentPlayer.getTerritories().get(p.xIndex).getY() +" = "+ p.xBSR );
//        }
//        System.out.println("sum BSRz = " + sumBSRz);
        for (Pair pair : NBSRarr1
        ) {
            NBSRarr2.add(new Pair(pair.xBSR / sumBSRz , pair.xIndex));
            sumNBSRz += pair.xBSR / sumBSRz;
        }
//        for (Pair p:NBSRarr2
//        ) {
//            System.out.println("NBSRx at  " + currentPlayer.getTerritories().get(p.xIndex).getX()+" and " + currentPlayer.getTerritories().get(p.xIndex).getY() +" = "+ p.xBSR );
//        }
//        System.out.println("sum NBSRz = " + sumNBSRz);


//        System.out.println("units : " + unitsToAdd);
//        System.out.println(currentPlayer.getTerritories().size());
        if (unitsToAdd == 1 && currentPlayer.getTerritories().size() == 1){
            currentMap.changeTerritoryUnit(currentPlayer.getTerritories().get(0).getX(), currentPlayer.getTerritories().get(0).getY(), 1);
            return currentMap;
        }
        while(unitsToAdd >0) {

//            System.out.println(unitsToAdd);
            int tempUnitsToAdd = unitsToAdd;
//            System.out.println("left units to add : " + unitsToAdd);
            for (Pair nbsrPair: NBSRarr2) {

                double toAddDouble = (nbsrPair.xBSR / sumNBSRz) * unitsToAdd;
//               System.out.println("before round " + toAddDouble);
                int rounded = (int) Math.round(toAddDouble);
                if (rounded == 0)
                    rounded = 1;
//                System.out.println("adding to   " + currentPlayer.getTerritories().get(nbsrPair.xIndex).getX()+" and " + currentPlayer.getTerritories().get(nbsrPair.xIndex).getY() + " = "  + rounded );

                Territory eqTerr = currentPlayer.getTerritories().get(nbsrPair.xIndex);

                if(tempUnitsToAdd - rounded >=0 )
                {
                    currentMap.changeTerritoryUnit(eqTerr.getX() , eqTerr.getY() , rounded);
                    tempUnitsToAdd -= rounded;
                    if(tempUnitsToAdd == 0)
                        break;

                }
                else
                {
                    currentMap.changeTerritoryUnit(eqTerr.getX() , eqTerr.getY() , tempUnitsToAdd);
                    break;
                }
            }

            unitsToAdd = tempUnitsToAdd;

        }
//        System.out.println("no units to add : " + unitsToAdd);
        return  currentMap;
    }
}