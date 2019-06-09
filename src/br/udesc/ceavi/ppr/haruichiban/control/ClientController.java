package br.udesc.ceavi.ppr.haruichiban.control;

import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

import br.udesc.ceavi.ppr.haruichiban.control.observers.GameStateObserverProxy;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Principal para inicializasao e controle do estado da Aplicacao.
 *
 * @author Jeferson e Gustavo
 *
 * AVISO - N�O USAR INSTANCEOF, COMPARAR USANDO GETCLASS() == .CLASS. - N�O PODE
 * HAVER //JOptionPane NO CONTROLLER. - Quando adicionar um componente
 * transparente (como overlay por ex.), lembrar do setOpaque(false).
 */
public class ClientController {

    /**
     * Instancia de referencia do Singleton.
     */
    private static ClientController instance;

    /**
     * Retorna a instancia existente do Singleton.
     *
     * @return A instancia existente ou uma nova instencia do jogo.
     */
    public synchronized static ClientController getInstance() {
        if (instance == null) {
            instance = new ClientController();
        }
        return instance;
    }

    /**
     * Constante para o nome do jogo.
     */
    public static final String GAME_NAME = "Haru Ichiban";

    /**
     * Responsavel por gerar dados aleatorios.
     */
    private Random randomizer;

    /**
     * Semente fixa para geracao estatica de dados aleatarios.
     */
    private long fixedSeed;

    /**
     * Classe de requisicoes
     */
    private RequestSocket request;


    private List<GameStateObserverProxy> observers;

    /**
     * Classe para criação da instância do Singleton.
     *
     * @throws Exception
     */
    private ClientController() {
        this.observers = new ArrayList<>();
        this.randomizer = new Random();
        this.fixedSeed = this.randomizer.nextLong();
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

    public void initRequestSocket(Scanner in, PrintWriter out) {
        this.request = new RequestSocket(in, out);
    }

    public RequestSocket getCanal() {
        return request;
    }

    public void addObserver(GameStateObserverProxy obs) {
        this.observers.add(obs);
    }


    public void play() {
    }
}
