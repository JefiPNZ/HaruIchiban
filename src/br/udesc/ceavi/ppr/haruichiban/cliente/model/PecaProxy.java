package br.udesc.ceavi.ppr.haruichiban.cliente.model;

import java.awt.Color;

/**
 *
 * @author Gustavo C
 */
public class PecaProxy {
    
    protected float pecaRotacao;
    protected String pecaName;
    protected Color pecaCor;

    public Color getPecaCor() {
        return pecaCor;
    }

    public float getPecaRotacao() {
        return pecaRotacao;
    }

    public String getPecaSimpleName() {
        return pecaName;
    }
    
    protected boolean isFlower(){
        return pecaName.contains("Flor");
    }
    
    protected boolean isAnimal(){
        return pecaName.contains("Pinguim") || pecaName.contains("Sapo");
    }

    public void setPecaCor(Color pecaCor) {
        this.pecaCor = pecaCor;
    }

    public void setPecaName(String pecaName) {
        this.pecaName = pecaName;
    }

    public void setPecaRotacao(float pecaRotacao) {
        this.pecaRotacao = pecaRotacao;
    }

    @Override
    public String toString() {
        return "PecaProxy{" + "pecaRotacao=" + pecaRotacao + ", pecaName=" + pecaName + ", pecaCor=" + pecaCor + '}';
    }
    
    
}
