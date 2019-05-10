package br.udesc.ceavi.ppr.haruichiban.model;

import br.udesc.ceavi.ppr.haruichiban.model.Nenufera;
import java.awt.Color;

/**
 *
 * @author Jeferson Penz
 */
public abstract class FactoryPecas {
    
    public abstract Flor createFlor(Color cor, int valor, ModelPlayer playerOrigem);
    public abstract Nenufera createNenufera();
    public abstract Sapo createSapo(Color cor);
    
}
