package br.udesc.ceavi.ppr.haruichiban.model;

import java.awt.Color;

/**
 *
 * @author Gustavo C Santos
 * @since 10/05/2019
 * 
 */

public class Ovo extends PecaTabuleiro{

    public Ovo(float rotacao, Color cor) {
        super(rotacao, cor);
    }

    @Override
    public TipoPeca getTipo() {
        return TipoPeca.OVO;
    }

}
