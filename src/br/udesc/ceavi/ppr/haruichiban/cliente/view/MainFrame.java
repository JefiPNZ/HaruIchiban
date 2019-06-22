package br.udesc.ceavi.ppr.haruichiban.cliente.view;

import br.udesc.ceavi.ppr.haruichiban.cliente.control.GameClienteController;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.observers.GameStateObserver;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * JFrame Principal contendo a tela da Aplicação.
 *
 * @author Jeferson Penz
 */
public class MainFrame extends JFrame implements GameStateObserver {

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
     * Cria o frame para conter o jogo.
     */
    public MainFrame() {
        super(GameClienteController.GAME_NAME);
        GameClienteController.getInstance();
        GameClienteController.getInstance().addGameStateObserver(this);
        GameClienteController.getInstance().begin();
        while (!GameClienteController.getInstance().isDadosCarregados()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {}
        }
        this.initializeGameComponents();
        this.initializeFrameProperties();
        GameClienteController.getInstance().startGame();
    }

    /**
     * Método principal da aplicação.
     *
     * @param args
     */
    public static void main(String[] args) {
        new MainFrame();
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
        this.setLocationRelativeTo(null);
    }

    /**
     * Inicializa os diferentes componentes do jogo.
     */
    private void initializeGameComponents() {
        this.gamePanel = new GamePanel();
        this.topPlayerPanel = new PlayerPanel(GameClienteController.getInstance().getTopPlayer().getColor(),
                GameClienteController.getInstance().getTopPlayer());
        this.topPlayerPanel.setRotation(180);
        this.bottomPlayerPanel = new PlayerPanel(
                GameClienteController.getInstance().getBottomPlayer().getColor(),
                GameClienteController.getInstance().getBottomPlayer());
        this.boardPanel = new BoardPanel();
        this.scorePanel = new ScorePanel();
        this.menuPanel = new MenuPanel();
        this.gamePanel.add(this.topPlayerPanel, BorderLayout.NORTH);
        this.gamePanel.add(this.bottomPlayerPanel, BorderLayout.SOUTH);
        this.gamePanel.add(this.boardPanel, BorderLayout.CENTER);
        this.gamePanel.add(this.scorePanel, BorderLayout.WEST);
        this.gamePanel.add(this.menuPanel, BorderLayout.EAST);
        GameClienteController.getInstance().getPlayer().notifyUtilizaCoachar();
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

    @Override
    public void notifyVencedor(int pontosDoVencedor, int pontosDoPerdedor) {
        JOptionPane.showMessageDialog(null, "Voce ganhou e se tornou o Jardineiro Supremo! \nCom " + pontosDoVencedor
                + " pontos e seu Adversa\u00e1rio com " + pontosDoPerdedor,
                "GAME OVER", JOptionPane.INFORMATION_MESSAGE);
        GameClienteController.getInstance().getPackageInput().close();
        GameClienteController.getInstance().getPackageOutput().close();
        System.exit(0);
    }

    @Override
    public void notifyPerdedor(int pontosDoVencedor, int pontosDoPerdedor) {
        JOptionPane.showMessageDialog(null, "Voce Perdeu! \nSeu oponente fez " + pontosDoVencedor + " e voce fez apenas " + pontosDoPerdedor, "GAME OVER", JOptionPane.INFORMATION_MESSAGE);
        GameClienteController.getInstance().getPackageInput().close();
        GameClienteController.getInstance().getPackageOutput().close();
        System.exit(0);
    }

}
