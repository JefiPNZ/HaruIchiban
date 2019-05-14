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
public class SeniorNewDrakLeaf implements BoardMovement {

    private IPlayerController player;
    private IBoardController boardController;
    private Point leafDrak;
    private IFluxoController fluxoController;

    public SeniorNewDrakLeaf(IPlayerController player, IBoardController boardController, IFluxoController fluxoController) {
        this.player = player;
        this.boardController = boardController;
        this.fluxoController = fluxoController;
        GameController.getInstance().notificaMudancaEstado("Junior Escolha Qual Folha Deseja Vira a Fase");
        System.out.println(this.getClass().getSimpleName());
    }

    @Override
    public boolean addPoint(Point positionBoard) {
        if (leafDrak == null) {
            leafDrak = positionBoard;
            return true;
        }
        return false;
    }

    @Override
    public boolean isReady() {
        return leafDrak != null;
    }

    @Override
    public synchronized void execute() {
        boardController.getBoardTile(leafDrak).getFolha().virarFolha();
        boardController.setFolhaEscura(leafDrak);
        boardController.renderBoard();
        boardController.removerBoardMovement();
//        fluxoController.selecaoFlorEscuraFinalizada();
    }

    @Override
    public boolean tableInteraction() {
        return true;
    }

    private boolean validar() {
//        ModelBoardTile boardTile = boardController.getBoardTile(leafDrak);
//        if (!boardTile.hasFolha()) {
//            player.notificarSimples("A Posição Escolhida Não Tem Folha");
//            this.leafDrak = null;
//            return false;
//        }
//        if (boardTile.getFolha().hasAnimal()) {
//            //Chamar sem quebrar o fluxo
//        }
//        if (boardTile.getFolha().hasPeca()) {
//            player.notificarSimples("A Posição Escolhida Ja Tem Flor e Não Pode Ser Virada");
//            this.leafDrak = null;
//            return false;
//        }
        return true;
    }

}
