package br.udesc.ceavi.ppr.haruichiban.view;

import br.udesc.ceavi.ppr.haruichiban.control.GameController;
import br.udesc.ceavi.ppr.haruichiban.utils.Diretion;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Painel para representação do tabuleiro do jogo.
 *
 * @author Jeferson Penz
 */
public class BoardPanel extends JPanel {

    private BoardTable board;
    private JButton norte;
    private JButton sul;
    private JButton leste;
    private JButton oeste;

    /**
     * Cria um novo painel para o Tabuleiro.
     */
    public BoardPanel() {
        super();
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                board.revalidate();
            }
        });
        this.setBackground(new Color(0, 0, 0));
        this.setOpaque(false);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        this.initializeBoard();
    }

    /**
     * Realiza o carregamento da tabela de jogo.
     */
    private void initializeBoard() {
        this.board = new BoardTable(this);
        GameController.getInstance().addGameStateObserver(this.board);
        JScrollPane pane = new JScrollPane();
        this.setLayout(new BorderLayout());
        norte = new JButton("<html>Norte</html>");
        norte.setPreferredSize(new Dimension(0, 30));
        norte.setBorder(null);
        norte.setFocusPainted(false);
        norte.setBackground(new Color(120, 143, 235));
        sul = new JButton("<html>Sul</html");
        sul.setBorder(null);
        sul.setPreferredSize(new Dimension(0, 30));
        sul.setBackground(new Color(120, 143, 235));
        oeste = new JButton("<html>O<br/>e<br/>s<br/>t<br/>e</html>");
        oeste.setPreferredSize(new Dimension(30, 0));
        oeste.setBorder(null);
        oeste.setBackground(new Color(120, 143, 235));
        leste = new JButton("<html>L<br/>e<br/>s<br/>t<br/>e</html>");
        leste.setPreferredSize(new Dimension(30, 0));
        leste.setBorder(null);
        leste.setBackground(new Color(120, 143, 235));
        desativarBotoes();
        JPanel pa = new JPanel();
        pa.setOpaque(false);
        pa.add(pane);
        this.add(norte, BorderLayout.NORTH);
        this.add(sul, BorderLayout.SOUTH);
        this.add(oeste, BorderLayout.WEST);
        this.add(leste, BorderLayout.EAST);
        
        
        pane.setViewportView(board);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        pane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        pane.setBackground(new Color(0, 0, 0, 0));
        pane.setOpaque(false);
        pane.getViewport().setOpaque(false);
        pane.setViewportBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pane.getViewport().setBackground(new Color(0, 0, 0, 0));

        this.add(pa, BorderLayout.CENTER);
    }

    protected void desativarBotoes() {
        norte.setSelected(false);
        norte.setEnabled(false);

        sul.setSelected(false);
        sul.setEnabled(false);

        oeste.setSelected(false);
        oeste.setEnabled(false);

        leste.setSelected(false);
        leste.setEnabled(false);
    }

    protected void ativarBotoes() {
        norte.setEnabled(true);
        norte.setSelected(true);
        
        sul.setEnabled(true);
        sul.setSelected(true);
        
        oeste.setEnabled(true);
        oeste.setSelected(true);
        
        leste.setEnabled(true);
        leste.setSelected(true);
        
        norte.addActionListener((e) -> board.controlleClick(Diretion.NORTE));
        sul.addActionListener((e) -> board.controlleClick(Diretion.SUL));
        oeste.addActionListener((e) -> board.controlleClick(Diretion.OESTE));
        leste.addActionListener((e) -> board.controlleClick(Diretion.LEST));
    }
}
