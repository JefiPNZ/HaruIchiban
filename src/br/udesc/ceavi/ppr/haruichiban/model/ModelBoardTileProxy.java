package br.udesc.ceavi.ppr.haruichiban.model;

import java.awt.Color;

/**
 *
 * @author Gustavo C
 */
public class ModelBoardTileProxy {

    private Color corDaFolha;
    private Color corDoFilhote;
    private Color corDaPeca;
    private float folharRotacao;
    private String folhaNome;
    private String filhoteName;
    private float pecaRotacao;
    private String pecaName;

    public boolean hasFolha() {
        return corDaFolha != null;
    }

    public Color getCorFolha() {
        return corDaFolha;
    }

    public boolean hasFilhote() {
        return corDoFilhote != null;
    }

    public boolean hasPeca() {
        return corDaPeca != null;
    }

    public float getFolhaRotacao() {
        return folharRotacao;
    }
    
    public String getFolhaSimpleName(){
        return folhaNome;
    }
    
    public Color getFilhoteCor(){
        return corDoFilhote;
    }
    
    public String getFilhoteSimpleName(){
        return filhoteName;
    }
    
    public Color getPecaCor(){
        return corDaPeca;
    }
    
    public float getPecaRotacao(){
        return pecaRotacao;
    }
    
    public String getPecaSimpleName(){
        return pecaName;
    }
}
