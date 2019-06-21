package br.udesc.ceavi.ppr.haruichiban.cliente.boardmovement;

import br.udesc.ceavi.ppr.haruichiban.cliente.control.GameClienteController;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces.IBoardController;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces.IPlayerController;
import br.udesc.ceavi.ppr.haruichiban.cliente.model.ModelBoardTileProxy;
import br.udesc.ceavi.ppr.haruichiban.cliente.utils.Diretion;
import java.awt.Point;
import br.udesc.ceavi.ppr.haruichiban.cliente.state.State;

/**
 *
 * @author Gustavo C Santos
 * @since 13/05/2019
 *
 */
public class SeniorNewDrakLeafBoardMovement implements BoardMovement {

    protected IPlayerController player;
    protected IBoardController boardController;
    protected Point newAnimalPosition;

    public SeniorNewDrakLeafBoardMovement() {
        this.player = GameClienteController.getInstance().getPlayer();
        this.boardController = GameClienteController.getInstance().getBoardController();
    }

    @Override
    public boolean addPoint(Point positionBoard) {
        if (newAnimalPosition == null) {

            if (validandoPosicao(positionBoard)) {
                return false;
            }

            newAnimalPosition = positionBoard;
            if (isReady()) {
                execute();
            }
            return true;
        }
        return false;
    }

    private boolean validandoPosicao(Point positionBoard) {
        if (positionBoard == null) {
            return false;
        }
        ModelBoardTileProxy boardTile = boardController.getBoardTile(positionBoard);
        if (!boardTile.hasFolha()) {
            player.notifySimples("A Posicao Escolhida N\u00E3o Tem Folha");
            return true;
        }
        if (boardTile.hasAnimal()) {
            player.notifySimples("Folha tem Animal");
            player.notifySimples("Folha Escura, Escolha A Nova Posi\u00E7\u00E3o Do Animal");
            boardController.removeBoardMovement();
            SeniorNewDrakLeafAnimalBoardMovement juniorFlowerBoard
                    = new SeniorNewDrakLeafAnimalBoardMovement(positionBoard);
            juniorFlowerBoard.executePutFlower();
            boardController.initBoardMovement(juniorFlowerBoard);

            return true;
        }
        if (boardTile.hasPeca()) {
            player.notifySimples("A Posicao Escolhida Ja Tem Flor");
            return true;
        }
        return false;
    }

    public boolean isReady() {
        return newAnimalPosition != null;
    }

    @Override
    public synchronized void execute() {
        State state = GameClienteController.getInstance().getFluxoController().getState();
        state.addParametroToFase("");
        state.addParametroToFase(newAnimalPosition);
        state.setEndFase();
        boardController.removeBoardMovement();
    }

    @Override
    public boolean tableInteraction() {
        return true;
    }

    @Override
    public boolean addDiretion(Diretion deretion) {
        return false;
    }

}
