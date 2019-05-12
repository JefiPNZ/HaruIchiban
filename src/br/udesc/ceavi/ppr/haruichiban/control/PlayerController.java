package br.udesc.ceavi.ppr.haruichiban.control;

import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarJuniorException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarSeniorException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarUntitledGardenerException;
import br.udesc.ceavi.ppr.haruichiban.model.Flor;
import br.udesc.ceavi.ppr.haruichiban.model.ModelPlayer;
import br.udesc.ceavi.ppr.haruichiban.model.PecaTabuleiro;
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

    /**
     *
     * @param cor identifica a cor das flores do jogador
     */
    public PlayerController(Color cor) {
        this.title = new UntitledGardener();
        this.play = new ModelPlayer(cor);
        hideHandValue();
    }

    public void becomeUntitledGardener() throws PlayNaoPodeSeTornarUntitledGardenerException {
        title.becomeUntitledGardener(this);
    }

    public void becomeJuniorGardener() throws PlayNaoPodeSeTornarJuniorException {
        title.becomeJuniorGardener(this);
        System.out.println("Me Tornei Junior");
    }

    public void becomeSeniorGardener() throws PlayNaoPodeSeTornarSeniorException {
        title.becomeSeniorGardener(this);
        System.out.println("Me Tornei Senior");
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
    public List<Object> getHand() {
        if (esconderValoresDaMao) {
            return Arrays.asList(-1, -1, -1);
        }
        return play.getListaMao().stream().map(flor -> flor.getValor()).collect(Collectors.toList());
    }

    @Override
    public void selecionarFlor(int x) {
        System.out.println("Selecao Realizada");
        //Tira da mao do play a flor para coloca-la em jogo
        this.florEmJogo = play.getFlorFromHand(x);
        if (florEmJogo != null) {
            System.out.println("Selecionei");
            GameController.getInstance().selecaoDeFlorFinalizada();
        }else{
            System.err.println("Falha Na selecao da Flor");
            System.exit(0);
        }
        //Notificar A Area da Flor Escolhida Pelo Jogador

    }

    public Flor getFlorEmJogo() {
        return florEmJogo;
    }

    public Color getColor() {
        return play.getColor();
    }

    public void showHandValue() {
        esconderValoresDaMao = false;
        observers.forEach(obs -> obs.notificarRenderizeOsValoresDaMao());
    }

    public void hideHandValue() {
        esconderValoresDaMao = true;
        observers.forEach(obs -> obs.notificarRenderizeOsValoresDaMao());
    }

    public void requerirAoJogadorQueEsteEscolhaUmaFlor() {
        observers.forEach(obs -> {
            obs.notifyJogadorEscolhaUmaFlor();
        });
        showHandValue();
    }

    public void requerirAoJogadorQueEsteEscolhaUmFolhaParaSerVirada() throws Exception {

    }

    public void requerirAoJogadorQueEsteEscolhaUmFolhaParaSerMovida() throws Exception {

    }

    public void requerirAoJogadorQueEsteEscolhaUmFolhaParaColocarAFlor() throws Exception {

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

    public PecaTabuleiro removerFlorEmJogo() {
        Flor flor = florEmJogo;
        florEmJogo = null;
        return flor;
    }

    public void chamarOPrimeiroVentoDaPrimaveira() {
        try {
            title.chamarPrimeiroVentoDaPrimaveira(this);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    public void escolhaANovaFolhaEscura() {
        try {
            title.escolhaANovaFolhaEscura(this);
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
}
