package br.udesc.ceavi.ppr.haruichibanl.command;

import br.udesc.ceavi.ppr.haruichiban.exceptions.CanNotChangeSideNenufareException;
import br.udesc.ceavi.ppr.haruichiban.model.folha.Folha;

/**
 *
 * @author GustavoSantos
 * @since 03/05/2019
 * 
 */
public class SetNewBlackNenufar implements Command{

    private final Folha nenufara;

    public SetNewBlackNenufar(Folha nenufara) {
        this.nenufara = nenufara;
    }
    
    @Override
    public void execute() {
        try {
            nenufara.virarFolha();
        } catch (CanNotChangeSideNenufareException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

}
