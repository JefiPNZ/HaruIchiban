package br.udesc.ceavi.ppr.haruichiban.builder;

import br.udesc.ceavi.ppr.haruichiban.control.GameController;
import br.udesc.ceavi.ppr.haruichiban.exceptions.NenufareJaPossuiUmaPecaEmCimaException;
import br.udesc.ceavi.ppr.haruichiban.model.ModelBoardTile;
import br.udesc.ceavi.ppr.haruichiban.model.folha.Folha;
import java.awt.Color;

/**
 * Controi um ModelBoardTile vaziu
 *
 * @author Gustavo C Santos
 * @since 10/05/2019
 *
 */
public class BoardTileComFlorComPaiEFilhoteVermelho implements IBuilder<ModelBoardTile> {

    private ModelBoardTile board;

    @Override
    public ModelBoardTile getProduto() {
        return board;
    }

    @Override
    public void reset() {
        this.board = new ModelBoardTile();
    }

    @Override
    public void constroiPartes() {
        try {
            Folha f = GameController.getInstance().getFactoryPecas().createFolha();
            f.setFilhote(GameController.getInstance().getFactoryPecas().createFilhote(new Color(255, 15, 35)));
            f.colocarPecaNaFolha(GameController.getInstance().getFactoryPecas().createAnimal(new Color(255, 15, 35)));
            this.board.addFolha(f);
        } catch (NenufareJaPossuiUmaPecaEmCimaException ex) {
        }
    }

}
