package br.udesc.ceavi.ppr.haruichiban.control;

import br.udesc.ceavi.ppr.haruichiban.abstractfactory.FactoryPecas;
import br.udesc.ceavi.ppr.haruichiban.abstractfactory.FactoryPecasInverno;
import br.udesc.ceavi.ppr.haruichiban.abstractfactory.FactoryPecasPrimavera;
import br.udesc.ceavi.ppr.haruichiban.builder.BoardBuilder;
import br.udesc.ceavi.ppr.haruichiban.builder.BoardGigaBuilder;
import br.udesc.ceavi.ppr.haruichiban.builder.BoardNormalBuilder;
import br.udesc.ceavi.ppr.haruichiban.view.MenuPanel;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.SwingUtilities;

/**
 * Classe Principal para inicialização e controle do estado da Aplicação.
 *
 * @author Gustavo
 *
 * AVISO - NÃO USAR INSTANCEOF, COMPARAR USANDO GETCLASS() == .CLASS. - NÃO PODE
 * HAVER //JOptionPane NO CONTROLLER. - Quando adicionar um componente
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
    
    private List<GameStateObserver> gameStateObserver;

    /**
     * Representa o jogador da base da tela.
     */
    private PlayerController bottomPlayer;

    private FactoryPecas factoryPecas;

    private BoardController controllerBoard;

    private IFluxoController controlDeFluxo;

    /**
     * Classe para criação da instância do Singleton.
     */
    private GameController() {
        this.randomizer = new Random();
        this.fixedSeed = this.randomizer.nextLong();
        this.gameStateObserver = new ArrayList<>();
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
        if (instance == null) {
            instance = new GameController();
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
    public void begin(String varianteTabuleiro, String tamanhoTabuleiro, Color corJogadorTopo, Color corJogadorBase) {
        switch (varianteTabuleiro) {
            default:
            case "Primavera":
                this.factoryPecas = new FactoryPecasPrimavera();
                break;
            case "Inverno":
                this.factoryPecas = new FactoryPecasInverno();
                break;
        }
        int tamanhoDeck;
        switch (tamanhoTabuleiro) {
            default:
            case "Giga":
                this.builderTabuleiro = new BoardGigaBuilder();
                tamanhoDeck = 13;
                break;
            case "Normal":
                this.builderTabuleiro = new BoardNormalBuilder();
                tamanhoDeck = 9;
                break;
        }
        topPlayer = new PlayerController(corJogadorTopo, tamanhoDeck);
        bottomPlayer = new PlayerController(corJogadorBase, tamanhoDeck);
        this.controllerBoard = new BoardController();
        this.controlDeFluxo = new FluxoController(this);
    }

    /**
     * Para a execução da lógica do jogo.
     * @param mensagem
     */
    public void stop(String mensagem) {
        this.gameStateObserver.forEach((observer) -> {
            observer.notificaFimJogo(mensagem);
        });
        this.gameStateObserver = new ArrayList<>();
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
        controlDeFluxo.startGame();
    }

    public IFluxoController getControlDeFluxo() {
        return controlDeFluxo;
    }
    
    public void notificaMudancaEstado(String mensagem){
        this.gameStateObserver.forEach((observer) -> {
            observer.notificaMudancaEstado(mensagem);
        });
    }
    
    public void addGameStateObserver(GameStateObserver obs){
        this.gameStateObserver.add(obs);
    }
    
    public void removeGameStateObserver(GameStateObserver obs){
        this.gameStateObserver.remove(obs);
    }

}
