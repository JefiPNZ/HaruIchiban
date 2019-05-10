package br.udesc.ceavi.ppr.haruichiban.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta Classe Monta Os Builder
 *
 * @author Gustavo C Santos
 */
public class Director {

    private final List<IBuilder> listBuider;

    public Director() {
        this.listBuider = new ArrayList<>();
    }

    public void contruir() {
        listBuider.forEach(builder -> {
            builder.reset();
            builder.constroiPartes();
        });
    }

    public void addBuilder(IBuilder builder){
        this.listBuider.add(builder);
    }
}
