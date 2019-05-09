package br.udesc.ceavi.ppr.haruichiban.model.abstractFactory;

import br.udesc.ceavi.ppr.haruichiban.exceptions.CanNotChangeSideNenufareException;

/**
 *
 * @author Gustavo C Santos
 * @since 09/05/2019
 *
 */
public class NenufareComum extends Nenufare {

    private boolean showDarkSize;

    public NenufareComum(int x, int y, float rotacao) {
        super(x, y, rotacao);
        this.showDarkSize = false;
    }

    @Override
    public boolean isShowYouDarkSide() {
        return showDarkSize;
    }

    @Override
    public boolean haveAnEgg() {
        return false;
    }

    @Override
    public void changeSideNenufare() throws CanNotChangeSideNenufareException {
        if (showDarkSize) {
            throw new CanNotChangeSideNenufareException();
        }
        showDarkSize = true;
    }

}
