package br.udesc.ceavi.ppr.haruichiban.boardmovement;

import br.udesc.ceavi.ppr.haruichiban.utils.Posicao;

/**
 *
 * @author Gustavo C Santos
 * @since 13/05/2019
 *
 */
public interface BoardMovement {

    public boolean addPoint(Posicao positionBoard);

    public void execute();

    public boolean tableInteraction();

}
