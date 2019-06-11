package br.udesc.ceavi.ppr.haruichiban.view;

import br.udesc.ceavi.ppr.haruichiban.control.ClientController;
import br.udesc.ceavi.ppr.haruichiban.control.OponnetControllerProxy;
import br.udesc.ceavi.ppr.haruichiban.control.PlayerControllerProxy;
import br.udesc.ceavi.ppr.haruichiban.control.RequestSocket;
import br.udesc.ceavi.ppr.haruichiban.control.observers.GameStateObserverProxy;
import br.udesc.ceavi.ppr.haruichiban.model.Request;
import br.udesc.ceavi.ppr.haruichiban.utils.Images;
import com.google.gson.Gson;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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

    /**
     * Representa o player
     */
    private PlayerControllerProxy player;

    /**
     * Representa o Oponnet
     */
    private OponnetControllerProxy oponnet;

    /**
     * Cria o frame para conter o jogo.
     */
    public MainFrame() {
        super("Haru Ichiban");
    }

    public void begin(PlayerControllerProxy player) {
        this.player = player;
        this.initializeFrameProperties();
//        this.iniciaInterface();
        ClientController.getInstance().addObserver(this);
        ClientController.getInstance().play();
    }

    private void initImages() {
        getCanal().newRequest(Request.GAME_ESTACAO).enviar();
        String estacao = getCanal().getResposta();
        if (estacao.equalsIgnoreCase("Inverno")) {
            Images.mapImagemInverno();
        } else {
            Images.mapImagemPrimaveira();
        }
    }

    private void iniciaInterface() {
        this.initImages();
        this.oponnet = new OponnetControllerProxy();
        getCanal().newRequest(Request.OPONNET_COLOR).enviar();
        this.oponnet.setCor(new Gson().fromJson(getCanal().getResposta(), Color.class));
        this.initializeGameComponents();
    }

    private RequestSocket getCanal() {
        return ClientController.getInstance().getCanal();
    }

    /**
     * (Re)Inicializa as propriedades da tela atualizando esta de acordo.
     */
    public final void initializeFrameProperties() {
        this.setVisible(false);
        this.setMinimumSize(new Dimension(600, 400));
        this.setSize(800, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    /**
     * Inicializa os diferentes componentes do jogo.
     */
    private void initializeGameComponents() {
        this.gamePanel = new GamePanel();
        this.bottomPlayerPanel = new PlayerPanel(player.getColor(), player);
        this.topPlayerPanel = new PlayerPanel(oponnet.getColor(), oponnet);
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
        initializeFrameProperties();
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

    @Override
    public void update() {
        this.repaint();
    }

}
