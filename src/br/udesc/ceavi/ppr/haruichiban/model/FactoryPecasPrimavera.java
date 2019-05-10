package br.udesc.ceavi.ppr.haruichiban.model;

import br.udesc.ceavi.ppr.haruichiban.control.GameController;
import java.awt.Color;

/**
 *
 * @author Jeferson Penz
 */
public class FactoryPecasPrimavera extends FactoryPecas{

    @Override
    public Flor createFlor(Color cor, int valor, ModelPlayer playerOrigem) {
        return new Flor(GameController.getInstance().getRandomizer().nextFloat() * 20, cor, valor, playerOrigem);
    }

    @Override
    public Nenufera createNenufera() {
        return new Nenufera(GameController.getInstance().getRandomizer().nextFloat() * 360);
    }

    @Override
    public Sapo createSapo(Color cor) {
        return new Sapo(GameController.getInstance().getRandomizer().nextFloat() * 360, cor);
    }
    
}
