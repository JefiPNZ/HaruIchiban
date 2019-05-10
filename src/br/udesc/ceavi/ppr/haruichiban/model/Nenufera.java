package br.udesc.ceavi.ppr.haruichiban.model;

import br.udesc.ceavi.ppr.haruichiban.exceptions.NenufareJaPossuiUmaPecaEmCimaException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.CanNotChangeSideNenufareException;
import java.awt.Color;

/**
 *
 * @author GustavoSantos
 * @since 09/05/2019
 *
 */
public class Nenufera extends PecaTabuleiro {

    private boolean showDarkSide;
    private PecaTabuleiro peca;
    private Ovo ovo;

    public Nenufera(float rotacao) {
        super(rotacao, null);
        this.showDarkSide = false;
    }

    public void setOvo(Ovo ovo) {
        this.ovo = ovo;
    }

    /**
     * Retorna a nenufera esta mostrando seu lado escuro
     *
     * @return true esta mostrando,false se não esta mostrando
     */
    public boolean isEscura() {
        return showDarkSide;
    }

    /**
     * Retorna se tem um ovo
     *
     * @return true se há um ovo,false se não há
     */
    public boolean hasOvo() {
        return ovo != null;
    }

    /**
     * Retorna se tem um Peca
     *
     * @return true se há um Peca,false se não há
     */
    public boolean hasPeca() {
        return this.peca != null;
    }

    /**
     * Este metodo vira a Nenufare para essa mostar seu lado escuro, lança
     * CanNotChangeSideNenufareException quando o lado escuro ja esta sendo
     * mostardo
     *
     * @throws CanNotChangeSideNenufareException
     */
    public void virarNenufare() throws CanNotChangeSideNenufareException {
        if (showDarkSide) {
            throw new CanNotChangeSideNenufareException();
        }
        showDarkSide = true;
    }

    /**
     * Este metodo cria uma associação de nenufare com a peca, lanca
     * NenufareJaPossuiUmaPecaEmCimaException quando nenufar ja esta associado
     * com outra peca
     *
     * @param peca
     * @throws NenufareJaPossuiUmaPecaEmCimaException
     */
    public void colocarPecaEmNenufare(PecaTabuleiro peca) throws NenufareJaPossuiUmaPecaEmCimaException {
        if (hasPeca()) {
            throw new NenufareJaPossuiUmaPecaEmCimaException(peca.getClass().getName());
        }
        this.peca = peca;
    }

    /**
     * Este metodo acaba com a associação de nenufare com a peca
     *
     * @return retorna peca removida
     */
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

    public Ovo getOvo() {
        return ovo;
    }

    /**
     * Retorna se tem Um Sapo
     *
     * @return true se tem um sapo,false se nao
     */
    public boolean hasSapo() {
        return hasPeca() && peca.getTipo() == TipoPeca.SAPO;
    }

}
