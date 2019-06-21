package br.udesc.ceavi.ppr.haruichiban.cliente.view;

import br.udesc.ceavi.ppr.haruichiban.cliente.utils.Images;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * Painel para representação do jogo.
 *
 * @author Jeferson Penz
 */
public class GamePanel extends JPanel {

    private BufferedImage waterImg;

    /**
     * Cria um novo painel para o jogo.
     */
    public GamePanel() {
        super();
        waterImg = Images.getImagem(Images.TABULEIRO_AGUA);
        this.setBackground(new Color(86, 134, 190));
        this.setLayout(new BorderLayout(1, 1));
    }

    @Override
    /**
     * {@inheritdoc}
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int xImg = (int) Math.ceil((float) this.getWidth() / (float) waterImg.getWidth());
        int yImg = (int) Math.ceil((float) this.getHeight() / (float) waterImg.getHeight());
        ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05f));
        for (int i = 0; i < xImg; i++) {
            for (int j = 0; j < yImg; j++) {
                g.drawImage(waterImg, i * waterImg.getWidth(), j * waterImg.getHeight(), null);
            }
        }
        ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
    }

}
