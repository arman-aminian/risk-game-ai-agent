package Models;

public class Territory {
    private String owner;
    private int units;
    private int coordinateX;
    private int coordinateY;
    public Territory(Territory territory){
        owner = territory.getOwner();
        coordinateY = territory.getX();
        coordinateX = territory.getY();
        units = territory.getUnits();
    }
    public Territory(String owner, int units) {
        this.owner = owner;
        this.units = units;
    }

    public Territory(String owner, int units, int coordinateX, int coordinateY) {
        this.owner = owner;
        this.units = units;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public int getX() {
        return this.coordinateX;
    }
    public int getY() {
        return this.coordinateY;
    }
}
