package br.udesc.ceavi.ppr.haruichiban.builder;

import br.udesc.ceavi.ppr.haruichiban.control.GameController;
import br.udesc.ceavi.ppr.haruichiban.model.ModelBoardTile;
import br.udesc.ceavi.ppr.haruichiban.model.folha.Folha;
import java.awt.Point;

/**
 * Controi um ModelBoardTile vaziu
 *
 * @author Gustavo C Santos
 * @since 10/05/2019
 *
 */
public class BoardGigaBuilder extends BoardBuilder {

    private final Point POSICAO_ANIMAL_TOPO = new Point(1, 1);
    private final Point POSICAO_ANIMAL_BASE = new Point(3, 4);
    private final Point POSICAO_FOLHA_PRETA = new Point(3, 1);

    private final boolean[][] TABULEIRO = {
        {true, false, false, true, false, false, true},
        {false, true, false, true, false, true, false},
        {false, false, true, true, true, false, false},
        {true, true, true, false, true, true, true},
        {false, false, true, true, true, false, false},
        {false, true, false, true, false, true, false},
        {true, false, false, true, false, false, true}
    };

    @Override
    public void reset() {
        this.board = new ModelBoardTile[7][];
        for (int i = 0; i < 7; i++) {
            this.board[i] = new ModelBoardTile[7];
            for (int j = 0; j < 7; j++) {
                this.board[i][j] = new ModelBoardTile();
            }
        }
    }

    @Override
    public void constroiPartes() {
        GameController gcInstance = GameController.getInstance();
        for (int row = 0; row < this.board.length; row++) {
            for (int column = 0; column < this.board[row].length; column++) {
                if (TABULEIRO[row][column]) {
                    Folha folha = gcInstance.getFactoryPecas().createFolha();
                    this.board[row][column].addFolha(folha);
                    Point pos = new Point(column, row);
                    if (POSICAO_FOLHA_PRETA.equals(pos)) {
                        folha.virarFolha();
                        folha.setSempreEscura(true);
                    }
                    if (POSICAO_ANIMAL_TOPO.equals(pos)) {
                        folha.colocarFilhoteNaFolha(gcInstance.getFactoryPecas().createFilhote(gcInstance.getTopPlayer().getColor()));
                        folha.colocarPecaNaFolha(gcInstance.getFactoryPecas().createAnimal(gcInstance.getTopPlayer().getColor()));
                    } else if (POSICAO_ANIMAL_BASE.equals(pos)) {
                        folha.colocarFilhoteNaFolha(gcInstance.getFactoryPecas().createFilhote(gcInstance.getBottomPlayer().getColor()));
                        folha.colocarPecaNaFolha(gcInstance.getFactoryPecas().createAnimal(gcInstance.getBottomPlayer().getColor()));
                    }
                }
            }
        }
    }

    @Override
    public Point getBlack() {
        return POSICAO_FOLHA_PRETA;
    }

}
