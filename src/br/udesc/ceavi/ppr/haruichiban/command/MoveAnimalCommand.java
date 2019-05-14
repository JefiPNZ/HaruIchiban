package br.udesc.ceavi.ppr.haruichiban.command;

import br.udesc.ceavi.ppr.haruichiban.control.IBoardController;
import br.udesc.ceavi.ppr.haruichiban.model.ModelBoardTile;
import br.udesc.ceavi.ppr.haruichiban.model.animais.Animal;

/**
 *
 * @author Gustavo C Santos
 * @since 14/05/2019
 *
 */
public class MoveAnimalCommand implements Command {

    private Animal animal;
    private ModelBoardTile boardTile;
    private IBoardController boardController;

    public MoveAnimalCommand(Animal animal, ModelBoardTile boardTile, IBoardController boardController) {
        this.animal = animal;
        this.boardTile = boardTile;
        this.boardController = boardController;
    }

    @Override
    public void execute() {
        boardTile.getFolha().colocarPecaNaFolha(animal);
        boardController.renderBoard();
    }

}
