package br.udesc.ceavi.ppr.haruichiban.cliente.state;

import br.udesc.ceavi.ppr.haruichiban.cliente.boardmovement.FirstWindBoardMovement;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.GameClienteController;

/**
 *
 * @author Gustavo C
 */
public class FistWindState extends State  {

    @Override
    public void executar() {
        GameClienteController.getInstance().getBoardController().initBoardMovement(new FirstWindBoardMovement());
    }

    @Override
    public void setEndFase() {
        super.setEndFase();
    }

}
