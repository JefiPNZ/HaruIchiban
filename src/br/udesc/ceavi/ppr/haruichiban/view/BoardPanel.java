package br.udesc.ceavi.ppr.haruichiban.view;

import br.udesc.ceavi.ppr.haruichiban.control.GameController;
import br.udesc.ceavi.ppr.haruichiban.utils.Diretion;
import java.awt.BorderLayout;
import java.awt.Color;
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
    private JButton lest;
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
        norte = new JButton("Norte");
        sul = new JButton("Sul");
        oeste = new JButton("Oeste");
        lest = new JButton("Lest");
        desativarBotoes();
        JPanel pa = new JPanel();
        pa.setOpaque(false);
        pa.add(pane);
        this.add(norte, BorderLayout.NORTH);
        this.add(sul, BorderLayout.SOUTH);
        this.add(oeste, BorderLayout.WEST);
        this.add(lest, BorderLayout.EAST);

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

        lest.setSelected(false);
        lest.setEnabled(false);
    }

    protected void ativarBotoes() {
        norte.setEnabled(true);
        norte.setSelected(true);
        
        sul.setEnabled(true);
        sul.setSelected(true);
        
        oeste.setEnabled(true);
        oeste.setSelected(true);
        
        lest.setEnabled(true);
        lest.setSelected(true);
        
        norte.addActionListener((e) -> board.controlleClick(Diretion.NORTE));
        sul.addActionListener((e) -> board.controlleClick(Diretion.SUL));
        oeste.addActionListener((e) -> board.controlleClick(Diretion.OESTE));
        lest.addActionListener((e) -> board.controlleClick(Diretion.LEST));
    }
}
