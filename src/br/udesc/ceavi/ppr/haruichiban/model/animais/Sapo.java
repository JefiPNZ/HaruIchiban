package br.udesc.ceavi.ppr.haruichiban.model.animais;

import br.udesc.ceavi.ppr.haruichiban.utils.Images;
import java.awt.Color;
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
public class Sapo extends Animal{

    public Sapo(float rotacao, Color cor) {
        super(rotacao, cor);
    }

    private static BufferedImage imagem;
    @Override
    public BufferedImage getImagem() throws IOException{
        if(imagem == null){
            imagem = ImageIO.read(new File(Images.ANIMAL_SAPO));
        }
        return imagem;
    }

}
