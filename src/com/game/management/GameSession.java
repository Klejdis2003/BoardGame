package com.game.management;

import com.game.graphics.phases.DeploymentPhase;
import com.game.graphics.phases.DestructionPhase;
import com.game.graphics.phases.InitializationPhase;
import com.game.graphics.phases.StartingPhase;
import com.game.tools.Board;
import com.game.tools.ComputerPlayer;
import com.game.tools.Player;

import java.util.Random;

public class GameSession {
    public int startingPlayer;
    private InitializationPhase initializationPhase;
    private StartingPhase startingPhase;
    private DeploymentPhase deploymentPhase;
    private DestructionPhase destructionPhase;
    private InitializationPhase currentWindow;
    protected  Board board;
    public  Player player;
    public ComputerPlayer computerPlayer;
    public InitializationPhase getInitializationPhase() {
        return initializationPhase;
    }

    public void setInitializationPhase(InitializationPhase initializationPhase) {
        this.initializationPhase = initializationPhase;
    }
    public static int getStartingPlayer() {
        Random random = new Random();
        return random.nextInt(1, 3);
    }

    public StartingPhase getStartingPhase() {
        return startingPhase;
    }

    public void setStartingPhase(StartingPhase startingPhase) {
        this.startingPhase = startingPhase;
    }

    public DeploymentPhase getDeploymentPhase() {
        return deploymentPhase;
    }

    public void setDeploymentPhase(DeploymentPhase deploymentPhase) {
        this.deploymentPhase = deploymentPhase;
    }

    public DestructionPhase getDestructionPhase() {
        return destructionPhase;
    }
    public Board getBoard(){
        return board;
    }

    public void setDestructionPhase(DestructionPhase destructionPhase) {
        this.destructionPhase = destructionPhase;
    }
    public InitializationPhase getCurrentWindow(){
        return currentWindow;
    }
    public void begin(){
        player = new Player();
        computerPlayer = new ComputerPlayer();
        startingPlayer = getStartingPlayer();
        new InitializationPhase();
        currentWindow = initializationPhase;
        board = new Board(startingPlayer);
    }
    public void newGame(){
        initializationPhase.dispose();
        begin();
    }

}
