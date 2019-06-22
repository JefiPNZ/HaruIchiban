package br.udesc.ceavi.ppr.haruichiban.cliente.view;

import br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces.IScoreController;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.ScoreController;
import br.udesc.ceavi.ppr.haruichiban.cliente.utils.Images;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Painel para representação da Pontuação.
 *
 * @author Jeferson Penz
 */
public class ScorePanel extends JPanel {

    private BufferedImage scoreImg;
    private IScoreController controller;

    /**
     * Cria um novo painel para a pontuação.
     */
    public ScorePanel() {
        super();
        controller = new ScoreController();
        this.scoreImg = Images.getImagem(Images.PONTUACAO_FUNDO);
        this.setOpaque(false);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setPreferredSize(new Dimension(150, 0));
        Thread tst = new Thread(() -> {
            while(true){
                SwingUtilities.invokeLater(()->{
                    repaint();
                });
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {}
            }
        });
        tst.start();
    }

    @Override
    /**
     * {@inheritdoc}
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        float scaleX = (float) this.getWidth() / (float) scoreImg.getWidth();
        float scaleY = (float) this.getHeight() / (float) scoreImg.getHeight();
        if (scaleX > scaleY) {
            int larguraDesenho = (int) (scaleY / scaleX * this.getWidth());
            int posicaoDesenho = (int) (this.getWidth() / 2f - larguraDesenho / 2f);
            
            int tamanhoBoneco = (int) (this.getHeight() * 0.06f);
            int posicaoX = (int) (posicaoDesenho * 1.5f);
            int posicaoY = 0 + tamanhoBoneco;
            int tamanhoX = this.getWidth() - posicaoX * 2;
            int tamanhoY = this.getHeight() - tamanhoBoneco * 2;
            
            g.fillRect(posicaoX, posicaoY, tamanhoX, tamanhoY);
            this.desenhaPontuacao(g, posicaoX, posicaoY, tamanhoX, tamanhoY);
            
            g.drawImage(scoreImg, posicaoDesenho, 0, larguraDesenho, this.getHeight(), null);
        } else {
            int alturaDesenho = (int) (scaleX / scaleY * this.getHeight());
            int posicaoDesenho = (int) (this.getHeight() / 2f - alturaDesenho / 2f);
            
            int tamanhoBoneco = (int) (alturaDesenho * 0.06f);
            int posicaoX = (int) (this.getWidth() * 0.05f);
            int posicaoY = posicaoDesenho + tamanhoBoneco;
            int tamanhoX = this.getWidth() - posicaoX * 2;
            int tamanhoY = alturaDesenho - tamanhoBoneco * 2;
            g.fillRect(posicaoX, posicaoY, tamanhoX, tamanhoY);
            this.desenhaPontuacao(g, posicaoX, posicaoY, tamanhoX, tamanhoY);
            
            g.drawImage(scoreImg, 0, posicaoDesenho, this.getWidth(), alturaDesenho, null);
        }
    }
    
    protected void desenhaPontuacao(Graphics g, int posX, int posY, int tamX, int tamY){
        g.setColor(Color.LIGHT_GRAY);
        int tamYJogadorTopo = (int) ((tamY / 10f) * controller.getTopPlayerScore() * 1.09f + tamY * 0.05f);
        int tamYJogadorBase = (int) ((tamY / 10f) * controller.getBotPlayerScore() * 1.09f);
        if(controller.getBotPlayerScore() == 1){
            tamYJogadorBase *= 1.3f;
        }
        int posYJogadorBase = posY + tamY - tamYJogadorBase;
        g.fillRect(posX, posY, tamX, tamYJogadorTopo);
        g.fillRect(posX, posYJogadorBase, tamX, tamYJogadorBase);
    }

}
