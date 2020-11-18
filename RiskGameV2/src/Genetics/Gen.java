package Genetics;

public class Gen implements Comparable<Gen>{
    public double totalGamePlayed;
    public double totalGameWins;
    public double w1;
    public double w2;
    public double w3;
    public double w4;

    public void addTotalGame(){
        this.totalGamePlayed++;
        System.out.println("tottaaaal" + totalGameWins);
    }

    public void addTotalWins(){
        this.totalGameWins++;
    }

    public Gen(Gen gen){
        this.w1 = gen.w1;
        this.w2 = gen.w2;
        this.w3 = gen.w3;
        this.w4 = gen.w4;
    }

    public Gen(double w1, double w2, double w3, double w4) {
        this.w1 = w1;
        this.w2 = w2;
        this.w3 = w3;
        this.w4 = w4;
        totalGamePlayed = 0;
        totalGameWins = 0;
    }

    @Override
    public int compareTo(Gen g) {
        if ((this.totalGameWins / this.totalGamePlayed) - (g.totalGameWins / g.totalGamePlayed) > 0)
            return 1;
        else if ((this.totalGameWins / this.totalGamePlayed) - (g.totalGameWins / g.totalGamePlayed) < 0)
            return -1;
        else return 0;
    }

    @Override
    public String toString() {
        return "Gen{" +
                "w1=" + w1 +
                ", w2=" + w2 +
                ", w3=" + w3 +
                ", w4=" + w4 +
                '}';
    }
}
