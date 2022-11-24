package com.game.tools;

import com.game.Main;
import com.game.management.PlayerIds;

import java.util.ArrayList;
import java.util.Map;

public class Board {
    private final int startingPlayer;
    private static int numberOfInteractions = 0; //track the number of interactions
    private final Tower[] board = new Tower[16];
    private final ArrayList<Integer> availablePositions = new ArrayList<>(16);
    public int currentPlayer;

    public Board(int startingPlayer){
        this.startingPlayer = startingPlayer;
        for(int i = 0; i < board.length; i++){
            board[i] = new Tower();
            availablePositions.add(i);
        }
        currentPlayer = startingPlayer;
    }
    public void resetCurrentPlayer(){
        this.currentPlayer = startingPlayer;
    }
    public int getCurrentPlayer(){
        return currentPlayer;
    }
    public ArrayList<Integer> getAvailablePositions() {
        return availablePositions;
    }

    public Tower get(int index){
        return board[index];
    }
    public int size(){
        return board.length;
    }

    public void insert(int number, int index){
        if(numberOfInteractions %2 == 0) currentPlayer = startingPlayer;
        else currentPlayer = 3-startingPlayer; //function 3-x switches between players 1, 2

        Tower tower = new Tower(number, currentPlayer);
        board[index] = tower; //add the node to the array

        if(currentPlayer == PlayerIds.PLAYER)
            Main.gameSession.player.getRemainingNumbers().remove((Integer) number);

        availablePositions.remove((Integer)index);
        tower.calculateSumOfNeighbors();
        numberOfInteractions++;
    }
    public void destroy(int element){
        //function 3-x switches between players
         for(int i = 0; i<board.length; i++){
             if(board[i].value == element && board[i].isDestructible()) {
                 board[i] = new Tower();
                 board[i].calculateSumOfNeighbors();
             }
         }
        currentPlayer = 3 - currentPlayer;

    }
    public int destroy(Tower tower){
        for(int i = 0; i < board.length; i++){
            if(board[i].equals(tower)) {
                board[i] = new Tower();
                board[i].calculateSumOfNeighbors();
                currentPlayer = 3 - currentPlayer;
                return i;
            }
        }
        return -1;
    }
    public ArrayList<Tower> getDestroyableTowers(){ //returns the towers which can be destroyed according to the player making the destruction
        ArrayList<Tower> arr = new ArrayList<>();
        for(Tower tower : board){
            if(tower.isDestructible()) arr.add(tower);
        }
        return arr;
    }
    public void nextTurn(){
        currentPlayer = 3 - currentPlayer;
    }
    public int decideWinner(){ //returns 0 for draw, 1 for player and 2 for computer
        ArrayList<Tower> playerTowers = new ArrayList<>();
        int playerTowersSum = 0;
        ArrayList<Tower> computerTowers = new ArrayList<>();
        int computerTowersSum = 0;
        for(Tower tower : board){
            if(tower.player == PlayerIds.PLAYER) {
                playerTowers.add(tower);
                playerTowersSum += tower.value;
            }
            else if(tower.player == PlayerIds.COMPUTER_PLAYER) {
                computerTowers.add(tower);
                computerTowersSum += tower.value;
            }
        }
        if(playerTowers.size() > computerTowers.size()) return 1;
        else if(playerTowers.size() < computerTowers.size()) return 2;
        else{
            if(playerTowersSum > computerTowersSum) return 1;
            else if(playerTowersSum < computerTowersSum) return 2;
        }
        return 0;
    }
    public boolean isFull(){
        return availablePositions.size() == 0;
    }
    public String toString(){
        Map<Integer, String> mapping = PlayerIds.MAPPING;
        StringBuilder s = new StringBuilder("[");
        for(Tower tower : board){
            s.append(String.format("[Value: %d, : %s, Sum of neighbors is: %d], ", tower.value, mapping.get(tower.player), tower.sumOfNeighbors));
        }
        s.append("]");
        s.replace(s.length()-3,s.length()-1 , "");
        return s.toString();
    }
    public class Tower {
        protected int value; //x where x in [1,8]
        private int player; //player who inserted the element, it is 1 for Player and 2 for Enemy
        private int sumOfNeighbors; //sum of neighbor node values

        private Tower(int value, int player){
            this.value = value;
            this.player = player;
        }
        private Tower(){
            //get Tower with value 0 and empty player, hence 0
        }
        private void calculateSumOfNeighbors(){//finds the sum of neighbor node values of different type for all nodes
            int sum;
            for(int index = 0; index < board.length; index++){
                sum = 0;
                if(index != 0 )
                    if (board[index - 1].player != board[index].player) sum += board[index - 1].value;
                if(index != board.length-1)
                    if (board[index + 1].player != board[index].player) sum += board[index + 1].value;

                board[index].sumOfNeighbors = sum;
            }
        }
        public boolean isDestructible(){
            return this.sumOfNeighbors > this.value && this.player == 3-currentPlayer;
        }

        public boolean equals(Tower tower){
            return value == tower.value && player == tower.player;
        }

    }
}
