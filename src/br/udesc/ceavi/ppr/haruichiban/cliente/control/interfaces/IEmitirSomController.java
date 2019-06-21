package br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces;

import br.udesc.ceavi.ppr.haruichiban.cliente.control.observers.IEmitirSomObserver;

/**
 *
 * @author Gustavo C
 */
public interface IEmitirSomController {

    public void ativarEmitirSom();

    public void emitirSom();

    public void desativarEmitirSom();

    public void addObserver(IEmitirSomObserver observer);
}
