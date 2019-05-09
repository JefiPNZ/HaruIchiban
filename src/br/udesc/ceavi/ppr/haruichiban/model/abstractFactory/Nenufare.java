package br.udesc.ceavi.ppr.haruichiban.model.abstractFactory;

import br.udesc.ceavi.ppr.haruichiban.exceptions.CanNotChangeSideNenufareException;
import br.udesc.ceavi.ppr.haruichiban.model.PecaTabuleiro;

/**
 *
 * @author GustavoSantos
 * @since 09/05/2019
 *
 */
public abstract class Nenufare extends PecaTabuleiro {

    protected boolean showDarkSide;

    public Nenufare(int x, int y, float rotacao) {
        super(x, y, rotacao);
    }

    public abstract boolean isShowYouDarkSide();

    public abstract boolean haveAnEgg();

    public abstract void changeSideNenufare() throws CanNotChangeSideNenufareException;
}
