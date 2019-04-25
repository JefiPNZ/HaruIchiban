package br.udesc.ceavi.ppr.haruichiban.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import br.udesc.ceavi.ppr.haruichiban.control.IBoardController;
import br.udesc.ceavi.ppr.haruichiban.control.TestBoardController;
import br.udesc.ceavi.ppr.haruichiban.utils.ColorScale;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Tabela para representação do tabuleiro do jogo.
 * @author Jeferson Penz
 */
public class BoardTable extends JTable {
    
    private IBoardController controller;
    private BoardPanel       parentPanel;

    /**
     * Modelo de dados para tabela.
     */
    private class BoardTableModel extends AbstractTableModel {

        @Override
        public int getColumnCount() {
            return 5;
        }

        @Override
        public int getRowCount() {
            return 5;
        }

        @Override
        public Object getValueAt(int row, int col) {
            try {
                return controller.getImage(row, col);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString());
                return null;
            }
        }
    }

    /**
     * Renderização dos dados da tabela.
     */
    private class BoardTableRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if(value != null){
                BufferedImage img = (BufferedImage) value;
                if(isSelected){
                    ColorScale scale = new ColorScale(1.15f, 1.15f, 1.15f);
                    img = scale.convert(img);
                }
                AffineTransform transform  = AffineTransform.getScaleInstance((float)table.getColumnModel().getColumn(column).getWidth() / img.getWidth(),
                                                                              (float)table.getRowHeight() / img.getHeight());
                AffineTransformOp operator = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);
                this.setIcon(new ImageIcon(operator.filter(img, null)));
            }
            return this;
        }
    }

    /**
     * Cria uma nova tabela para servir de tabuleiro.
     */
    public BoardTable(BoardPanel parent) {
        this.controller  = new TestBoardController();
        this.parentPanel = parent;
        this.setModel(new BoardTableModel());
        this.setDefaultRenderer(Object.class, new BoardTableRenderer());
        this.setBackground(new Color(0, 0, 0, 0));
        this.setRowSelectionAllowed(false);
        this.setCellSelectionEnabled(true);
        this.setDragEnabled(false);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setTableHeader(null);
        this.setFillsViewportHeight(true);
        this.setOpaque(false);
        this.setShowGrid(false);
        this.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if(!e.getValueIsAdjusting()){
                executeTableSelectionChange(new Point(getSelectedColumn(), getSelectedRow()));
            }
        });
        this.getColumnModel().getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if(!e.getValueIsAdjusting()){
                executeTableSelectionChange(new Point(getSelectedColumn(), getSelectedRow()));
            }
        });
    }
    
    private Point lastSelection = null;
    /**
     * Executa o evento de troca da seleção do tabuleiro.<br/>
     * Como há dois eventos ouvindo (linha e coluna), é necessário garantir que apenas seja chamado o evento quando não
     * houver a troca de posição.
     * @param newSelection 
     */
    protected void executeTableSelectionChange(Point newSelection){
        if(!newSelection.equals(lastSelection)){
            lastSelection = newSelection;
            System.out.println("Nova seleção: " + newSelection);
        }
    }

    @Override
    /**
     * {@inheritdoc}
     */
    public Dimension getPreferredScrollableViewportSize() {
        Dimension size = parentPanel.getSize();
        if (size.getWidth() <= 0 || size.getHeight() <= 0) {
            return new Dimension(0, 0);
        }
        size.height -= 20;
        size.width  -= 20;
        float scaleX = (float) size.getWidth();
        float scaleY = (float) size.getHeight();
        if (scaleX > scaleY) {
            int width = (int) (scaleY / scaleX * size.getWidth());
            size = new Dimension(width, (int) size.getHeight());
        } else {
            int height = (int) (scaleX / scaleY * size.getHeight());
            size = new Dimension((int) size.getWidth(), height);
        }
        this.setRowHeight((int) size.getHeight() / this.getModel().getRowCount());
        return size;
    }
}
