package br.udesc.ceavi.ppr.haruichiban.builder;

import br.udesc.ceavi.ppr.haruichiban.control.GameController;
import br.udesc.ceavi.ppr.haruichiban.exceptions.CanNotChangeSideNenufareException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.NenufareJaPossuiUmaPecaEmCimaException;
import br.udesc.ceavi.ppr.haruichiban.model.ModelBoardTile;
import br.udesc.ceavi.ppr.haruichiban.model.folha.Folha;
import java.awt.Point;

/**
 *
 * @author Jeferson Penz
 */
public abstract class BoardBuilder {
    
    protected Point posicaoAnimalTopo = null;
    protected Point posicaoAnimalBaixo = null;
    protected Point posicaoVitoriaPreta = null;
    protected boolean[][] tabuleiro = null;
    protected ModelBoardTile[][] board;

    public ModelBoardTile[][] getProduto() {
        return board;
    }

    public abstract void reset();

    public abstract void constroiPartes();
    
}
