package br.udesc.ceavi.ppr.haruichiban.cliente.state;

import br.udesc.ceavi.ppr.haruichiban.cliente.control.GameClienteController;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.channel.ModelPost;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gustavo C
 */
public abstract class State {

    protected final List<String> parametroFase;
    private final Gson gson;

    public State() {
        this.gson = new Gson();
        this.parametroFase = new ArrayList<>();
    }

    public abstract void executar();

    public void setEndFase() {
        GameClienteController.getInstance().getPackageOutput().newPost(ModelPost.FASE_END, gson.toJson(parametroFase));
    }

    public void addParametroToFase(String paramentro) {
        this.parametroFase.add(paramentro);
    }

    public void addParametroToFase(Object paramentro) {
        this.parametroFase.add(gson.toJson(paramentro));
    }

    public void addParametroToFase(int paramentro) {
        this.parametroFase.add("" + paramentro);
    }
}
