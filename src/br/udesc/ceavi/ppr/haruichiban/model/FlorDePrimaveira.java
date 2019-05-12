package br.udesc.ceavi.ppr.haruichiban.model;

import br.udesc.ceavi.ppr.haruichiban.utils.Images;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Gustavo C Santos
 * @since 10/05/2019
 * 
 */
public class FlorDePrimaveira extends Flor{

    public FlorDePrimaveira(float rotacao, Color cor, int valor, ModelPlayer playerOrigem) {
        super(rotacao, cor, valor, playerOrigem);
    }

    private static BufferedImage imagem;
    @Override
    public BufferedImage getImagem() throws IOException{
        if(imagem == null){
            imagem = ImageIO.read(new File(Images.JOGADOR_FLOR));
        }
        return imagem;
    }

}
