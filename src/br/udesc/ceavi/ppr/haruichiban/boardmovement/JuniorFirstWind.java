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
public class JuniorFirstWind implements BoardMovement {

    private IPlayerController player;
    private IBoardController boardController;
    private Point origim, destino;
    private IFluxoController fluxoController;
    private ModelBoardTile[][] tabuleiro;

    public JuniorFirstWind(IPlayerController player, IBoardController boardController, IFluxoController fluxoController) {
        this.player = player;
        this.boardController = boardController;
        this.fluxoController = fluxoController;
        this.tabuleiro = boardController.getTabuleiro();
    }

    @Override
    public boolean addPoint(Point positionBoard) {
        if (origim == null) {
            if (validacaoOrigem(positionBoard)) {
                origim = positionBoard;
                return true;
            }
        } else if (destino == null) {
            if (validarPosicaoDestino(positionBoard)) {
                destino = positionBoard;
                return true;
            }
        } else {
            return false;
        }
        return false;
    }

    private boolean validarPosicaoDestino(Point positionBoard) {
        if (origim.x == positionBoard.x + 1 && origim.y == positionBoard.y
                || origim.x == positionBoard.x - 1 && origim.y == positionBoard.y
                || origim.x == positionBoard.x && origim.y + 1 == positionBoard.y
                || origim.x == positionBoard.x && origim.y - 1 == positionBoard.y) {
            if (boardController.isPosicaoValida(positionBoard.x, positionBoard.y)) {
                return true;
            }
        }
        return false;
    }

    private boolean validacaoOrigem(Point positionBoard) {
        ModelBoardTile boardTile = boardController.getBoardTile(positionBoard);
        if (!boardTile.hasFolha()) {
            player.notifySimples("So Pode Mover Uma Folha");
            return false;
        }
        return true;
    }

    @Override
    public boolean isReady() {
//        return origim != null && destino != null;
        return true;
    }

    @Override
    public synchronized void execute() {
//        if (verificarValidadeMovimento()) {
//            realizarMovimento();
//            boardController.renderBoard();
            boardController.removeBoardMovement();
            player.setFase(fluxoController.firstWindEnd());
            fluxoController.firstWind();
//        } else {
//            destino = null;
//        }
        player.notifySimples("");
    }

    @Override
    public boolean tableInteraction() {
        return true;
    }

    private boolean verificarValidadeMovimento() {
        return true;
    }

    private void realizarMovimento() {
    }
}
