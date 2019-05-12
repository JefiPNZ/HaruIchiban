package br.udesc.ceavi.ppr.haruichiban.control;

/**
 *
 * @author Jeferson Penz
 */
public class ScoreTestController implements IScoreController{

    @Override
    public int getTopPlayerScore() {
        return 2;
    }

    @Override
    public int getBotPlayerScore() {
        return 5;
    }
    
}
