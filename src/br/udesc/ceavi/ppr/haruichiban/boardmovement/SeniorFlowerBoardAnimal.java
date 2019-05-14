package br.udesc.ceavi.ppr.haruichiban.boardmovement;

import br.udesc.ceavi.ppr.haruichiban.control.Fase;
import br.udesc.ceavi.ppr.haruichiban.control.GameController;
import br.udesc.ceavi.ppr.haruichiban.control.IBoardController;
import br.udesc.ceavi.ppr.haruichiban.control.IFluxoController;
import br.udesc.ceavi.ppr.haruichiban.control.IPlayerController;
import br.udesc.ceavi.ppr.haruichiban.model.ModelBoardTile;
import br.udesc.ceavi.ppr.haruichiban.model.animais.Animal;
import java.awt.Point;

/**
 *
 * @author Gustavo C Santos
 * @since 13/05/2019
 *
 */
public class SeniorFlowerBoardAnimal extends SeniorFlowerBoard implements BoardMovement {

    private Point animalLocal;
    private Animal animal;

    public SeniorFlowerBoardAnimal(IPlayerController player,
            IBoardController boardController, IFluxoController fluxoController, Point animalLocal) {
        super(player, boardController, fluxoController);
        this.animalLocal = animalLocal;
       
        super.localLerf = null;
    }

    @Override
    public boolean addPoint(Point positionBoard) {
        if (animal != null && localLerf == null) {
            if (validar(positionBoard)) {
                return false;
            }
            localLerf = positionBoard;
            return true;
        }
        return false;
    }

    private boolean validar(Point positionBoard) {
        ModelBoardTile boardTile = boardController.getBoardTile(positionBoard);
        if (!boardTile.hasFolha()) {
            player.notifySimples("A Posicao Escolhida Não Tem Folha");
            return true;
        }
        if (boardTile.getFolha().isEscura()) {
            player.notifySimples("A Posicao Escolhida é Invalida Folha Escura");
            this.localLerf = null;
            return true;
        }
        if (boardTile.getFolha().hasAnimal()) {
            player.notifySimples("A Posicao Escolhida Tem Outro Animal");
            this.localLerf = null;
            return true;
        }
        if (boardTile.getFolha().hasPeca()) {
            player.notifySimples("A Posicao Escolhida Ja Tem Flor");
            this.localLerf = null;
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
        boardTile.getFolha().colocarPecaNaFolha(animal);
        boardController.removeBoardMovement();
        boardController.renderBoard();
        player.setFase(fluxoController.putFlowerTableEnd());
        fluxoController.putFlowerTable();
    }

    @Override
    public boolean tableInteraction() {
        return true;
    }

    public void executePutFlower() {
        ModelBoardTile boardTile = boardController.getBoardTile(animalLocal);

        this.animal = (Animal) boardTile.getFolha().removerPecaDeFlor();
        boardController.renderBoard();

        boardTile.getFolha().colocarPecaNaFolha(player.removeFlower());
        boardController.renderBoard();
        player.setFase(Fase.MOVE_ANIMAL);
        
        boardController.initBoardMovement(this);
        GameController.getInstance().notificaMudancaEstado("Flor Do Senior Colocada No Tabuleiro");

        GameController.getInstance().notificaMudancaEstado("Escolha Um Novo Local Para O Animal");
    }

}
