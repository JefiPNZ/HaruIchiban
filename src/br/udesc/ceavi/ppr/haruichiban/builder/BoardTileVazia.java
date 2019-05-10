package br.udesc.ceavi.ppr.haruichiban.builder;

import br.udesc.ceavi.ppr.haruichiban.model.ModelBoardTile;

/**
 * Controi um ModelBoardTile vaziu
 * @author Gustavo C Santos
 * @since 10/05/2019
 *
 */
public class BoardTileVazia implements IBuilder<ModelBoardTile> {

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
    }

}
