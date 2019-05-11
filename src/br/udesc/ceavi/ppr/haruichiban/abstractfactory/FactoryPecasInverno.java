package br.udesc.ceavi.ppr.haruichiban.abstractfactory;

import br.udesc.ceavi.ppr.haruichiban.model.folha.FolhaSeca;
import br.udesc.ceavi.ppr.haruichiban.model.animais.Pinguim;
import br.udesc.ceavi.ppr.haruichiban.model.filhote.OvoDePinguin;
import br.udesc.ceavi.ppr.haruichiban.model.animais.Animal;
import br.udesc.ceavi.ppr.haruichiban.control.GameController;
import br.udesc.ceavi.ppr.haruichiban.model.Flor;
import br.udesc.ceavi.ppr.haruichiban.model.filhote.Filhote;
import br.udesc.ceavi.ppr.haruichiban.model.folha.Folha;
import br.udesc.ceavi.ppr.haruichiban.model.ModelPlayer;
import br.udesc.ceavi.ppr.haruichiban.model.FlorDeInverno;
import java.awt.Color;

/**
 *
 * @author Jeferson Penz
 */
public class FactoryPecasInverno extends FactoryPecas{

    public FactoryPecasInverno() {
         System.out.println("Usando Tabuleiro de FactoryPecasInverno");
    }

    
    @Override
    public Flor createFlor(Color cor, int valor, ModelPlayer playerOrigem) {
        return new FlorDeInverno(GameController.getInstance().getRandomizer().nextFloat() * 20, cor, valor, playerOrigem);
    }

    @Override
    public Folha createFolha() {
        return new FolhaSeca(GameController.getInstance().getRandomizer().nextFloat() * 360);
    }

    @Override
    public Animal createAnimal(Color cor) {
        return new Pinguim(GameController.getInstance().getRandomizer().nextFloat() * 360, cor);
    }

    @Override
    public Filhote createFilhote(Color cor) {
        return new OvoDePinguin(GameController.getInstance().getRandomizer().nextFloat() * 360, cor);
    }
    
}
