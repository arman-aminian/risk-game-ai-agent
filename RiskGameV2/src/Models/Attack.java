package Models;

public class Attack {
    private int iStart;
    private int jStart;
    private int iTarget;
    private int jTarget;

    private int armySize;
    private String attacker;

    public Attack(int iStart, int jStart, int iTarget, int jTarget, int armySize, String attacker) {
        this.iStart = iStart;
        this.jStart = jStart;
        this.iTarget = iTarget;
        this.jTarget = jTarget;
        this.armySize = armySize;
        this.attacker = attacker;
    }

    public Attack(int iStart, int jStart) {
        this.iStart = iStart;
        this.jStart = jStart;
    }

    public Attack(int iStart, int jStart, int armySize) {
        this.iStart = iStart;
        this.jStart = jStart;
        this.armySize = armySize;
    }

    public int getiStart() {
        return iStart;
    }

    public void setiStart(int iStart) {
        this.iStart = iStart;
    }

    public int getjStart() {
        return jStart;
    }

    public void setjStart(int jStart) {
        this.jStart = jStart;
    }

    public int getiTarget() {
        return iTarget;
    }

    public void setiTarget(int iTarget) {
        this.iTarget = iTarget;
    }

    public int getjTarget() {
        return jTarget;
    }

    public void setjTarget(int jTarget) {
        this.jTarget = jTarget;
    }

    public int getArmySize() {
        return armySize;
    }

    public void setArmySize(int armySize) {
        this.armySize = armySize;
    }

    public String getAttacker() {
        return attacker;
    }

    public void setAttacker(String attacker) {
        this.attacker = attacker;
    }

    @Override
    public String toString() {
        return "Attack{" +
                "iStart=" + iStart +
                ", jStart=" + jStart +
                ", iTarget=" + iTarget +
                ", jTarget=" + jTarget +
                ", armySize=" + armySize +
                ", attacker='" + attacker + '\'' +
                '}';
    }
}
