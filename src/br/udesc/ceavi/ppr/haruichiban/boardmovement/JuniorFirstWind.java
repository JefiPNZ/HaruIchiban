package br.udesc.ceavi.ppr.haruichiban.boardmovement;

import br.udesc.ceavi.ppr.haruichiban.control.GameController;
import br.udesc.ceavi.ppr.haruichiban.control.IBoardController;
import br.udesc.ceavi.ppr.haruichiban.control.IFluxoController;
import br.udesc.ceavi.ppr.haruichiban.control.IPlayerController;
import java.awt.Point;

/**
 *
 * @author Gustavo C Santos
 * @since 13/05/2019
 *
 */
public class JuniorFirstWind implements BoardMovement {

    private IPlayerController player;
    private IBoardController boardController;
    private Point origim, destino;
    private IFluxoController fluxoController;

    public JuniorFirstWind(IPlayerController player, IBoardController boardController, IFluxoController fluxoController) {
        this.player = player;
        this.boardController = boardController;
        this.fluxoController = fluxoController;

        System.out.println(this.getClass().getSimpleName());
        GameController.getInstance().notificaMudancaEstado("Junior Escolha Qual Folha Deseja Mover");
    }

    @Override
    public boolean addPoint(Point positionBoard) {
        if (origim == null) {
            origim = positionBoard;
            GameController.getInstance().notificaMudancaEstado("Para Onde Quer Move?");
        } else if (destino == null) {
            destino = positionBoard;
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean isReady() {
        return destino != null && origim != null;
    }

    @Override
    public synchronized void execute() {
        validar();
//        boardController.moveTo(origim, destino);
        boardController.renderBoard();
        boardController.removerBoardMovement();
//        fluxoController.newDarkLeaf();
    }

    @Override
    public boolean tableInteraction() {
        return true;
    }

    private void validar() {
    }

}
