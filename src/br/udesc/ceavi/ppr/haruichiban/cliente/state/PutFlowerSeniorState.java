package br.udesc.ceavi.ppr.haruichiban.cliente.state;

import br.udesc.ceavi.ppr.haruichiban.cliente.boardmovement.SeniorFlowerBoardBoardMovement;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.GameClienteController;

/**
 *
 * @author Gustavo C
 */
public class PutFlowerSeniorState extends State {


    @Override
    public void executar() {
        GameClienteController.getInstance().getBoardController().initBoardMovement(new SeniorFlowerBoardBoardMovement());
    }

    @Override
    public void setEndFase() {
        super.setEndFase();
    }

}
