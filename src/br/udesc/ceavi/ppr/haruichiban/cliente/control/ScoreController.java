package br.udesc.ceavi.ppr.haruichiban.cliente.control;

import br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces.IScoreController;

/**
 *
 * @author Jeferson Penz
 */
public class ScoreController implements IScoreController{

    @Override
    public int getTopPlayerScore() {
        return GameClienteController.getInstance().getTopPlayer().getPlayerScore();
    }

    @Override
    public int getBotPlayerScore() {
        return GameClienteController.getInstance().getBottomPlayer().getPlayerScore();
    }
    
}
