package br.udesc.ceavi.ppr.haruichiban.view;

import br.udesc.ceavi.ppr.haruichiban.control.OponnetControllerProxy;
import br.udesc.ceavi.ppr.haruichiban.control.PlayerControllerProxy;
import br.udesc.ceavi.ppr.haruichiban.control.observers.GameStateObserverProxy;
import br.udesc.ceavi.ppr.haruichiban.model.GameConfig;
import br.udesc.ceavi.ppr.haruichiban.utils.Images;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.google.gson.Gson;
import java.awt.Dimension;

/**
 * JFrame Principal contendo a tela da Aplicação.
 *
 * @author Jeferson Penz
 */
public class MainFrame extends JFrame implements GameStateObserverProxy {

    private static final long serialVersionUID = 1L;
    /**
     * Painel para conter todo o jogo.
     */
    private JPanel gamePanel;
    /**
     * Painel para o jogador superior.
     */
    private PlayerPanel topPlayerPanel;
    /**
     * Painel para o jogador inferior.
     */
    private PlayerPanel bottomPlayerPanel;
    /**
     * Painel para o tabuleiro.
     */
    private JPanel boardPanel;
    /**
     * Painel para a pontuação.
     */
    private JPanel scorePanel;
    /**
     * Painel.para o menu.
     */
    private JPanel menuPanel;

    private PlayerControllerProxy player;
    private OponnetControllerProxy oponnet;

    private GameConfig gameConfig;

    /**
     * Cria o frame para conter o jogo.
     */
    public MainFrame() {
        super("Haru Ichiban");
    }

    public void begin(PlayerControllerProxy player) throws Exception {
        this.player = player;
        this.getGameConfig();

        this.oponnet = new OponnetControllerProxy(player.getSocket());
        this.oponnet.setCor(player.isTop() ? gameConfig.getColorBotton() : gameConfig.getColorTop());
        this.initializeGameComponents();
        this.initializeFrameProperties();
        this.setVisible(true);
    }

    private void getGameConfig() {
        player.sendRequest("E,GAMECONFIG");
        String ret = player.getIn().nextLine();
        System.out.println("Resposta: " + ret);
        this.gameConfig = (GameConfig) new Gson().fromJson(ret, GameConfig.class);
        if (gameConfig.getEstacao().equalsIgnoreCase("Inverno")) {
            Images.mapImagemInverno();
        } else {
            Images.mapImagemPrimaveira();
        }
    }

    /**
     * (Re)Inicializa as propriedades da tela atualizando esta de acordo.
     */
    public final void initializeFrameProperties() {
        this.setVisible(false);
        this.setMinimumSize(new Dimension(600, 400));
        this.setSize(800, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Inicializa os diferentes componentes do jogo.
     */
    private void initializeGameComponents() {
        this.gamePanel = new GamePanel();
        this.bottomPlayerPanel = new PlayerPanel(player.getColor(), player, gameConfig);
        this.topPlayerPanel = new PlayerPanel(oponnet.getColor(), oponnet, gameConfig);
        this.boardPanel = new BoardPanel();
        this.topPlayerPanel.setRotation(180);
        this.scorePanel = new ScorePanel();
        this.menuPanel = new MenuPanel();
        this.gamePanel.add(this.topPlayerPanel, BorderLayout.NORTH);
        this.gamePanel.add(this.bottomPlayerPanel, BorderLayout.SOUTH);
        this.gamePanel.add(this.boardPanel, BorderLayout.CENTER);
        this.gamePanel.add(this.scorePanel, BorderLayout.WEST);
        this.gamePanel.add(this.menuPanel, BorderLayout.EAST);
        this.setContentPane(this.gamePanel);
    }

    @Override
    public void notificaMudancaEstado(String mensagem) {
        this.repaint();
    }

    @Override
    public void notificaFimJogo(String mensagem) {
        if (mensagem != null && !mensagem.isEmpty()) {
            JOptionPane.showMessageDialog(this, mensagem);
        }
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    public void play() {
        while (player.getIn().hasNextLine()) {}
        player.sendRequest("END");
        // Finalizar Thread La no Servidor
    }

}
