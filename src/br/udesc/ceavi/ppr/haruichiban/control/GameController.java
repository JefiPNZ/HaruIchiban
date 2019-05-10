package br.udesc.ceavi.ppr.haruichiban.control;

import br.udesc.ceavi.ppr.haruichiban.abstractfactory.FactoryPecas;
import br.udesc.ceavi.ppr.haruichiban.abstractfactory.FactoryPecasInverno;
import br.udesc.ceavi.ppr.haruichiban.abstractfactory.FactoryPecasPrimavera;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        initFabricaPecas();
    }

    private void initFabricaPecas() throws Exception {
        //@todo criar a fábrica de peças com base em config
        Properties props = new Properties();
        props.load(new InputStreamReader(new FileInputStream(new File("conf.properties"))));
        String tabuleiro = props.getProperty("tabuleiro");
        switch (tabuleiro) {
            case "FactoryPecasPrimavera":
                this.factoryPecas = new FactoryPecasPrimavera();
                break;
            case "FactoryPecasInverno":
                this.factoryPecas = new FactoryPecasInverno();
                break;
            default:
                throw new Exception("Tabuleiro Na Indentificado");
        }
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
     */
    public void begin() {
        topPlayer = new PlayerController(new Color(255, 210, 65));
        bottomPlayer = new PlayerController(new Color(255, 15, 35));
        System.out.println(topPlayer.getPlay().toString());
        System.out.println(bottomPlayer.getPlay().toString());
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

}
