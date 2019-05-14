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
public class SeniorFlowerBoardAnimal extends SeniorFlowerBoard implements BoardMovement {

    private Point animalLocal;

    public SeniorFlowerBoardAnimal(IPlayerController player,
            IBoardController boardController, IFluxoController fluxoController, Point animalLocal) {
        super(player, boardController, fluxoController);
        this.animalLocal = animalLocal;
        System.out.println(this.getClass().getSimpleName());
        GameController.getInstance().notificaMudancaEstado("Senior Escolha Qual Em "
                + "Que Folha Quer Colocar Sua Flor");
    }

    @Override
    public boolean addPoint(Point positionBoard) {
        if (localLerf == null) {
            ModelBoardTile boardTile = boardController.getBoardTile(localLerf);
            if (!boardTile.hasFolha()) {
                player.notifySimples("A Posicao Escolhida Não Tem Folha");
                return false;
            }
            if (boardTile.getFolha().isEscura()) {
                player.notifySimples("A Posicao Escolhida é Invalida Folha Escura");
                this.localLerf = null;
                return false;
            }
            if (boardTile.getFolha().hasAnimal()) {
                player.notifySimples("A Posicao Escolhida Tem Outro Animal");
                this.localLerf = null;
                return false;
            }
            if (boardTile.getFolha().hasPeca()) {
                player.notifySimples("A Posicao Escolhida Ja Tem Flor");
                this.localLerf = null;
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean isReady() {
        return localLerf != null;
    }

    @Override
    public synchronized void execute() {
        if (validarPoint()) {
            ModelBoardTile boardTile = boardController.getBoardTile(localLerf);
        }
    }

    private boolean validarPoint() {
        ModelBoardTile boardTile = boardController.getBoardTile(localLerf);
        if (boardTile.getFolha().hasAnimal()) {
            //Chamar sem quebrar o fluxo
        }
        return true;
    }

    @Override
    public boolean tableInteraction() {
        return true;
    }

}
