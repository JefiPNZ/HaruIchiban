package br.udesc.ceavi.ppr.haruichiban.model;

import br.udesc.ceavi.ppr.haruichiban.model.abstractFactory.*;
import br.udesc.ceavi.ppr.haruichiban.exceptions.NenufareJaPossuiUmaPecaEmCimaException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.CanNotChangeSideNenufareException;
import br.udesc.ceavi.ppr.haruichiban.model.PecaTabuleiro;
import java.awt.Color;

/**
 *
 * @author GustavoSantos
 * @since 09/05/2019
 *
 */
public class Nenufera extends PecaTabuleiro {

    protected boolean showDarkSide;
    protected PecaTabuleiro peca;

    public Nenufera(float rotacao) {
        super(rotacao, null);
    }

    public boolean isEscura(){
        return false;
    }

    public boolean hasOvo(){
        return false;
    }
    
    public boolean hasPeca(){
        return this.peca != null;
    }

    public void colocarPecaEmCimaDeMim(PecaTabuleiro peca) throws NenufareJaPossuiUmaPecaEmCimaException {
        if (peca != null) {
            throw new NenufareJaPossuiUmaPecaEmCimaException(peca.getClass().getName());
        }
        this.peca = peca;
    }

    public PecaTabuleiro removerPecaDeNenufare() {
        PecaTabuleiro pecaPegar = peca;
        peca = null;
        return pecaPegar;
    }

    public PecaTabuleiro getPeca() {
        return peca;
    }

    @Override
    public Color getCor() {
        return this.isEscura() ? new Color(10, 125, 10) : new Color(15, 205, 15);
    }

    @Override
    public TipoPeca getTipo() {
        return TipoPeca.NENUFERA;
    }

}
