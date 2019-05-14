package br.udesc.ceavi.ppr.haruichiban.boardmovement;

import br.udesc.ceavi.ppr.haruichiban.command.FlowerBoardCommand;
import br.udesc.ceavi.ppr.haruichiban.control.GameController;
import br.udesc.ceavi.ppr.haruichiban.control.IBoardController;
import br.udesc.ceavi.ppr.haruichiban.control.IPlayerController;
import br.udesc.ceavi.ppr.haruichiban.model.ModelBoardTile;
import java.awt.Point;
import br.udesc.ceavi.ppr.haruichiban.control.IFluxoController;

/**
 *
 * @author Gustavo C Santos
 * @since 13/05/2019
 *
 */
public class JuniorFlowerBoard implements BoardMovement {

    private IPlayerController player;
    private IBoardController boardController;
    private Point localLerf;
    private final IFluxoController fluxoController;

    public JuniorFlowerBoard(IPlayerController player, IBoardController boardController,
            IFluxoController fluxoController) {
        this.player = player;
        this.boardController = boardController;
        this.fluxoController = fluxoController;
    }

    @Override
    public boolean addPoint(Point positionBoard) {
        this.localLerf = positionBoard;
        return true;
    }

    @Override
    public boolean isReady() {
        return localLerf != null;
    }

    @Override
    public synchronized void execute() {
        GameController.getInstance().executeCommand(
                new FlowerBoardCommand(
                        player.removeFlower(),
                        boardController.getBoardTile(localLerf),
                        boardController));
        
        player.setFase(fluxoController.putFlowerTableEnd());
        fluxoController.putFlowerTable();
        player.notifySimples("");
    }

    @Override
    public boolean tableInteraction() {
        return false;
    }

}
