package br.udesc.ceavi.ppr.haruichiban.cliente.exceptions;

import java.awt.Dimension;

/**
 * Excessão para erros de resolução do jogo.
 * @author Jeferson Penz
 */
public class ResolutionException extends Exception{
    
    private Dimension resolucaoAlvo;
    private Dimension resolucaoMinima;
    private Dimension resolucaoMaxima;
    
    /**
     * Cria uma nova excessão ao trocar para a resolução desejada.
     * @param resolucaoAlvo - Resolução que tentou se atingir.
     * @param resolucaoMinima - Resolução mínima permitida.
     * @param resolucaoMaxima - Resolução máxima permitida.
     */
    public ResolutionException(Dimension resolucaoAlvo, Dimension resolucaoMinima, Dimension resolucaoMaxima) {
        this.resolucaoAlvo = resolucaoAlvo;
        this.resolucaoMinima = resolucaoMinima;
        this.resolucaoMaxima = resolucaoMaxima;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage() {
        String mensagem = "N\u00E3o foi possível alterar a resoluç\u00E3o";
        if(this.resolucaoAlvo != null){
            mensagem += " para " + this.resolucaoAlvo.getWidth() + " x " + this.resolucaoAlvo.getHeight();
        }
        if(this.resolucaoMinima != null || this.resolucaoMaxima != null){
            mensagem += "(deve ser ";
            if(this.resolucaoMinima != null){
                mensagem += "no mínimo " + this.resolucaoMinima.getWidth() + " x " + this.resolucaoMinima.getHeight();
                if(this.resolucaoMaxima != null){
                    mensagem += " e ";
                }
            }
            if(this.resolucaoMaxima != null){
                mensagem += "no máximo " + this.resolucaoMaxima.getWidth() + " x " + this.resolucaoMaxima.getHeight();
            }
            mensagem += ")";
        }
        return mensagem + ".";
    }
    
}
