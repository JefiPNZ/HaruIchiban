package br.udesc.ceavi.ppr.haruichiban.cliente.state;

import br.udesc.ceavi.ppr.haruichiban.cliente.boardmovement.AutoFloweringBoardMovement;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.GameClienteController;

/**
 *
 * @author Gustavo C
 */
public class AutomaticFloweringtState extends State  {

    @Override
    public void executar() {
        GameClienteController.getInstance().getBoardController().initBoardMovement(new AutoFloweringBoardMovement());
    }

}
