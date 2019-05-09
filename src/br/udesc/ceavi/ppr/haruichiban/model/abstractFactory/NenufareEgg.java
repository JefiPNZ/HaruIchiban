package br.udesc.ceavi.ppr.haruichiban.model.abstractFactory;

import br.udesc.ceavi.ppr.haruichiban.exceptions.CanNotChangeSideNenufareException;
import java.awt.Color;

/**
 *
 * @author Gustavo C Santos
 * @since 09/05/2019
 *
 */
public class NenufareEgg extends Nenufare {

    private Color cor;

    public NenufareEgg(int x, int y, float rotacao) {
        super(x, y, rotacao);
        this.showDarkSide = false;
    }

    @Override
    public boolean isShowYouDarkSide() {
        return showDarkSide;
    }

    @Override
    public boolean haveAnEgg() {
        return true;
    }

    @Override
    public void changeSideNenufare() throws CanNotChangeSideNenufareException {
        if (showDarkSide) {
            throw new CanNotChangeSideNenufareException();
        }
        showDarkSide = true;
    }

    public void setColor(Color cor) {
        this.cor = cor;
    }

}
