package br.udesc.ceavi.ppr.haruichiban.cliente.control;

import br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces.IEmitirSomController;
import br.udesc.ceavi.ppr.haruichiban.cliente.state.State;
import java.util.ArrayList;
import java.util.List;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.observers.IEmitirSomObserver;

/**
 *
 * @author Gustavo C
 */
public class EmitirSomController implements IEmitirSomController {

    private final List<IEmitirSomObserver> observers;

    public EmitirSomController() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void addObserver(IEmitirSomObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void ativarEmitirSom() {
        observers.forEach(obs -> obs.ativarButao());
    }

    @Override
    public void emitirSom() {
        GameClienteController.getInstance().getPlayer().notifySimples("Emitindo Som");
        State state = GameClienteController.getInstance().getFluxoController().getState();
        desativarEmitirSom();
        state.setEndFase();
    }

    @Override
    public void desativarEmitirSom() {
        observers.forEach(obs -> obs.desativarButao());
    }

}
