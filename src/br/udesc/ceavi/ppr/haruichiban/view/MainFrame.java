package br.udesc.ceavi.ppr.haruichiban.view;

import br.udesc.ceavi.ppr.haruichiban.control.GameController;
import br.udesc.ceavi.ppr.haruichiban.control.GameStateObserver;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * JFrame Principal contendo a tela da Aplicação.
 *
 * @author Jeferson Penz
 */
public class MainFrame extends JFrame implements GameStateObserver {

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
     * Cria o frame para conter o jogo.
     */
    public MainFrame() {
        super(GameController.GAME_NAME);
    }

    /**
     * Método principal da aplicação.
     *
     * @param args
     */
    public static void main(String[] args) {
        GameController.getInstance();
        MainFrame.exibeConfiguracao();
    }

    public void begin(String varianteTabuleiro, String tamanhoTabuleiro, Color corJogadorTopo, Color corJogadorBase) {
        GameController.getInstance().addGameStateObserver(this);
        GameController.getInstance().begin(varianteTabuleiro, tamanhoTabuleiro, corJogadorTopo, corJogadorBase);
        this.initializeGameComponents();
        this.initializeFrameProperties();
        SwingUtilities.invokeLater(() -> {
            GameController.getInstance().startGame();
        });
    }

    /**
     * (Re)Inicializa as propriedades da tela atualizando esta de acordo.
     */
    public final void initializeFrameProperties() {
        this.setVisible(false);
        this.setUndecorated(true);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Inicializa os diferentes componentes do jogo.
     */
    private void initializeGameComponents() {
        this.gamePanel = new GamePanel();
        this.topPlayerPanel = new PlayerPanel(GameController.getInstance().getTopPlayer().getColor(),
                GameController.getInstance().getTopPlayer());
        this.topPlayerPanel.setRotation(180);
        this.bottomPlayerPanel = new PlayerPanel(GameController.getInstance().getBottomPlayer().getColor(),
                GameController.getInstance().getBottomPlayer());
        this.boardPanel = new BoardPanel();
        this.scorePanel = new ScorePanel();
        this.menuPanel  = new MenuPanel();
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
        if(mensagem != null && !mensagem.isEmpty()){
            JOptionPane.showMessageDialog(this, mensagem);
        }
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        MainFrame.exibeConfiguracao();
    }
    
    public static void exibeConfiguracao(){
        FrameConfig frameConfig = new FrameConfig(new MainFrame());
        frameConfig.initializeFrameProperties();
    }

}
