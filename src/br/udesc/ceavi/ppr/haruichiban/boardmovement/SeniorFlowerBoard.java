package br.udesc.ceavi.ppr.haruichiban.boardmovement;

import br.udesc.ceavi.ppr.haruichiban.control.GameController;
import br.udesc.ceavi.ppr.haruichiban.control.IBoardController;
import br.udesc.ceavi.ppr.haruichiban.control.IFluxoController;
import br.udesc.ceavi.ppr.haruichiban.control.IPlayerController;
import br.udesc.ceavi.ppr.haruichiban.model.ModelBoardTile;
import java.awt.Point;

/**
 *
 * @author Gustavo C Santos
 * @since 13/05/2019
 *
 */
public class SeniorFlowerBoard implements BoardMovement {

    protected IPlayerController player;
    protected IBoardController boardController;
    protected Point localLerf;
    protected final IFluxoController fluxoController;

    public SeniorFlowerBoard(IPlayerController player, IBoardController boardController, IFluxoController fluxoController) {
        this.player = player;
        this.boardController = boardController;
        this.fluxoController = fluxoController;
        this.localLerf = null;
       
        GameController.getInstance().notificaMudancaEstado("Senior Escolha Qual Em Que Folha Quer Colocar Sua Flor");
    }

    @Override
    public boolean addPoint(Point positionBoard) {
        if (localLerf == null) {

            if (validandoPosicao(positionBoard)) {
                return false;
            }

            localLerf = positionBoard;
            return true;
        }
        return false;
    }

    private boolean validandoPosicao(Point positionBoard) {
        ModelBoardTile boardTile = boardController.getBoardTile(positionBoard);
        if (!boardTile.hasFolha()) {
            player.notifySimples("A Posicao Escolhida Não Tem Folha");
            return true;
        }
        if (boardTile.getFolha().hasAnimal()) {
            player.notifySimples("A Posicao Escolhida Tem Animal");
            boardController.removeBoardMovement();
            SeniorFlowerBoardAnimal juniorFlowerBoard
                    = new SeniorFlowerBoardAnimal(player, boardController, fluxoController, positionBoard);
            boardController.initBoardMovement(juniorFlowerBoard);
            juniorFlowerBoard.executePutFlower();
            
            return true;
        }
        if (boardTile.getFolha().hasPeca()) {
            player.notifySimples("A Posicao Escolhida Ja Tem Flor");
            return true;
        }
        return false;
    }

    @Override
    public boolean isReady() {
        return localLerf != null;
    }

    @Override
    public synchronized void execute() {
        ModelBoardTile boardTile = boardController.getBoardTile(localLerf);
        boardTile.getFolha().colocarPecaNaFolha(player.removeFlower());
        boardController.renderBoard();
        boardController.removeBoardMovement();
        GameController.getInstance().notificaMudancaEstado("Flor Do Senior Colocada No Tabuleiro");
        player.setFase(fluxoController.putFlowerTableEnd());
        fluxoController.putFlowerTable();
    }

    @Override
    public boolean tableInteraction() {
        return true;
    }

}
