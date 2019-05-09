package br.udesc.ceavi.ppr.haruichiban.model.abstractFactory;

import java.awt.Color;

/**
 *
 * @author Gustavo C Santos
 * @since 09/05/2019
 *
 */
public class FactoryNenufare {

    public Nenufare createNenufareTwoSideDark(int x, int y, float rotacao) {
        return new NenufareDarkSides(x, y, rotacao);
    }

    public Nenufare createNenufareEggRed(int x, int y, float rotacao) {
        NenufareEgg nenufareEggRed = new NenufareEgg(x, y, rotacao);
        nenufareEggRed.setColor(Color.RED);
        return nenufareEggRed;
    }
    
    public Nenufare createNenufareEggYellow(int x, int y, float rotacao) {
        NenufareEgg nenufareEggYellow = new NenufareEgg(x, y, rotacao);
        nenufareEggYellow.setColor(Color.YELLOW);
        return nenufareEggYellow;
    }

    public Nenufare createNenufareComum(int x, int y, float rotacao) {
        return new NenufareComum(x, y, rotacao);
    }
}
