package br.udesc.ceavi.ppr.haruichiban.view;

import br.udesc.ceavi.ppr.haruichiban.control.Game;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * JFrame Principal contendo a tela da Aplicação.
 * @author Jeferson Penz
 */
public class MainFrame extends JFrame{
    
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
    public MainFrame(){
        super(Game.GAME_NAME);
        this.initializeGameComponents();
    }
    
    /**
     * (Re)Inicializa as propriedades da tela atualizando esta de acordo.
     */
    public final void initializeFrameProperties(){
        this.setVisible(false);
        this.setSize(new Dimension(1024, 768));
        this.setUndecorated(true);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    /**
     * Inicializa os diferentes componentes do jogo.
     */
    private void initializeGameComponents(){
        this.gamePanel         = new GamePanel();
        this.topPlayerPanel    = new PlayerPanel(new Color(255, 210, 65));
        this.topPlayerPanel.setRotation(180);
        this.bottomPlayerPanel = new PlayerPanel(new Color(255, 15, 35));
        this.boardPanel        = new BoardPanel();
        this.scorePanel        = new ScorePanel();
        this.menuPanel         = new JPanel();
        this.gamePanel.add(this.topPlayerPanel,    BorderLayout.NORTH);
        this.gamePanel.add(this.bottomPlayerPanel, BorderLayout.SOUTH);
        this.gamePanel.add(this.boardPanel,        BorderLayout.CENTER);
        this.gamePanel.add(this.scorePanel,        BorderLayout.WEST);
        this.gamePanel.add(this.menuPanel,         BorderLayout.EAST);
        this.menuPanel.setBackground(new Color(140, 75, 55));
        this.menuPanel.setLayout(new BoxLayout(this.menuPanel, BoxLayout.Y_AXIS));
        JLabel haruIchiban = new JLabel("Haru Ichiban");
        haruIchiban.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        haruIchiban.setForeground(Color.white);
        JLabel men = new JLabel("Menu");
        men.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        men.setForeground(Color.white);
        this.menuPanel.add(haruIchiban);
        this.menuPanel.add(men);
        this.setContentPane(this.gamePanel);
    }
    
}