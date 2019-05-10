package br.udesc.ceavi.ppr.haruichiban.builder;

import br.udesc.ceavi.ppr.haruichiban.control.GameController;
import br.udesc.ceavi.ppr.haruichiban.exceptions.CanNotChangeSideNenufareException;
import br.udesc.ceavi.ppr.haruichiban.model.ModelBoardTile;
import br.udesc.ceavi.ppr.haruichiban.model.folha.Folha;

/**
 *
 * @author Gustavo C Santos
 * @since 10/05/2019
 *
 */
public class BoardTileComFolhaEscura implements IBuilder<ModelBoardTile> {

    private ModelBoardTile tile;

    @Override
    public ModelBoardTile getProduto() {
        return tile;
    }

    @Override
    public void reset() {
        tile = new ModelBoardTile();
    }

    @Override
    public void constroiPartes() {
        Folha folha = GameController.getInstance().getFactoryPecas().createFolha();
        try {
            folha.virarNenufare();
            tile.addFolha(folha);
        } catch (CanNotChangeSideNenufareException ex) {
        }
    }

}
