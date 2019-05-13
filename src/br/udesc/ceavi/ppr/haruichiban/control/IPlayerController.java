package br.udesc.ceavi.ppr.haruichiban.control;

import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarJuniorException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarSeniorException;
import br.udesc.ceavi.ppr.haruichiban.model.Flor;
import br.udesc.ceavi.ppr.haruichiban.state.TitleOfGardener;
import java.util.List;

/**
 * Interface para classes que realizam o controle da mão do jogador.
 *
 * @author Jeferson Penz
 */
public interface IPlayerController {

    /**
     * Retorna o tamanho da pilha do jogador.
     *
     * @return
     */
    public int getPileSize();

    /**
     * Retorna a mão do usuário.
     *
     * @return
     */
    public List<Object> getHand();

    public void selecionarFlor(int x);

    public void addObserver(PlayerPanelObserver obs);
    
    public abstract void setControllerFluxo(IControleDeFluxo aThis);
    
    public abstract void requerirAoJogadorQueEsteEscolhaUmaFlor();
    
    public abstract Flor getFlorEmJogo();
    
    public abstract void hideHandValue();
    
    public abstract void becomeSeniorGardener() throws PlayNaoPodeSeTornarSeniorException;
    
    public abstract void becomeJuniorGardener() throws PlayNaoPodeSeTornarJuniorException;
    
    public abstract void devolverFlorAoDeck();
    
    public abstract void requerirQueOJogadorColoqueAFlorNoTabuleiro();
    
    public abstract void chamarOPrimeiroVentoDaPrimaveira();
    
    public abstract void escolhaANovaFolhaEscura();
    
    public abstract TitleOfGardener getTitle();

}
