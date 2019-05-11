package br.udesc.ceavi.ppr.haruichiban.builder;

import br.udesc.ceavi.ppr.haruichiban.control.GameController;
import br.udesc.ceavi.ppr.haruichiban.exceptions.CanNotChangeSideNenufareException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.NenufareJaPossuiUmaPecaEmCimaException;
import br.udesc.ceavi.ppr.haruichiban.model.ModelBoardTile;
import br.udesc.ceavi.ppr.haruichiban.model.folha.Folha;
import java.awt.Point;

/**
 * Controi um ModelBoardTile vaziu
 * @author Gustavo C Santos
 * @since 10/05/2019
 *
 */
public class BoardNormalBuilder extends BoardBuilder{
    
    private final Point POSICAO_ANIMAL_TOPO = new Point(2, 1);
    private final Point POSICAO_ANIMAL_BASE = new Point(3, 3);
    private final Point POSICAO_FOLHA_PRETA = new Point(3, 1);
    private static final boolean[][] TABULEIRO = { 
        { true, false,  true,  false,  true},
        {false,  true,  true,   true, false},
        { true,  true, false,   true,  true},
        {false,  true,  true,   true, false},
        { true, false,  true,  false,  true}
    };
    
    @Override
    public void reset() {
        this.board = new ModelBoardTile[5][];
        for (int i = 0; i < 5; i++) {
            this.board[i] = new ModelBoardTile[5];
            for (int j = 0; j < 5; j++) {
                this.board[i][j] = new ModelBoardTile();
            }
        }
    }

    @Override
    public void constroiPartes() {
        GameController gcInstance = GameController.getInstance();
        for (int row = 0; row < this.board.length; row++) {
            for (int column = 0; column < this.board[row].length; column++) {
                if(TABULEIRO[row][column]){
                    Folha folha = gcInstance.getFactoryPecas().createFolha();
                    this.board[row][column].addFolha(folha);
                    Point pos = new Point(column, row);
                    if(POSICAO_FOLHA_PRETA.equals(pos)){
                        try {
                            folha.virarFolha();
                        } catch (CanNotChangeSideNenufareException ex) {}
                    }
                    if(POSICAO_ANIMAL_TOPO.equals(pos)){
                        try {
                            folha.colocarFilhoteNaFolha(gcInstance.getFactoryPecas().createFilhote(gcInstance.getTopPlayer().getColor()));
                            folha.colocarPecaNaFolha(gcInstance.getFactoryPecas().createAnimal(gcInstance.getTopPlayer().getColor()));
                        } catch (NenufareJaPossuiUmaPecaEmCimaException ex) {}
                    }
                    else if(POSICAO_ANIMAL_BASE.equals(pos)){
                        try {
                            folha.colocarFilhoteNaFolha(gcInstance.getFactoryPecas().createFilhote(gcInstance.getBottomPlayer().getColor()));
                            folha.colocarPecaNaFolha(gcInstance.getFactoryPecas().createAnimal(gcInstance.getTopPlayer().getColor()));
                        } catch (NenufareJaPossuiUmaPecaEmCimaException ex) {}
                        
                    }
                }
            }
        }
    }

}