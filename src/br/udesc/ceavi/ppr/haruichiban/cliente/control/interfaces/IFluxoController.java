package br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces;

import br.udesc.ceavi.ppr.haruichiban.cliente.state.State;

/**
 *
 * @author Gustavo C Santos
 * @since 12/05/2019
 *
 */
public interface IFluxoController {

    public void startGame();

    public void endGame();

    public State getState();

    public void setNewFase(String parametro);
}
