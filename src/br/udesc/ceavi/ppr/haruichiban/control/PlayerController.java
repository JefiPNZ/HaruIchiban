package br.udesc.ceavi.ppr.haruichiban.control;

import br.udesc.ceavi.ppr.haruichiban.command.ChooseFlowerPlayer;
import br.udesc.ceavi.ppr.haruichiban.control.observers.PlayerPanelObserver;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarJuniorException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarSeniorException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarUntitledGardenerException;
import br.udesc.ceavi.ppr.haruichiban.model.flores.Flor;
import br.udesc.ceavi.ppr.haruichiban.model.ModelPlayer;
import br.udesc.ceavi.ppr.haruichiban.state.TitleOfGardener;
import br.udesc.ceavi.ppr.haruichiban.state.UntitledGardener;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador de Player, servirar para controlar as acoes do player no jogo
 *
 * @author Gustavo C Santos
 * @since 08/05/2019
 *
 */
public class PlayerController implements IPlayerController {

    /**
     * Representa o jogador.
     */
    private ModelPlayer player;

    /**
     * Padrao State para guiar as açoes do player perante o estado dele
     */
    private TitleOfGardener title;

    /**
     * Este guarda a flor do turno
     */
    private Flor florEmJogo;

    private List<PlayerPanelObserver> observers = new ArrayList<>();

    private IFluxoController fluxoController;

    private Fase fase;

    /**
     *
     * @param cor identifica a cor das flores do jogador
     * @param tamanhoDoDeck tamano do deck
     */
    public PlayerController(Color cor, int tamanhoDoDeck) {
        this.title = new UntitledGardener();
        this.player = new ModelPlayer(cor, tamanhoDoDeck);
    }

    @Override
    public void becomeUntitledGardener() throws PlayNaoPodeSeTornarUntitledGardenerException {
        title.becomeUntitledGardener(this);
    }

    @Override
    public void becomeJuniorGardener() throws PlayNaoPodeSeTornarJuniorException {
        title.becomeJuniorGardener(this);
    }

    @Override
    public void becomeSeniorGardener() throws PlayNaoPodeSeTornarSeniorException {
        title.becomeSeniorGardener(this);
    }

    @Override
    public void setTitle(TitleOfGardener title) {
        this.title = title;
    }

    public void addPontos(int pontos) {
        player.addPontos(pontos);
    }

    public ModelPlayer getPlayer() {
        return player;
    }

    @Override
    public int getPileSize() {
        return player.getListaDeFlores().size();
    }

    public Color getColor() {
        return player.getColor();
    }

    @Override
    public synchronized List<Object> getHand() {
        return player.getListaMao().stream().map(flor -> flor.getValor()).collect(Collectors.toList());
    }

    @Override
    public void choseFlowerDeck() {
        observers.forEach(obs -> obs.notifyJogadorEscolhaUmaFlor());
    }

    @Override
    public void chooseFlowerDeckEnd(int x) {
        GameController.getInstance().executeCommand(
                new ChooseFlowerPlayer(this, x));
        fase = fluxoController.chooseFlowerEnd();
        fluxoController.chooseFlower();
        notifiyFlowerChoise();
    }

    @Override
    public Flor getFlower() {
        return florEmJogo;
    }

    @Override
    public void getFlorFromHand(int x) {
        florEmJogo = player.getFlorFromHand(x);
    }

    @Override
    public Flor removeFlower() {
        Flor flor = florEmJogo;
        florEmJogo = null;
        return flor;
    }

    @Override
    public void putFlowerTable() {
        title.putFlowerTable(this);
    }

    @Override
    public void fristWint() {
        title.firstWind(this);
    }

    @Override
    public void newDarkLeaf() {
        title.newDarkLeaf(this);
    }

    @Override
    public TitleOfGardener getTitle() {
        return title;
    }

    @Override
    public void devolverFlorAoDeck() {
        player.devolverFlor(removeFlower());
    }

    @Override
    public void setFluxoController(IFluxoController aThis) {
        this.fluxoController = aThis;
    }

    public IFluxoController getFluxoController() {
        return fluxoController;
    }

    @Override
    public int getPlayerScore() {
        return this.player.getPoints();
    }

    @Override
    public void addObserver(PlayerPanelObserver obs) {
        this.observers.add(obs);
    }

    @Override
    public void notifySemTitulo() {
        observers.forEach(obs -> obs.notifyYouAreSemTitulo());
    }

    @Override
    public void notifyYouAJunior() {
        observers.forEach(obs -> obs.notifyYouAreJunior());
    }

    @Override
    public void notifyYouASenior() {
        observers.forEach(obs -> obs.notifyYouAreSenior());
    }

    @Override
    public void notifySimples(String messagem) {
        observers.forEach(obs -> obs.notifySimpleMessager(messagem));
    }

    private void notifiyFlowerChoise() {
        observers.forEach(obs -> obs.notifyJogadorEscolhaUmaFlorEnd());
    }

    @Override
    public void setFase(Fase fase) {
        this.fase = fase;
    }

    @Override
    public Fase getFase() {
        return fase;
    }

    @Override
    public boolean haveFlowers() {
        return player.haveFlowers();
    }

}
