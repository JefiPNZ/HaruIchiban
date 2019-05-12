package br.udesc.ceavi.ppr.haruichiban.view;

import br.udesc.ceavi.ppr.haruichiban.control.IScoreController;
import br.udesc.ceavi.ppr.haruichiban.control.ScoreTestController;
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
    private IScoreController controller;

    /**
     * Cria um novo painel para a pontuação.
     */
    public ScorePanel() {
        super();
        controller = new ScoreTestController();
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
        g.setColor(Color.black);
        float scaleX = (float)this.getWidth() / (float)scoreImg.getWidth();
        float scaleY = (float)this.getHeight() / (float)scoreImg.getHeight();
        if(scaleX > scaleY){
            int width = (int) (scaleY / scaleX * this.getWidth());
            g.fillRect(this.getWidth() - width + 5, 20, width - 10, this.getHeight() - 40);
            g.drawImage(scoreImg, this.getWidth() - width, 0, width, this.getHeight(), null);
        }
        else {
            int height = (int) (scaleX / scaleY * this.getHeight());
            g.fillRect(5, this.getHeight() / 2 - height / 2 + 20, this.getWidth() - 10, height - 40);
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(5, this.getHeight() / 2 - height / 2 + 20, this.getWidth() - 10, ((height - 40) / 2) / 5 * controller.getTopPlayerScore() + 20);
            int posBot = (this.getHeight() / 2 + height / 2 - 20) - ((height - 40) / 2) / 5 * controller.getBotPlayerScore() - (controller.getBotPlayerScore() == 1 ? 20 : 0);
            g.fillRect(5, posBot, this.getWidth() - 10, ((height - 40) / 2) / 5 * controller.getBotPlayerScore() - (controller.getBotPlayerScore() == 1 ? 20 : 0));
            g.drawImage(scoreImg, 0, this.getHeight() / 2 - height / 2, this.getWidth(), height, null);
        }
    }
    
}
