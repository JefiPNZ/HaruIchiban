package br.udesc.ceavi.ppr.haruichiban.view;

import br.udesc.ceavi.ppr.haruichiban.utils.Images;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Painel para representação da Pontuação.
 * @author Jeferson Penz
 */
public class ScorePanel extends JPanel{
    
    private BufferedImage scoreImg;

    /**
     * Cria um novo painel para a pontuação.
     */
    public ScorePanel() {
        super();
        try {
            this.scoreImg = ImageIO.read(new File(Images.PONTUACAO_FUNDO));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível ler os arquivos de imagem do jogo.");
        }
        this.setOpaque(false);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setPreferredSize(new Dimension(150, 0));
    }

    @Override
    /**
     * {@inheritdoc}
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        float scaleX = (float)this.getWidth() / (float)scoreImg.getWidth();
        float scaleY = (float)this.getHeight() / (float)scoreImg.getHeight();
        if(scaleX > scaleY){
            int width = (int) (scaleY / scaleX * this.getWidth());
            g.drawImage(scoreImg, this.getWidth() - width, 0, width, this.getHeight(), null);
        }
        else {
            int height = (int) (scaleX / scaleY * this.getHeight());
            g.drawImage(scoreImg, 0, this.getHeight() / 2 - height / 2, this.getWidth(), height, null);
        }
    }
    
}
