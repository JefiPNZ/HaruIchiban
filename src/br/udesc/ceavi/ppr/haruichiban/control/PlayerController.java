package br.udesc.ceavi.ppr.haruichiban.control;

import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarJuniorException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarSeniorException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarUntitledGardenerException;
import br.udesc.ceavi.ppr.haruichiban.model.Flor;
import br.udesc.ceavi.ppr.haruichiban.model.ModelPlayer;
import br.udesc.ceavi.ppr.haruichiban.state.TitleOfGardener;
import br.udesc.ceavi.ppr.haruichiban.state.UntitledGardener;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador de Player, servirar para controlar as acoes do play no jogo
 *
 * @author Gustavo C Santos
 * @since 08/05/2019
 *
 */
public class PlayerController implements IPlayerController {

    /**
     * Representa o jogador.
     */
    private ModelPlayer play;

    /**
     * Padrao State para guiar as açoes do play perante o estado dele
     */
    private TitleOfGardener title;

    /**
     * Este guarda a flor do turno
     */
    private Flor florEmJogo;

    private List<PlayerPanelObserver> observers = new ArrayList<>();

    private boolean esconderValoresDaMao;
    private IControleDeFluxo controllerFluxo;

    /**
     *
     * @param cor identifica a cor das flores do jogador
     */
    public PlayerController(Color cor, int tamanhoDoDeck) {
        this.title = new UntitledGardener();
        this.play = new ModelPlayer(cor, tamanhoDoDeck);
        hideHandValue();
    }

    public void becomeUntitledGardener() throws PlayNaoPodeSeTornarUntitledGardenerException {
        title.becomeUntitledGardener(this);
    }

    public void becomeJuniorGardener() throws PlayNaoPodeSeTornarJuniorException {
        title.becomeJuniorGardener(this);
    }

    public void becomeSeniorGardener() throws PlayNaoPodeSeTornarSeniorException {
        title.becomeSeniorGardener(this);
    }

    public void setTitle(TitleOfGardener title) {
        this.title = title;
    }

    public void addPontos(int pontos) {
        play.addPontos(pontos);
    }

    public ModelPlayer getPlay() {
        return play;
    }

    @Override
    public int getPileSize() {
        return play.getListaDeFlores().size();
    }

    @Override
    public synchronized List<Object> getHand() {
        if (esconderValoresDaMao) {
            return Arrays.asList(-1, -1, -1);
        }
        return play.getListaMao().stream().map(flor -> flor.getValor()).collect(Collectors.toList());
    }

    @Override
    public void selecionarFlor(int x) {
        this.florEmJogo = play.getFlorFromHand(x);
        controllerFluxo.selecaoDeFlorFinalizada();
        observers.forEach(obs -> obs.repintarPlayerHandTable());
    }

    public Flor getFlorEmJogo() {
        return florEmJogo;
    }

    public Color getColor() {
        return play.getColor();
    }

    public synchronized void showHandValue() {
        esconderValoresDaMao = false;
        observers.forEach(obs -> obs.repintarPlayerHandTable());
    }

    public synchronized void hideHandValue() {
        esconderValoresDaMao = true;
        observers.forEach(obs -> obs.repintarPlayerHandTable());
    }

    public void requerirAoJogadorQueEsteEscolhaUmaFlor() {
        showHandValue();
        observers.forEach(obs -> obs.notifyJogadorEscolhaUmaFlor());
    }

    @Override
    public void addObserver(PlayerPanelObserver obs) {
        this.observers.add(obs);
    }

    public void requerirQueOJogadorColoqueAFlorNoTabuleiro() {
        try {
            title.getFolhaNoTabuleiroParaFlor(this);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    public Flor removerFlorEmJogo() {
        Flor flor = florEmJogo;
        System.out.println("removerFlorEmJogo " + flor);
        florEmJogo = null;
        return flor;
    }

    public void chamarOPrimeiroVentoDaPrimaveira() {
        try {
            title.chamarPrimeiroVentoDaPrimaveiraGetPosicoes(this);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    public void escolhaANovaFolhaEscura() {
        try {
            title.escolhaANovaFolhaEscuraGetPosicoes(this);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    public void notifyYouWon() {
        System.out.println("Ganhei Porra");
    }

    public TitleOfGardener getTitle() {
        return title;
    }

    public void notifyYouAJunior() {
        observers.forEach(obs -> obs.notifyYouAreJunior());
    }

    public void notifyYouASenior() {
        observers.forEach(obs -> obs.notifyYouAreSenior());
    }

    public void notifySemTitulo() {
        observers.forEach(obs -> obs.notifyYouAreSemTitulo());
    }

    public void devouverFlorAoDech() {
        play.devolverFlor(removerFlorEmJogo());
    }

    public void setContollerFluxo(IControleDeFluxo aThis) {
        this.controllerFluxo = aThis;
    }

    public IControleDeFluxo getControllerFluxo() {
        return controllerFluxo;
    }
}
