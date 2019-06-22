package br.udesc.ceavi.ppr.haruichiban.cliente.control;

import br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces.IFluxoController;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces.IBoardController;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.channel.ModelGet;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.channel.CommunicationPackage;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.channel.PackageClientInput;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.channel.PackageClientOutput;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces.IEmitirSomController;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces.IPlayerController;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.observers.GameStateObserver;
import br.udesc.ceavi.ppr.haruichiban.cliente.utils.Images;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.awt.Color;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe Principal para inicialização e controle do estado da Aplicação.
 *
 * @author Jerfeson e Gustavo
 *
 * AVISO - NÃO USAR INSTANCEOF, COMPARAR USANDO GETCLASS() == .CLASS. - NÃO PODE
 * HAVER //JOptionPane NO CONTROLLER. - Quando adicionar um componente
 * transparente (como overlay por ex.), lembrar do setOpaque(false).
 */
public class GameClienteController {

    /**
     * Constante para o nome do jogo.
     */
    public static final String GAME_NAME = "Haru Ichiban - Cliente";

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
    private IPlayerController topPlayer;

    private List<GameStateObserver> gameStateObserver;

    /**
     * Representa o jogador da base da tela.
     */
    private IPlayerController bottomPlayer;

    private BoardController controllerBoard;

    private IFluxoController controlDeFluxo;

    private String estacao;

    private ExecutorService threadPool;

    /**
     * Classe do sistema de comunicacao
     */
    private PackageClientInput packageInput;
    private PackageClientOutput packageOutput;
    private boolean isTop;
    private IEmitirSomController emitirSomController;
    private boolean dadosCarregador;

    /**
     * Classe para criação da instância do Singleton.
     */
    private GameClienteController() {
        this.randomizer = new Random();
        this.fixedSeed = this.randomizer.nextLong();
        this.gameStateObserver = new ArrayList<>();
        this.threadPool = Executors.newCachedThreadPool();
    }

    /**
     * Instância de referência do Singleton.
     */
    private static GameClienteController instance;

    /**
     * Retorna a instância existente do Singleton.
     *
     * @return A instância existente ou uma nova instância do jogo.
     */
    public synchronized static GameClienteController getInstance() {
        if (instance == null) {
            instance = new GameClienteController();
        }
        return instance;
    }

    /**
     * Para a execução da lógica do jogo.
     */
    public void begin() {
        this.bottomPlayer = new OponentControllerProxy(Color.BLACK);
        this.topPlayer = new OponentControllerProxy(Color.BLACK);
        this.controllerBoard = new BoardController();
        this.controlDeFluxo = new FluxoController();
        this.emitirSomController = new EmitirSomController();
        openChannel();
    }

    public void setEmitirSomController(IEmitirSomController emitirSomController) {
        this.emitirSomController = emitirSomController;
    }

    protected void startEstacao() {
        switch (getEstacao()) {
            default:
            case "Primavera":
                Images.mapImagemPrimaveira();
                break;
            case "Inverno":
                Images.mapImagemInverno();
                break;
        }
    }

    public IPlayerController getPlayer() {
        return topPlayer.getClass().getSimpleName().equals(PlayerController.class.getSimpleName()) ? topPlayer : bottomPlayer;
    }

    public IPlayerController getOponnet() {
        return topPlayer.getClass().getSimpleName().equals(OponentControllerProxy.class.getSimpleName()) ? topPlayer : bottomPlayer;
    }

    /**
     * Para a execução da lógica do jogo.
     *
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

    public IPlayerController getBottomPlayer() {
        return bottomPlayer;
    }

    public IPlayerController getTopPlayer() {
        return topPlayer;
    }

    public IBoardController getBoardController() {
        return controllerBoard;
    }

    public void startGame() {
        controlDeFluxo.startGame();
    }

    public IFluxoController getFluxoController() {
        return controlDeFluxo;
    }

    public void notificaMudancaEstado(String mensagem) {
        this.gameStateObserver.forEach((observer) -> {
            observer.notificaMudancaEstado(mensagem);
        });
    }

    public void addGameStateObserver(GameStateObserver obs) {
        this.gameStateObserver.add(obs);
    }

    public void removeGameStateObserver(GameStateObserver obs) {
        this.gameStateObserver.remove(obs);
    }

    public String getEstacao() {
        return estacao;
    }

    private void openChannel() {
        try {
            Socket socket = new Socket("127.0.0.1", 60000);
            PlayerController player = new PlayerController(socket);
            Scanner scanner = new Scanner(socket.getInputStream());
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            CommunicationPackage communicationPackage = new CommunicationPackage();

            communicationPackage.addGet(ModelGet.MY_POSITION, "");
            printWriter.println(communicationPackage.toJson());
            printWriter.flush();

            communicationPackage = new Gson().fromJson(scanner.nextLine(), CommunicationPackage.class);

            if (communicationPackage.getParametro().toUpperCase().contains("TOP")) {
                topPlayer = player;
                player.setTop(true);
            } else {
                bottomPlayer = player;
                player.setTop(false);
            }
            packageInput = new PackageClientInput(scanner);
            packageOutput = new PackageClientOutput(printWriter);

            new Thread(packageInput, "Thread Input Cliente").start();
            new Thread(packageOutput, "Thread Output Cliente").start();
            carregandoDados();

        } catch (IOException ex) {
            Logger.getLogger(GameClienteController.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }

    private void carregandoDados() throws JsonSyntaxException, NumberFormatException {
        packageOutput.newGet(ModelGet.MY_PILESIZE, "");
        packageOutput.newGet(ModelGet.MY_COLOR, "");
        packageOutput.newGet(ModelGet.MY_HAND, "");

        packageOutput.newGet(ModelGet.OPONNET_COLOR, "");
        packageOutput.newGet(ModelGet.OPONNET_HAND, "");
        packageOutput.newGet(ModelGet.OPONNET_PILESIZE, "");

        packageOutput.newGet(ModelGet.GAME_BOARD, "");
        packageOutput.newGet(ModelGet.GAME_ESTACAO, "");
    }

    public PackageClientOutput getPackageOutput() {
        return packageOutput;
    }

    public void initBoard() {
        this.controllerBoard = new BoardController();
    }

    public void threadPoolExecute(Runnable command) {
        threadPool.execute(command);
    }

    public String getHoraSimples() {
        LocalDateTime now = LocalDateTime.now();
        return (now.getHour() < 10 ? "0" + now.getHour() : now.getHour()) + " - " + now.getMinute() + " - " + (now.getSecond() < 10 ? "0" + now.getSecond() : now.getSecond());
    }

    public IEmitirSomController getEmitirSomController() {
        return emitirSomController;
    }

    public void vencedor(int pontosDoVencedor, int pontosDoPerdedor) {
        gameStateObserver.forEach(obs -> obs.notifyVencedor(pontosDoVencedor, pontosDoPerdedor));
    }

    public void perderdor(int pontosDoVencedor, int pontosDoPerdedor) {
        gameStateObserver.forEach(obs -> obs.notifyPerdedor(pontosDoVencedor, pontosDoPerdedor));
    }

    public PackageClientInput getPackageInput() {
        return packageInput;
    }

    public void setEstacao(String estacao) {
        this.estacao = estacao;
        startEstacao();
        this.dadosCarregador = true;
    }

    public boolean isDadosCarregados() {
        return dadosCarregador;
    }

}
