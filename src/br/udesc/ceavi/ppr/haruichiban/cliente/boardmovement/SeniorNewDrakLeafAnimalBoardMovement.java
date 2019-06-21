package br.udesc.ceavi.ppr.haruichiban.cliente.boardmovement;

import br.udesc.ceavi.ppr.haruichiban.cliente.control.GameClienteController;
import br.udesc.ceavi.ppr.haruichiban.cliente.model.ModelBoardTileProxy;
import br.udesc.ceavi.ppr.haruichiban.cliente.model.PecaProxy;
import br.udesc.ceavi.ppr.haruichiban.cliente.state.State;
import br.udesc.ceavi.ppr.haruichiban.cliente.utils.Diretion;
import java.awt.Point;
import javax.swing.SwingUtilities;

/**
 *
 * @author Gustavo C Santos
 * @since 13/05/2019
 *
 */
public class SeniorNewDrakLeafAnimalBoardMovement extends SeniorNewDrakLeafBoardMovement implements BoardMovement {

    private final Point animalLocal;
    private PecaProxy animal;

    public SeniorNewDrakLeafAnimalBoardMovement(Point animalLocal) {
        super();
        this.animalLocal = animalLocal;
        super.newAnimalPosition = null;
    }

    @Override
    public boolean addPoint(Point positionBoard) {
        if (newAnimalPosition == null) {
            System.out.println("Posicao Para Sapo : " + positionBoard);
            if (!validar(positionBoard)) {
                System.out.println("Posicao Valida : " + positionBoard);
                newAnimalPosition = positionBoard;
            } else {

                System.out.println("Posicao Nao : " + positionBoard);
            }
            return true;
        }
        if (isReady()) {
            execute();
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
            return true;
        }

        if (boardTile.isEscura()) {
            player.notifySimples("Animal Apenas Pode Ser Colocado Folha Clara");
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
        boardTile.setPeca(null);
        boardTile.tornarEscura();
        SwingUtilities.invokeLater(() -> boardController.renderBoard());
        boardController.initBoardMovement(this);
        GameClienteController.getInstance().threadPoolExecute(() -> gameController.notificaMudancaEstado("Mover Animal"));
    }

    @Override
    public boolean addDiretion(Diretion deretion) {
        return false;
    }

}
