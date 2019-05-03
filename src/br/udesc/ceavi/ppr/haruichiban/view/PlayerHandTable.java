package br.udesc.ceavi.ppr.haruichiban.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import br.udesc.ceavi.ppr.haruichiban.control.IPlayerController;
import br.udesc.ceavi.ppr.haruichiban.utils.ColorScale;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.event.ListSelectionEvent;

/**
 * Tabela para a mão atual do jogador.
 * @author Jeferson Penz
 */
public class PlayerHandTable extends JTable{
    
    private IPlayerController controller;
    private PlayerPanel       parentPanel;

    /**
     * Modelo de dados para tabela.
     */
    private class PlayerHandTableModel extends AbstractTableModel {

        @Override
        public int getColumnCount() {
            return controller.getHand().size();
        }

        @Override
        public int getRowCount() {
            return 1;
        }

        @Override
        public Object getValueAt(int row, int col) {
            try {
                return parentPanel.getFlowerImg();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString());
                return null;
            }
        }
    }

    /**
     * Renderização dos dados da tabela.
     */
    private class PlayerHandTableRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if(value != null){
                BufferedImage img = (BufferedImage) value;
                if(isSelected){
                    ColorScale scale = new ColorScale(1.15f, 1.15f, 1.15f);
                    img = scale.convert(img);
                }
                this.setIcon(new ImageIcon(img));
                this.setText(controller.getHand().get(column).toString());
            }
            return this;
        }
    }

    /**
     * Cria uma nova tabela para a mão do jogador.
     * @param parent 
     * @param controller 
     */
    public PlayerHandTable(PlayerPanel parent, IPlayerController controller) {
        this.parentPanel = parent;
        this.controller  = controller;
        this.setModel(new PlayerHandTableModel());
        this.setDefaultRenderer(Object.class, new PlayerHandTableRenderer());
        this.setBackground(new Color(0, 0, 0, 0));
        this.setRowSelectionAllowed(false);
        this.setCellSelectionEnabled(true);
        this.setDragEnabled(false);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setTableHeader(null);
        this.setFillsViewportHeight(true);
        this.setOpaque(false);
        this.setShowGrid(false);
        this.setForeground(Color.WHITE);
        this.getColumnModel().getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if(!e.getValueIsAdjusting()){
                executeTableSelectionChange(new Point(getSelectedColumn(), getSelectedRow()));
            }
        });
    }
    
    /**
     * Executa a chamada do evento de troca de seleção da mão do jogador.
     * @param newSelection 
     */
    protected void executeTableSelectionChange(Point newSelection){
        System.out.println("Nova seleção para mão do jogador: " + newSelection);
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
        size.width  = size.width > 300 ? 300 : size.width;
        size.height = size.width / this.getModel().getColumnCount();
        this.setRowHeight((int) size.getHeight() / this.getModel().getRowCount());
        return size;
    }
    
}
