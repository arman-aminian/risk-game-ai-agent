package Models;

import java.util.ArrayList;

public class Player {
    private String name;
    private boolean isAI;
    private ArrayList<Territory> territories;

    public void addTerritory(Territory territory){
        territories.add(territory);
    }

    public void removeTerritory(Territory territory){
        territories.remove(territory);
    }

    public Player(String name, boolean isAI) {
        this.name = name;
        this.isAI = isAI;
        territories = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAI() {
        return isAI;
    }

    public void setAI(boolean AI) {
        isAI = AI;
    }

    public ArrayList<Territory> getTerritories() {
        return territories;
    }

    public void setTerritories(ArrayList<Territory> territories) {
        this.territories = territories;
    }

}
