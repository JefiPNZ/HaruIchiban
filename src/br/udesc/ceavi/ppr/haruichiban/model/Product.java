/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.ppr.haruichiban.model;

public enum Product {
    MY_READY("MY,Ready"),
    MY_PRODUCEMYDECK("MY,ProduceDeck"),
    MY_CHOOSEFLOWER("MY,ChooseFlower"),
    GAME_ENDGAME("GAME,ENDGAME");

    private String request;

    Product(String request) {
        this.request = request;
    }

    public String getProduct() {
        return request;
    }
}
