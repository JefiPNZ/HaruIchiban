package br.udesc.ceavi.ppr.haruichiban.model.filhote;

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
public class OvoDeSapo extends Filhote{

    public OvoDeSapo(float rotacao, Color cor) {
        super(rotacao, cor);
    }

    private static BufferedImage imagem;
    @Override
    public BufferedImage getImagem() throws IOException{
        if(imagem == null){
            imagem = ImageIO.read(new File(Images.OVO_SAPO));
        }
        return imagem;
    }

}
