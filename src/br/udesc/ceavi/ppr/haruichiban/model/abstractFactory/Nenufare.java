package br.udesc.ceavi.ppr.haruichiban.model.abstractFactory;

import br.udesc.ceavi.ppr.haruichiban.exceptions.NenufareJaPossuiUmaPecaEmCimaException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.CanNotChangeSideNenufareException;
import br.udesc.ceavi.ppr.haruichiban.model.PecaTabuleiro;

/**
 *
 * @author GustavoSantos
 * @since 09/05/2019
 *
 */
public abstract class Nenufare extends PecaTabuleiro {

    protected boolean showDarkSide;
    protected PecaTabuleiro peca;

    public Nenufare(int x, int y, float rotacao) {
        super(x, y, rotacao);
    }

    public abstract boolean isShowYouDarkSide();

    public abstract boolean haveAnEgg();

    public abstract void changeSideNenufare() throws CanNotChangeSideNenufareException;

    @Override
    public void setPosicao(int x, int y) {
        super.setPosicao(x, y);
        if (peca != null) {
            peca.setPosicao(x, y);
        }
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

}
