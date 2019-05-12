package br.udesc.ceavi.ppr.haruichiban.control;

import br.udesc.ceavi.ppr.haruichiban.abstractfactory.FactoryPecas;
import br.udesc.ceavi.ppr.haruichiban.abstractfactory.FactoryPecasInverno;
import br.udesc.ceavi.ppr.haruichiban.abstractfactory.FactoryPecasPrimavera;
import br.udesc.ceavi.ppr.haruichiban.builder.BoardBuilder;
import br.udesc.ceavi.ppr.haruichiban.builder.BoardGigaBuilder;
import br.udesc.ceavi.ppr.haruichiban.builder.BoardNormalBuilder;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarJuniorException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarSeniorException;
import br.udesc.ceavi.ppr.haruichiban.model.ModelPlayer;
import br.udesc.ceavi.ppr.haruichiban.state.SeniorGardener;
import java.awt.Color;
import java.awt.Point;
import java.util.Random;

/**
 * Classe Principal para inicialização e controle do estado da Aplicação.
 *
 * @author Jeferson Penz
 *
 * AVISO - NÃO USAR INSTANCEOF, COMPARAR USANDO GETCLASS() == .CLASS. - NÃO PODE
 * HAVER JOPTIONPANE NO CONTROLLER. - Quando adicionar um componente
 * transparente (como overlay por ex.), lembrar do setOpaque(false).
 */
public class GameController {

    /**
     * Constante para o nome do jogo.
     */
    public static final String GAME_NAME = "Haru Ichiban";

    /**
     * Responsável por gerar dados aleatórios.
     */
    private Random randomizer;

    /**
     * Semente fixa para geração estática de dados aleatórios.
     */
    private long fixedSeed;

    private BoardBuilder builderTabuleiro;

    /**
     * Representa o jogador do topo da tela.
     */
    private PlayerController topPlayer;

    /**
     * Representa o jogador da base da tela.
     */
    private PlayerController bottomPlayer;

    /**
     * Situação do jogo (se já foi ou não inicializado).
     */
    private boolean gameStarted;

    private FactoryPecas factoryPecas;

    private BoardController controllerBoard;

    /**
     * Classe para criação da instância do Singleton.
     */
    private GameController() throws Exception {
        this.gameStarted = false;
        this.randomizer = new Random();
        this.fixedSeed = this.randomizer.nextLong();
    }

    /**
     * Instância de referência do Singleton.
     */
    private static GameController instance;

    /**
     * Retorna a instância existente do Singleton.
     *
     * @return A instância existente ou uma nova instância do jogo.
     */
    public synchronized static GameController getInstance() {
        try {
            if (instance == null) {
                instance = new GameController();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }
        return instance;
    }

    /**
     * Para a execução da lógica do jogo.
     *
     * @param varianteTabuleiro
     * @param tamanhoTabuleiro
     * @param corJogadorTopo
     * @param corJogadorBase
     */
    public void begin(String varianteTabuleiro,
            String tamanhoTabuleiro, Color corJogadorTopo, Color corJogadorBase) {
        switch (varianteTabuleiro) {
            default:
            case "Primavera":
                this.factoryPecas = new FactoryPecasPrimavera();
                break;
            case "Inverno":
                this.factoryPecas = new FactoryPecasInverno();
                break;
        }
        switch (tamanhoTabuleiro) {
            default:
            case "Giga":
                this.builderTabuleiro = new BoardGigaBuilder();
                break;
            case "Normal":
                this.builderTabuleiro = new BoardNormalBuilder();
                break;
        }

        this.topPlayer = new PlayerController(corJogadorTopo);
        this.bottomPlayer = new PlayerController(corJogadorBase);
        this.controllerBoard = new BoardController();
    }

    /**
     * Para a execução da lógica do jogo.
     */
    public void stop() {
        this.gameStarted = false;
    }

    /**
     * Retorna se o jogo está rodando.
     *
     * @return <b>true</b> se o jogo já foi iniciado, senão <b>false</b>
     */
    public boolean isStarted() {
        return this.gameStarted;
    }

    /**
     * Retorna o gerador de dados aleatórios.
     *
     * @return
     */
    public Random getRandomizer() {
        return randomizer;
    }

    /**
     * Retorna o gerador de dados aleatórios fixo. Este gerador apresenta sempre
     * os mesmo valores quando é criado.
     *
     * @return
     */
    public Random getFixedRandomizer() {
        return new Random(this.fixedSeed);
    }

    public PlayerController getBottomPlayer() {
        return bottomPlayer;
    }

    public PlayerController getTopPlayer() {
        return topPlayer;
    }

    public FactoryPecas getFactoryPecas() {
        return factoryPecas;
    }

    public BoardBuilder getBuilder() {
        return builderTabuleiro;
    }

    public IBoardController getBoardeController() {
        return controllerBoard;
    }

    public void startGame() {
        System.out.println("Chamada de StartGame");
        int vez = randomizer.nextInt();
        if (vez % 2 == 0) {
            inicioDeTurno(bottomPlayer);
        } else {
            inicioDeTurno(topPlayer);
        }
    }

    private void inicioDeTurno(PlayerController primeiro) {
        System.out.println("inicioDeTurno");
        primeiro.requerirAoJogadorQueEsteEscolhaUmaFlor();
    }

    public void selecaoDeFlorFinalizada() {
        System.out.println("selecaoDeFlorFinalizada");
        topPlayer.hideHandValue();
        bottomPlayer.hideHandValue();
        if (bottomPlayer.getFlorEmJogo() == null) {
            bottomPlayer.requerirAoJogadorQueEsteEscolhaUmaFlor();
        } else if (topPlayer.getFlorEmJogo() == null) {
            topPlayer.requerirAoJogadorQueEsteEscolhaUmaFlor();
        } else {
            topPlayer.hideHandValue();
            bottomPlayer.hideHandValue();
            definirTitulos();
        }
    }

    private void definirTitulos() {
        System.out.println("definirTitulos iniciado");
        if (bottomPlayer.getFlorEmJogo().getValor() > topPlayer.getFlorEmJogo().getValor()) {
            try {
                bottomPlayer.becomeSeniorGardener();
                topPlayer.becomeJuniorGardener();
                colocarFlorNoTabuleiro();
            } catch (PlayNaoPodeSeTornarSeniorException | PlayNaoPodeSeTornarJuniorException ex) {
                ex.printStackTrace();
                System.exit(0);
            }
        }
        System.out.println("definirTitulos Finalizado");
    }

    private void colocarFlorNoTabuleiro() {
        System.out.println("colocarFlorNoTabuleiro iniciado");
        bottomPlayer.requerirQueOJogadorColoqueAFlorNoTabuleiro();
        topPlayer.requerirQueOJogadorColoqueAFlorNoTabuleiro();
    }

    public void florColocadaNoTabuleiro() {
        System.out.println("Check florColocadaNoTabuleiro");
        if (bottomPlayer.getFlorEmJogo() == null && topPlayer.getFlorEmJogo() == null) {
            bottomPlayer.chamarOPrimeiroVentoDaPrimaveira();
            topPlayer.chamarOPrimeiroVentoDaPrimaveira();
            System.out.println(" florColocadaNoTabuleiro finalizado");
        }
    }

    public void tabuleirMovimentado() {
        bottomPlayer.escolhaANovaFolhaEscura();
        topPlayer.escolhaANovaFolhaEscura();
    }

    public void folhaVirada(Point newFolhaEscura) {
        this.controllerBoard.setFolhaEscura(newFolhaEscura);
        verificarPontuacao();
    }

    private void verificarPontuacao() {
        controllerBoard.verificarPontuacaoDosJogadores();
        if (controllerBoard.hasWinner()) {
            ModelPlayer winner = controllerBoard.getWinner();
            if (bottomPlayer.getPlay().equals(winner)) {
                bottomPlayer.notifyYouWon();
            } else if (topPlayer.getPlay().equals(winner)) {
                topPlayer.notifyYouWon();
            }
        } else {
            novoTurno();
        }
    }

    private void novoTurno() {
        if (topPlayer.getTitle().getClass().getSimpleName() == SeniorGardener.class.getSimpleName()) {
            inicioDeTurno(topPlayer);
        } else {
            inicioDeTurno(bottomPlayer);
        }
    }

}
