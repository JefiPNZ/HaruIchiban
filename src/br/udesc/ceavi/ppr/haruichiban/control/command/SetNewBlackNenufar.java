package br.udesc.ceavi.ppr.haruichiban.control.command;

import br.udesc.ceavi.ppr.haruichiban.exceptions.CanNotChangeSideNenufareException;
import br.udesc.ceavi.ppr.haruichiban.model.Nenufera;

/**
 *
 * @author GustavoSantos
 * @since 03/05/2019
 * 
 */
public class SetNewBlackNenufar implements Command{

    private final Nenufera nenufara;

    public SetNewBlackNenufar(Nenufera nenufara) {
        this.nenufara = nenufara;
    }
    
    @Override
    public void execute() {
        try {
            nenufara.virarNenufare();
        } catch (CanNotChangeSideNenufareException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

}
