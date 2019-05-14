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
public class MoverAnimal implements BoardMovement {

    private IPlayerController player;
    private IBoardController boardController;
    private Point posicaoAnimal, destino;
    private IFluxoController fluxoController;

    public MoverAnimal(IPlayerController player, IBoardController boardController, IFluxoController fluxoController, Point posicaoAnimal) {
        this.player = player;
        this.boardController = boardController;
        this.fluxoController = fluxoController;

        System.out.println(this.getClass().getSimpleName());
        GameController.getInstance().notificaMudancaEstado("Junior Escolha Qual Folha Deseja Mover");
        this.posicaoAnimal = posicaoAnimal;
    }

    @Override
    public boolean addPoint(Point positionBoard) {
        GameController.getInstance().notificaMudancaEstado("Para Onde Quer Move?");
        if (destino == null) {
            destino = positionBoard;
            return true;
        }
        return false;
    }

    @Override
    public boolean isReady() {
        return destino != null;
    }

    @Override
    public void execute() {
        if (validar()) {
//            boardController.moveAnimalTo(posicaoAnimal, destino);
            boardController.removerBoardMovement();
            boardController.renderBoard();
        } else {
        }
    }

    @Override
    public boolean tableInteraction() {
        return true;
    }

    private boolean validar() {
        ModelBoardTile boardTile = boardController.getBoardTile(destino);
        if (boardTile.hasFolha()) {
            if (boardTile.getFolha().hasPeca()) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

}
