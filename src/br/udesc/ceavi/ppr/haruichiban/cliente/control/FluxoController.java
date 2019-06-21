package br.udesc.ceavi.ppr.haruichiban.cliente.control;

import br.udesc.ceavi.ppr.haruichiban.cliente.control.channel.ModelPost;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces.IFluxoController;
import br.udesc.ceavi.ppr.haruichiban.cliente.state.State;

/**
 *
 * @author Gustavo C Santos
 * @since 12/05/2019
 *
 */
public class FluxoController implements IFluxoController {

    private State state;

    public FluxoController() {
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public void endGame() {

    }

    @Override
    public void startGame() {
        GameClienteController.getInstance().getPackageOutput().newPost(ModelPost.LOGADO_COM_SUCESSO, "");
    }

    @Override
    public void setNewFase(String parametro) {
        //index 1 - nome da fase
        try {
            this.state = (State) Class.forName("br.udesc.ceavi.ppr.haruichiban.cliente.state." + parametro).newInstance();
            this.state.executar();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException("N\u00E3o foi PosivelImplementar a nova Fase");
        }
    }

}
