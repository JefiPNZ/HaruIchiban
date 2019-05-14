package br.udesc.ceavi.ppr.haruichiban.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import br.udesc.ceavi.ppr.haruichiban.control.IPlayerController;
import br.udesc.ceavi.ppr.haruichiban.control.PlayerPanelObserver;
import br.udesc.ceavi.ppr.haruichiban.utils.ColorScale;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.event.ListSelectionListener;

/**
 * Tabela para a mão atual do jogador.
 *
 * @author Jeferson Penz
 */
public class PlayerHandTable extends JTable implements PlayerPanelObserver {

    private IPlayerController controller;
    private PlayerPanel parentPanel;
    private ListSelectionListener listener;

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
//                JOptionPane.showMessageDialog(null, e.toString());
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
            if (value != null) {
                BufferedImage img = (BufferedImage) value;
                if (isSelected) {
                    ColorScale scale = new ColorScale(1.15f, 1.15f, 1.15f);
                    img = scale.convert(img);
                }
                this.setIcon(new ImageIcon(img));
                if (table.isEnabled()) {
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
    public PlayerHandTable(PlayerPanel parent, IPlayerController controller) {
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
            controller.selecionarFlor(getSelectedColumn());
            this.getColumnModel().getSelectionModel().clearSelection();
            this.setEnabled(false);
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
        size.width = size.width > 375 ? 375 : size.width;
        this.setRowHeight((int) size.getHeight());
        return size;
    }

    @Override
    public void repintarPlayerHand() {
        this.repaint();
    }

    @Override
    public void notifyJogadorEscolhaUmaFlor() {
        this.getColumnModel().getSelectionModel().addListSelectionListener(listener);
        this.setEnabled(true);
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
    public void notifyEscolhaUmaPosicaoNoTabuleiro() {
    }

    @Override
    public void notifySimpleMessager(String messagem) {
    }
}
