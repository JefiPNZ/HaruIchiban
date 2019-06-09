package br.udesc.ceavi.ppr.haruichiban.model;

/**
 * Enum Request em uma String
 *
 * MY,Do Player OP,Do Oponnet GAME,Do game MY,ColorRequest MY -> Informa a
 *
 */
public enum Request {
    MY_COLOR("MY,Color"),
    MY_PILESIZE("MY,PileSize"),
    MY_POSITION("MY,Position"),
    MY_HAND("MY,Hand"),
    
    OPONNET_COLOR("OP,Color"),
    OPONNET_PILESIZE("OP,PileSize"),
    OPONNET_POSITION("OP,Position"),
    OPONNET_HAND("OP,Hand"),
    
    GAME_WINEW("GAME,Winer"),
    GAME_JOGOCONTINUA("GAME,JogoContinua"),
    GAME_HAVEOPONENT("GAME,HaveOponent"),
    GAME_POINTS("GAME,Points"),
    GAME_BOARD_TILE("GAME,BoardTile"),
    GAME_BOARD_HEIGHT("GAME,BordHeight"),
    GAME_BOARD_WIDTH("GAME,BordWidth"),
    GAME_ESTACAO("GAME,Estacao");

    private String request;

    Request(String request) {
        this.request = request;
    }

    public String getRequest() {
        return request;
    }
}
