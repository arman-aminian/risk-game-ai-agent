package Models;

public class Map {

    private Territory[][] map;

    public Map(Map map){
        this.map = new Territory[FinalValues.MAP_SIZE][FinalValues.MAP_SIZE];
        Territory[][] m = map.getMap();
        for (int i = 0; i < FinalValues.MAP_SIZE; i++) {
            for (int j = 0; j < FinalValues.MAP_SIZE; j++) {
               this.map[i][j] = new Territory(m[i][j]);
            }
        }
    }
    public Map() {
        this.map = new Territory[FinalValues.MAP_SIZE][FinalValues.MAP_SIZE];;
    }

    public Territory[][] getMap() {
        return map;
    }

    public void setMap(Territory[][] map) {
        this.map = map;
    }

    public Territory getTerritoryMap(int x , int y)
    {
        return this.map[x][y];
    }

    public void changeTerritoryUnit (int x , int y , int unit)
    {
        int temp = this.map[x][y].getUnits();
        this.map[x][y].setUnits(temp + unit);
    }
}
