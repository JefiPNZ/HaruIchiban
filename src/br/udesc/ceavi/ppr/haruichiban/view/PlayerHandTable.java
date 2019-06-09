package br.udesc.ceavi.ppr.haruichiban.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import br.udesc.ceavi.ppr.haruichiban.utils.ColorScale;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import javax.swing.event.ListSelectionListener;

/**
 * Tabela para a m�o atual do jogador.
 *
 * @author Jeferson Penz
 */
public class PlayerHandTable extends JTable implements IPlayerPanelObserver {

    private static final long serialVersionUID = 1L;
    private Jogador controller;
    private PlayerPanel parentPanel;
    private ListSelectionListener listener;

    /**
     * Modelo de dados para tabela.
     */
    private class PlayerHandTableModel extends AbstractTableModel {

        private static final long serialVersionUID = 1L;

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
            return parentPanel.getFlowerImg();
        }
    }

    /**
     * Renderização dos dados da tabela.
     */
    private class PlayerHandTableRenderer extends DefaultTableCellRenderer {

        private static final long serialVersionUID = 1L;

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value != null) {
                BufferedImage img = (BufferedImage) value;
                if (isSelected) {
                    ColorScale scale = new ColorScale(1.15f, 1.15f, 1.15f);
                    img = scale.convert(img);
                }
                AffineTransform transform = AffineTransform.getScaleInstance((float) table.getColumnModel().getColumn(column).getWidth() / img.getWidth() * 0.8f,
                                                                             (float) table.getRowHeight() / img.getHeight() * 0.8f);
                AffineTransformOp operator = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);
                this.setIcon(new ImageIcon(operator.filter(img, null)));
                if (table.isEnabled() && controller.getHand().size() > column) {
                    this.setText(controller.getHand().get(column).toString());
                } else {
                    this.setText("");
                }
            }
            return this;
        }
    }

    /**
     * Cria uma nova tabela para a mão do jogador.
     *
     * @param parent
     * @param controller entrege pelo GameController
     */
    public PlayerHandTable(PlayerPanel parent, Jogador controller) {
        this.parentPanel = parent;
        this.controller = controller;
        this.controller.addObserver(this);
        this.initPropriedadesComponente();
        this.listener = (e) -> {
            if (!e.getValueIsAdjusting()) {
                executeTableSelectionChange(new Point(getSelectedColumn(), getSelectedRow()));
            }
        };
    }

    private void initPropriedadesComponente() {
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
        this.setEnabled(false);
    }

    /**
     * Executa a chamada do evento de troca de seleção da mão do jogador.
     *
     * @param newSelection
     */
    protected void executeTableSelectionChange(Point newSelection) {
        if (!this.columnModel.getSelectionModel().isSelectionEmpty()) {
            controller.chooseFlowerDeckEnd(getSelectedColumn());
            this.getColumnModel().getSelectionModel().clearSelection();
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
        size.width = (int)(size.width * 0.4);
        float scaleX = (float) size.getWidth();
        float scaleY = (float) size.getHeight();
        if (scaleX > scaleY * 3) {
            int width = (int) (scaleY * 3 / scaleX * scaleX);
            size = new Dimension(width, (int) size.getHeight());
        } else {
            int height = (int) (scaleX / 3 / scaleY * scaleY);
            size = new Dimension((int) size.getWidth(), height);
        }
        this.setRowHeight((int) size.getHeight());
        return size;
    }

    @Override
    public void notifyJogadorEscolhaUmaFlor() {
        this.setEnabled(true);
        ((AbstractTableModel) this.getModel()).fireTableStructureChanged();
        this.repaint();
        this.getColumnModel().getSelectionModel().addListSelectionListener(listener);
    }

    @Override
    public void notifyJogadorEscolhaUmaFlorEnd() {
        this.setEnabled(false);
        ((AbstractTableModel) this.getModel()).fireTableStructureChanged();
        this.repaint();
        this.getColumnModel().getSelectionModel().addListSelectionListener(listener);
    }

    @Override
    public void notifyYouAreJunior() {
    }

    @Override
    public void notifyYouAreSenior() {
    }

    @Override
    public void notifyYouAreSemTitulo() {
    }

    @Override
    public void notifySimpleMessager(String messagem) {
    }
}
