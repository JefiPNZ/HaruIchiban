package br.udesc.ceavi.ppr.haruichiban.cliente.state;

import br.udesc.ceavi.ppr.haruichiban.cliente.boardmovement.SeniorNewDrakLeafBoardMovement;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.GameClienteController;

/**
 *
 * @author Gustavo C
 */
public class NewDrakLeftState extends State  {

    @Override
    public void executar() {
        GameClienteController.getInstance().getPlayer().notifySimples("Escolha o local da nova Folha Escura");
        GameClienteController.getInstance().getBoardController().initBoardMovement(new SeniorNewDrakLeafBoardMovement());
    }


}
