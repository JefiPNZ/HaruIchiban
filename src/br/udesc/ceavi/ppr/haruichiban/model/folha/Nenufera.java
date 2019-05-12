package br.udesc.ceavi.ppr.haruichiban.model.folha;

import br.udesc.ceavi.ppr.haruichiban.utils.Images;
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
public class Nenufera extends Folha{

    public Nenufera(float rotacao) {
        super(rotacao);
    }

    private static BufferedImage imagem;
    @Override
    public BufferedImage getImagem() throws IOException{
        if(imagem == null){
            imagem = ImageIO.read(new File(Images.VITORIA_REGIA));
        }
        return imagem;
    }

}
