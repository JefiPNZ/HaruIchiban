package br.udesc.ceavi.ppr.haruichiban.control;

import br.udesc.ceavi.ppr.haruichiban.command.DefineTitleCommand;
import br.udesc.ceavi.ppr.haruichiban.command.DifineTitleEmpateCommand;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarUntitledGardenerException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.SwingUtilities;

/**
 *
 * @author Gustavo C Santos
 * @since 12/05/2019
 *
 */
public class FluxoController implements IFluxoController {

    private GameController controlGame;
    private IPlayerController bottomPlayer;
    private IPlayerController topPlayer;
    private IBoardController controllerBoard;
    private final int JARDINEIROJUNIOR = 1;
    private final int JARDINEIROSENIOR = 2;
    private Map<Integer, IPlayerController> jardineiro = new HashMap<>();

    public FluxoController(GameController controlGame) {
        this.controlGame = controlGame;
        this.controllerBoard = controlGame.getBoardController();
        this.bottomPlayer = controlGame.getBottomPlayer();
        this.topPlayer = controlGame.getTopPlayer();
        this.bottomPlayer.setFluxoController(this);
        this.topPlayer.setFluxoController(this);
    }

    /**
     * Escolhe Aleatoriamete o Jogador Para Dar Inicio Ao Turno
     */
    @Override
    public void startGame() {
        jardineiro.clear();
        try {
            topPlayer.becomeUntitledGardener();
            bottomPlayer.becomeUntitledGardener();
        } catch (PlayNaoPodeSeTornarUntitledGardenerException ex) {
        }
        topPlayer.setFase(Fase.CHOISE_FLOWER_DECK);
        bottomPlayer.setFase(Fase.CHOISE_FLOWER_DECK);
        int vez = controlGame.getRandomizer().nextInt();
        if (vez % 2 == 0) {
            this.notificaMudancaEstado("Jogador inferior escolha uma flor.");
            chooseFlower(bottomPlayer);
        } else {
            this.notificaMudancaEstado("Jogador superior escolha uma flor.");
            chooseFlower(topPlayer);
        }
    }

    @Override
    public void chooseFlower() {
        if (bottomPlayer.getFase() == Fase.CHOISE_FLOWER_DECK) {
            this.notificaMudancaEstado("Jogador inferior escolha uma flor.");
            chooseFlower(bottomPlayer);
        } else if (topPlayer.getFase() == Fase.CHOISE_FLOWER_DECK) {
            this.notificaMudancaEstado("Jogador superior escolha uma flor.");
            chooseFlower(topPlayer);
        } else if (topPlayer.getFase() != Fase.CHOISE_FLOWER_DECK && bottomPlayer.getFase() != Fase.CHOISE_FLOWER_DECK) {
            this.notificaMudancaEstado("Etapa de seleção de flores finalizada.");
            defineTitles();
        }
    }

    private void chooseFlower(IPlayerController vez) {
        vez.choseFlowerDeck();
    }

    private void defineTitles() {
        this.notificaMudancaEstado("Definição de títulos:");

        if (bottomPlayer.getFlower().getValor() > topPlayer.getFlower().getValor()) {
            controlGame.executeCommand(
                    new DefineTitleCommand(jardineiro, topPlayer, bottomPlayer));
            defineTitlesEnd();

        } else if (bottomPlayer.getFlower().getValor() < topPlayer.getFlower().getValor()) {
            controlGame.executeCommand(
                    new DefineTitleCommand(jardineiro, bottomPlayer, topPlayer));
            defineTitlesEnd();

        } else if (bottomPlayer.getFlower().getValor() == topPlayer.getFlower().getValor()) {
            controlGame.executeCommand(
                    new DifineTitleEmpateCommand(topPlayer, bottomPlayer, this));
            return;
        }
    }

    private void defineTitlesEnd() {
        this.notificaMudancaEstado("Coloquem As Flores No Tabuleiro");
        putFlowerTable();
    }

    @Override
    public void putFlowerTable() {
        if (jardineiro.get(JARDINEIROJUNIOR).getFase() == Fase.PUT_FLOWER_TABLE) {
            jardineiro.get(JARDINEIROJUNIOR).putFlowerTable();
        } else if (jardineiro.get(JARDINEIROSENIOR).getFase() == Fase.PUT_FLOWER_TABLE) {
            jardineiro.get(JARDINEIROSENIOR).putFlowerTable();
        } else {
            firstWind();
        }
    }

    @Override
    public void firstWind() {
        if (jardineiro.get(JARDINEIROJUNIOR).getFase() == Fase.FRIST_WINT
                && jardineiro.get(JARDINEIROSENIOR).getFase() == Fase.FRIST_WINT) {
            this.notificaMudancaEstado("Chamando Primeiro Vento Da Primavera");
        }

        if (jardineiro.get(JARDINEIROJUNIOR).getFase() == Fase.FRIST_WINT) {
            jardineiro.get(JARDINEIROJUNIOR).fristWint();
        } else if (jardineiro.get(JARDINEIROSENIOR).getFase() == Fase.FRIST_WINT) {
            jardineiro.get(JARDINEIROSENIOR).fristWint();
        } else {
            newDarkLeaf();
        }
    }

    @Override
    public void newDarkLeaf() {
        System.out.println("Chamado");
        if (jardineiro.get(JARDINEIROJUNIOR).getFase() == Fase.NEW_DARK_LEAF
                && jardineiro.get(JARDINEIROSENIOR).getFase() == Fase.NEW_DARK_LEAF) {
            this.notificaMudancaEstado("Escolher Nova Folha Escura");
        }

        if (jardineiro.get(JARDINEIROJUNIOR).getFase() == Fase.NEW_DARK_LEAF) {
            jardineiro.get(JARDINEIROJUNIOR).newDarkLeaf();
        } else if (jardineiro.get(JARDINEIROSENIOR).getFase() == Fase.NEW_DARK_LEAF) {
            jardineiro.get(JARDINEIROSENIOR).newDarkLeaf();
        } else {
            getPlayerPoints();
        }
    }

    @Override
    public void getPlayerPoints() {
        this.notificaMudancaEstado("Verificando Pontuação");
        System.out.println("Pontos Inicio De Calculo");

//        controllerBoard.validaPontuacao();

        System.out.println("Pontos Fim  De Calculo");
        getPlayerPointsEnd();
    }

    @Override
    /**
     * {@inheritdoc}
     */
    public Fase chooseFlowerEnd() {
        return Fase.PUT_FLOWER_TABLE;
    }

    @Override
    /**
     * {@inheritdoc}
     */
    public Fase putFlowerTableEnd() {
        return Fase.FRIST_WINT;
    }

    @Override
    /**
     * {@inheritdoc}
     */
    public Fase firstWindEnd() {
        return Fase.NEW_DARK_LEAF;
    }

    @Override
    /**
     * {@inheritdoc}
     */
    public Fase newDarkLeafEnd() {
        return Fase.GET_PONTS;
    }

    public void getPlayerPointsEnd() {
        topPlayer.setFase(Fase.INICIO_TURNO);
        bottomPlayer.setFase(Fase.INICIO_TURNO);
        startGame();
    }

    public void notificaMudancaEstado(String mensagem) {
        GameController.getInstance().notificaMudancaEstado(mensagem);
    }

}
