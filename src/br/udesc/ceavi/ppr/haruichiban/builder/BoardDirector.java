package br.udesc.ceavi.ppr.haruichiban.builder;


/**
 * Esta Classe Monta Os Builder
 *
 * @author Gustavo C Santos
 */
public class BoardDirector {

    private BoardBuilder builder;

    public BoardDirector(BoardBuilder builder) {
        this.builder = builder;
    }

    public void contruir() {
        this.builder.reset();
        this.builder.constroiPartes();
    }
}
