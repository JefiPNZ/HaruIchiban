package br.udesc.ceavi.ppr.haruichiban.model.folha;

import br.udesc.ceavi.ppr.haruichiban.exceptions.FolhaJaPossuiUmaPecaEmCimaException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.CanNotChangeSideNenufareException;
import br.udesc.ceavi.ppr.haruichiban.model.filhote.Filhote;
import br.udesc.ceavi.ppr.haruichiban.model.PecaTabuleiro;
import br.udesc.ceavi.ppr.haruichiban.model.TipoPeca;
import java.awt.Color;

/**
 *
 * @author GustavoSantos
 * @since 09/05/2019
 *
 */
public abstract class Folha extends PecaTabuleiro {

    protected boolean isShowDarkSide;
    protected PecaTabuleiro peca;
    protected Filhote filhote;

    public Folha(float rotacao) {
        super(rotacao, null);
        this.isShowDarkSide = false;
    }

    /**
     * Retorna se a folha esta mostrando seu lado escuro
     *
     * @return true esta mostrando,false se não esta mostrando
     */
    public boolean isEscura() {
        return isShowDarkSide;
    }

    /**
     * Retorna se tem um filhote
     *
     * @return true se há um filhote,false se não há
     */
    public boolean hasFilhote() {
        return filhote != null;
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
    public void virarFolha() throws CanNotChangeSideNenufareException {
        if (isShowDarkSide) {
            throw new CanNotChangeSideNenufareException();
        }
        isShowDarkSide = true;
    }

    /**
     * Este metodo cria uma associação de nenufare com a peca, lanca
     * NenufareJaPossuiUmaPecaEmCimaException quando nenufar ja esta associado
     * com outra peca
     *
     * @param peca
     * @throws FolhaJaPossuiUmaPecaEmCimaException
     */
    public void colocarPecaNaFolha(PecaTabuleiro peca) throws FolhaJaPossuiUmaPecaEmCimaException {
        if (hasPeca()) {
            throw new FolhaJaPossuiUmaPecaEmCimaException(peca.getClass().getName());
        }
        this.peca = peca;
    }
    
    public void colocarFilhoteNaFolha(Filhote filhote) throws FolhaJaPossuiUmaPecaEmCimaException {
        if (hasFilhote()) {
            throw new FolhaJaPossuiUmaPecaEmCimaException(peca.getClass().getName());
        }
        this.filhote = filhote;
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
        return TipoPeca.FOLHA;
    }

    public Filhote getFilhote() {
        return filhote;
    }

    /**
     * Retorna se tem Um Animal
     *
     * @return true se tem um Animal,false se nao
     */
    public boolean hasAnimal() {
        return hasPeca() && peca.getTipo() == TipoPeca.ANIMAL;
    }

}
