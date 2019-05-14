package br.udesc.ceavi.ppr.haruichiban.command;

import br.udesc.ceavi.ppr.haruichiban.control.IBoardController;
import br.udesc.ceavi.ppr.haruichiban.model.ModelBoardTile;
import br.udesc.ceavi.ppr.haruichiban.model.flores.Flor;

/**
 *
 * @author Gustavo C Santos
 * @since 14/05/2019
 *
 */
public class FlowerBoardCommand implements Command {

    private Flor flor;
    private ModelBoardTile boardTile;
    private IBoardController boardController;

    public FlowerBoardCommand(Flor flor, ModelBoardTile boardTile, IBoardController boardController) {
        this.flor = flor;
        this.boardTile = boardTile;
        this.boardController = boardController;
    }

    @Override
    public void execute() {
        boardTile.getFolha().colocarPecaNaFolha(flor);
        boardController.renderBoard();
    }

}
