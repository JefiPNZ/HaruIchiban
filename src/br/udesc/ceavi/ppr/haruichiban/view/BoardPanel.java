package br.udesc.ceavi.ppr.haruichiban.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Painel para representação do tabuleiro do jogo.
 * @author Jeferson Penz
 */
public class BoardPanel extends JPanel{
    
    private BoardTable board;

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
    private void initializeBoard(){
        this.board       = new BoardTable(this);
        JScrollPane pane = new JScrollPane();
        
        pane.setViewportView(board);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        pane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        pane.setBackground(new Color(0, 0, 0, 0));
        pane.setOpaque(false);
        pane.getViewport().setOpaque(false);
        pane.setViewportBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pane.getViewport().setBackground(new Color(0, 0, 0, 0));
        this.add(pane);
    }
    
}
