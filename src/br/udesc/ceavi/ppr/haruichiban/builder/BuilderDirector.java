package br.udesc.ceavi.ppr.haruichiban.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta Classe Monta Os Builder
 *
 * @author Gustavo C Santos
 */
public class BuilderDirector {

    private BoardBuilder builder;

    public BuilderDirector(BoardBuilder builder) {
        this.builder = builder;
    }

    public void contruir() {
        this.builder.reset();
        this.builder.constroiPartes();
    }
}
