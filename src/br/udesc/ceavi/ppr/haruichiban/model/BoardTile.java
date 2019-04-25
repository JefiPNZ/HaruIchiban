package br.udesc.ceavi.ppr.haruichiban.model;

import br.udesc.ceavi.ppr.haruichiban.Game;
import br.udesc.ceavi.ppr.haruichiban.utils.ColorScale;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/**
 * Representa uma peça no tabuleiro.
 * @author Jeferson Penz
 */
public class BoardTile {
    
    private boolean lilypad;
    private final int rotation;

    /**
     * Cria uma nova peça para o tabuleiro.
     */
    public BoardTile() {
        this.rotation = Game.getInstance().getRandomizer().nextInt(360);
        this.lilypad  = false;
    }
    
    /**
     * Adiciona uma vitória regia para a peça.
     */
    public void addLilypad(){
        this.lilypad = true;
    }
    
    /**
     * Remove uma vitória regia para a peça.
     */
    public void removeLilypad(){
        this.lilypad = false;
    }
    
    /**
     * Retorna a imagem da peça para exibição.
     * @param sizeX
     * @param sizeY
     * @param isSelected
     * @return 
     */
    public ImageIcon getImage(int sizeX, int sizeY, boolean isSelected){
          // TODO: MOVER PARA A VIEW
//        ColorScale scale;
//        if(isSelected){
//            scale = new ColorScale(125, 255, 175);
//        }
//        else {
//            scale = new ColorScale(0, 255, 0);
//        }
//        BufferedImage converted = new BufferedImage(BACKGROUND_IMAGE.getWidth(), BACKGROUND_IMAGE.getHeight(), BufferedImage.TYPE_INT_ARGB);
//        Graphics2D g = converted.createGraphics();
//        g.rotate(Math.toRadians(this.rotation), converted.getWidth() / 2, converted.getHeight() / 2);
//        g.drawImage(BACKGROUND_IMAGE, 0, 0, null);
//        if(this.lilypad){
//            g.drawImage(scale.convert(LILYPAD_IMAGE), 0, 0, null);
//        }
//        AffineTransform sclTransform  = AffineTransform.getScaleInstance((float)sizeX / converted.getWidth(), (float)sizeY / converted.getHeight());
//        AffineTransformOp sclOperator = new AffineTransformOp(sclTransform, AffineTransformOp.TYPE_BICUBIC);
//        return new ImageIcon(sclOperator.filter(converted, null));
        return null;
    }
    
}
