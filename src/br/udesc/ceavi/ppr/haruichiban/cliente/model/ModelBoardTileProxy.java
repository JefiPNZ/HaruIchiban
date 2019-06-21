package br.udesc.ceavi.ppr.haruichiban.cliente.model;

import java.awt.Color;

/**
 *
 * @author Gustavo C
 */
public class ModelBoardTileProxy {

    private Color folhaCor;
    private float folharRotacao;
    private String folhaNome;
    private boolean isEscura;

    private Color corFilhote;
    private String filhoteName;

    private PecaProxy peca;

    public boolean hasFolha() {
        return folhaCor != null;
    }

    public Color getCorFolha() {
        return folhaCor;
    }

    public boolean hasFilhote() {
        return corFilhote != null;
    }

    public boolean hasPeca() {
        return peca != null;
    }

    public boolean hasFlower() {
        return hasPeca() && peca.isFlower();
    }

    public boolean hasAnimal() {
        return hasPeca() && peca.isAnimal();
    }

    public float getFolhaRotacao() {
        return folharRotacao;
    }

    public String getFolhaSimpleName() {
        return folhaNome;
    }

    public Color getFilhoteCor() {
        return corFilhote;
    }

    public String getFilhoteSimpleName() {
        return filhoteName;
    }

    public PecaProxy getPeca() {
        return peca;
    }

    public boolean isEscura() {
        return isEscura;
    }

    public void setPeca(PecaProxy peca) {
        this.peca = peca;
    }

    
    @Override
    public String toString() {
        return "ModelBoardTileProxy{" + "folhaCor=" + folhaCor + ", folharRotacao=" + folharRotacao + ", folhaNome=" + folhaNome + ", isEscura=" + isEscura + ", corFilhote=" + corFilhote + ", filhoteName=" + filhoteName + ", peca=" + peca + '}';
    }

    public void tornarEscura() {
        isEscura = true;
    }

}
