package br.udesc.ceavi.ppr.haruichiban.model.abstractFactory;

import br.udesc.ceavi.ppr.haruichiban.exceptions.CanNotChangeSideNenufareException;

/**
 *
 * @author Gustavo C Santos
 * @since 09/05/2019
 *
 */
public class NenufareDarkSides extends Nenufare {

    public NenufareDarkSides(int x, int y, float rotacao) {
        super(x, y, rotacao);
        this.showDarkSide = true;
    }

    @Override
    public boolean isShowYouDarkSide() {
        return showDarkSide;
    }

    @Override
    public boolean haveAnEgg() {
        return false;
    }

    @Override
    public void changeSideNenufare() throws CanNotChangeSideNenufareException {
        throw new CanNotChangeSideNenufareException();
    }

}
