package br.udesc.ceavi.ppr.haruichiban.model;

import java.awt.Color;

/**
 *
 * @author GustavoSantos
 * @since 09/05/2019
 *
 */
public class Sapo extends PecaTabuleiro {

    public Sapo(float rotacao, Color cor) {
        super(rotacao, cor);
    }

    @Override
    public TipoPeca getTipo() {
        return TipoPeca.SAPO;
    }

}