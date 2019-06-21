package br.udesc.ceavi.ppr.haruichiban.cliente.boardmovement;

import br.udesc.ceavi.ppr.haruichiban.cliente.control.GameClienteController;
import br.udesc.ceavi.ppr.haruichiban.cliente.model.ModelBoardTileProxy;
import br.udesc.ceavi.ppr.haruichiban.cliente.model.PecaProxy;
import br.udesc.ceavi.ppr.haruichiban.cliente.state.State;
import br.udesc.ceavi.ppr.haruichiban.cliente.utils.Diretion;
import java.awt.Point;

/**
 *
 * @author Gustavo C Santos
 * @since 13/05/2019
 *
 */
public class SeniorFlowerBoardAnimalBoardMovement extends SeniorFlowerBoardBoardMovement implements BoardMovement {
    
    private Point animalLocal;
    private PecaProxy animal;
    
    public SeniorFlowerBoardAnimalBoardMovement(Point animalLocal) {
        super();
        this.animalLocal = animalLocal;
        super.newAnimalPosition = null;
    }
    
    @Override
    public boolean addPoint(Point positionBoard) {
        if (animal != null && newAnimalPosition == null) {
            if (validar(positionBoard)) {
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
    
    private boolean validar(Point positionBoard) {
        ModelBoardTileProxy boardTile = boardController.getBoardTile(positionBoard);
        if (!boardTile.hasFolha()) {
            player.notifySimples("Animal Apenas Pode Ser Colocado Na Folha");
            return true;
        }
        if (boardTile.hasPeca()) {
            player.notifySimples("Animal Apenas Pode Ser Colocado Folha Vazia");
            this.newAnimalPosition = null;
            return true;
        }
        if (boardTile.isEscura()) {
            player.notifySimples("Animal Apenas Pode Ser Colocado Folha Clara");
            this.newAnimalPosition = null;
            return true;
        }
        return false;
    }
    
    @Override
    public boolean isReady() {
        return newAnimalPosition != null;
    }
    
    @Override
    public synchronized void execute() {
        State state = GameClienteController.getInstance().getFluxoController().getState();
        state.addParametroToFase("Animal");
        state.addParametroToFase(animalLocal);
        state.addParametroToFase(newAnimalPosition);
        state.setEndFase();
        boardController.removeBoardMovement();
    }
    
    @Override
    public boolean tableInteraction() {
        return true;
    }
    
    public void executePutFlower() {
        GameClienteController gameController = GameClienteController.getInstance();
        ModelBoardTileProxy boardTile = boardController.getBoardTile(animalLocal);
        
        this.animal = boardTile.getPeca();
        animal.setPecaName("Flor" + gameController.getEstacao());
        animal.setPecaCor(GameClienteController.getInstance().getPlayer().getColor());
        
        boardController.renderBoard();
        boardController.initBoardMovement(this);
        gameController.notificaMudancaEstado("Mover Animal");
    }
    
    @Override
    public boolean addDiretion(Diretion deretion) {
        return false;
    }
    
}
