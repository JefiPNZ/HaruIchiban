package br.udesc.ceavi.ppr.haruichiban.model;

import java.awt.Color;

/**
 *
 * @author GustavoSantos
 * @since 09/05/2019
 *
 */
public class Sapo extends PecaTabuleiro {

    private final Color cor;

    public Sapo(int x, int y, float rotacao, Color cor) {
        super(x, y, rotacao);
        this.cor = cor;
    }

    public Color getCor() {
        return cor;
    }

}
