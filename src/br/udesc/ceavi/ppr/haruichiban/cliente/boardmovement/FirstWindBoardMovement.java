package br.udesc.ceavi.ppr.haruichiban.cliente.boardmovement;

import br.udesc.ceavi.ppr.haruichiban.cliente.control.GameClienteController;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces.IBoardController;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces.IPlayerController;
import br.udesc.ceavi.ppr.haruichiban.cliente.model.ModelBoardTileProxy;
import br.udesc.ceavi.ppr.haruichiban.cliente.state.State;
import br.udesc.ceavi.ppr.haruichiban.cliente.utils.Diretion;
import java.awt.Point;

/**
 *
 * @author Gustavo C Santos
 * @since 13/05/2019
 *
 */
public class FirstWindBoardMovement implements BoardMovement {

    private IPlayerController player;
    private IBoardController boardController;
    private Point origem;
    private Point fim;
    private Diretion destino;

    public FirstWindBoardMovement() {
        this.player = GameClienteController.getInstance().getPlayer();
        this.boardController = GameClienteController.getInstance().getBoardController();
        boardController.notifyDirectionAtivar();
    }

    @Override
    public boolean addPoint(Point positionBoard) {
        if (validacaoOrigem(positionBoard)) {
            if (origem == null) {
                player.notifySimples("Folha escolhida, escolha a dire\u00E7\u00E3o");
            } else {
                player.notifySimples("Nova folha escolhida, escolha a dire\u00E7\u00E3o");
            }
            origem = positionBoard;
            return true;
        }
        return false;
    }

    private boolean validacaoOrigem(Point positionBoard) {
        ModelBoardTileProxy boardTile = boardController.getBoardTile(positionBoard);
        if (!boardTile.hasFolha()) {
            player.notifySimples("S\u00f3 pode mover uma Folha");
            return false;
        }
        return true;
    }

    public boolean isReady() {
        return origem != null && destino != null;
    }

    @Override
    public synchronized void execute() {
        boardController.removeBoardMovement();
        State state = GameClienteController.getInstance().getFluxoController().getState();
        state.addParametroToFase(origem);
        state.addParametroToFase(destino);
        state.setEndFase();
        boardController.removeBoardMovement();
        player.notifySimples("");
    }

    @Override
    public boolean tableInteraction() {
        return true;
    }

    private boolean validarMovimento(Point newPoint, Diretion p) {
        switch (p) {
            case NORTE:
                if (0 <= newPoint.y) {
                    if (boardController.getBoardTile(newPoint).hasFolha()) {
                        return validarMovimento(new Point(newPoint.x, newPoint.y - 1), p);
                    } else {
                        fim = newPoint;
                        return true;
                    }
                } else {
                    return false;
                }
            case SUL:
                if (boardController.getAlturaTabuleiro() > newPoint.y) {
                    if (boardController.getBoardTile(newPoint).hasFolha()) {
                        return validarMovimento(new Point(newPoint.x, newPoint.y + 1), p);
                    } else {
                        fim = newPoint;
                        return true;
                    }
                } else {
                    return false;
                }
            case LEST:
                if (boardController.getLarguraTabuleiro() > newPoint.x) {
                    if (boardController.getBoardTile(newPoint).hasFolha()) {
                        return validarMovimento(new Point(newPoint.x + 1, newPoint.y), p);
                    } else {
                        fim = newPoint;
                        return true;
                    }
                } else {
                    return false;
                }
            case OESTE:
                if (0 <= newPoint.x) {
                    if (boardController.getBoardTile(newPoint).hasFolha()) {
                        return validarMovimento(new Point(newPoint.x - 1, newPoint.y), p);
                    } else {
                        fim = newPoint;
                        return true;
                    }
                } else {
                    return false;
                }
            default:
                return false;
        }
    }

    @Override
    public boolean addDiretion(Diretion deretion) {

        if (origem != null) {
            if (validarMovimento(origem, deretion)) {
                destino = deretion;
                if (isReady()) {
                    execute();
                }
                return true;
            }
            player.notifySimples("Movimento inv\u00e1lido");
        }
        return false;
    }

}
