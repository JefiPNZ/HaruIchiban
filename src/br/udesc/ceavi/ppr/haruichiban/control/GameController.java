package br.udesc.ceavi.ppr.haruichiban.control;

import br.udesc.ceavi.ppr.haruichiban.abstractfactory.FactoryPecas;
import br.udesc.ceavi.ppr.haruichiban.abstractfactory.FactoryPecasInverno;
import br.udesc.ceavi.ppr.haruichiban.abstractfactory.FactoryPecasPrimavera;
import br.udesc.ceavi.ppr.haruichiban.builder.BoardBuilder;
import br.udesc.ceavi.ppr.haruichiban.builder.BoardGigaBuilder;
import br.udesc.ceavi.ppr.haruichiban.builder.BoardNormalBuilder;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Random;

/**
 * Classe Principal para inicialização e controle do estado da Aplicação.
 *
 * @author Jeferson Penz
 *
 * AVISO - NÃO USAR INSTANCEOF, COMPARAR USANDO GETCLASS() == .CLASS. - NÃO PODE HAVER JOPTIONPANE NO CONTROLLER. -
 * Quando adicionar um componente transparente (como overlay por ex.), lembrar do setOpaque(false).
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
        this.gameStarted = true;
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
     * Retorna o gerador de dados aleatórios fixo. Este gerador apresenta sempre os mesmo valores quando é criado.
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

}
