package br.udesc.ceavi.ppr.haruichiban.abstractfactory;

import br.udesc.ceavi.ppr.haruichiban.model.animais.Sapo;
import br.udesc.ceavi.ppr.haruichiban.model.animais.Animal;
import br.udesc.ceavi.ppr.haruichiban.control.GameController;
import br.udesc.ceavi.ppr.haruichiban.model.filhote.Filhote;
import br.udesc.ceavi.ppr.haruichiban.model.Flor.Flor;
import br.udesc.ceavi.ppr.haruichiban.model.Flor.FlorDePrimaveira;
import br.udesc.ceavi.ppr.haruichiban.model.folha.Folha;
import br.udesc.ceavi.ppr.haruichiban.model.ModelPlayer;
import br.udesc.ceavi.ppr.haruichiban.model.folha.Nenufera;
import br.udesc.ceavi.ppr.haruichiban.model.filhote.OvoDeSapo;
import java.awt.Color;

/**
 *
 * @author Jeferson Penz
 */
public class FactoryPecasPrimavera extends FactoryPecas{

    public FactoryPecasPrimavera() {
        System.out.println("Usando Tabuleiro de FactoryPecasPrimavera");
    }

    
    @Override
    public Flor createFlor(Color cor, int valor, ModelPlayer playerOrigem) {
        return new FlorDePrimaveira(GameController.getInstance().getRandomizer().nextFloat() * 20, cor, valor, playerOrigem);
    }

    @Override
    public Folha createFolha() {
        return new Nenufera(GameController.getInstance().getRandomizer().nextFloat() * 360);
    }

    @Override
    public Animal createAnimal(Color cor) {
        return new Sapo(GameController.getInstance().getRandomizer().nextFloat() * 360, cor);
    }

    @Override
    public Filhote createFilhote(Color cor) {
        return new OvoDeSapo(GameController.getInstance().getRandomizer().nextFloat() * 360, cor);
    }
    
}
