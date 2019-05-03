package br.udesc.ceavi.ppr.haruichiban.model;

/**
 *
 * @author Jeferson Penz, Gustavo C. Santos
 */
public class ModelPlayer {

    private int points;
    protected TitleOfGardener title;

    public ModelPlayer() {
        this.points = 0;
        this.title = new UntitledGardener();
    }
    
    public void addPoint(int newPoints) {
        this.points += newPoints;
    }

    public int getPoints() {
        return points;
    }

    public TitleOfGardener getTitle() {
        return title;
    }
    
    public void becameUntitledGardener() throws Exception{
        this.title.becomeUntitledGardener(this);
    }
    
    public void becomeJuniorGardener() throws Exception{
        this.title.becomeJuniorGardener(this);
    }
    
    public void becomeSeniorGardener() throws Exception{
        this.title.becomeSeniorGardener(this);
    }
}
