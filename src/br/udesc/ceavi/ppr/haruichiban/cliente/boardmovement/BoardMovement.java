package br.udesc.ceavi.ppr.haruichiban.cliente.boardmovement;

import br.udesc.ceavi.ppr.haruichiban.cliente.utils.Diretion;
import java.awt.Point;

/**
 *
 * @author Gustavo C Santos
 * @since 13/05/2019
 *
 */
public interface BoardMovement {

    public boolean addPoint(Point positionBoard);

    public boolean addDiretion(Diretion deretion);

    public void execute();

    /**
     * Tem Interacao Com o Tabulerio
     * @return se necessita de interecao com o tabuleiro
     */
    public boolean tableInteraction();

}
