package br.udesc.ceavi.ppr.haruichiban.cliente.state;

import br.udesc.ceavi.ppr.haruichiban.cliente.control.GameClienteController;

/**
 *
 * @author Gustavo C
 */
public class ChooseFlowerState extends State  {


    @Override
    public void executar() {
        GameClienteController.getInstance().getPlayer().choseFlowerDeck();
    }

}