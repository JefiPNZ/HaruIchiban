package br.udesc.ceavi.ppr.haruichiban.control;

import br.udesc.ceavi.ppr.haruichiban.control.observers.PlayerPanelObserver;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarJuniorException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarSeniorException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarUntitledGardenerException;
import br.udesc.ceavi.ppr.haruichiban.model.Flor;
import br.udesc.ceavi.ppr.haruichiban.state.TitleOfGardener;
import br.udesc.ceavi.ppr.haruichiban.state.UntitledGardener;
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

    public int getPlayerScore();

    public void choseFlowerDeck();

    public void devolverFlorAoDeck();

    public void choseFlowerDeckEnd(int x);

    public void addObserver(PlayerPanelObserver obs);

    public void setControllerFluxo(IFluxoController aThis);

    public void becomeSeniorGardener() throws PlayNaoPodeSeTornarSeniorException;

    public void becomeJuniorGardener() throws PlayNaoPodeSeTornarJuniorException;

    public void becomeUntitledGardener() throws PlayNaoPodeSeTornarUntitledGardenerException;

    public void requerirQueOJogadorColoqueAFlorNoTabuleiro();

    public void chamarOPrimeiroVentoDaPrimaveira();

    public void escolhaANovaFolhaEscura();

    public Flor getFlorEmJogo();

    public TitleOfGardener getTitle();

    public void notifySimples(String messagem);

    public void setTitle(TitleOfGardener untitledGardener);

    public void notifySemTitulo();

    public void notifyYouAJunior();

    public void notifyYouASenior();

}
