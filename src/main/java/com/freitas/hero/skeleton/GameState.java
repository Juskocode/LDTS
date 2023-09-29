package com.freitas.hero.skeleton;

import com.freitas.hero.skeleton.elements.Block;
import com.freitas.hero.skeleton.elements.Coin;
import com.freitas.hero.skeleton.elements.Hero;
import com.freitas.hero.skeleton.elements.Wall;
import static com.freitas.hero.algorithms.MazeGenerator.*;
import com.freitas.hero.dataStructs.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameState implements StateInterface {
    private final int width; //width of display
    private final int height; //height of display

    private int END;

    private final Hero hero;

    private final List<Wall> walls;
    private final List<Block> blocks;

    private char[][] MAZE = new char[HEIGHT][WIDTH];

    ArrayList<Pair<Integer, Integer>> Freeindexs = new ArrayList<>();
    private List<Coin> coinsMap;

    public GameState(int width, int height){

        this.width = width;
        this.height = height;

        hero = new Hero(width/2, height/2);
        this.walls = createWalls();
        initializeMaze();
        this.MAZE = generateMaze(1, 1);
        printMaze();
        this.blocks = createMaze();
        this.coinsMap = createCoins();

    }

    //Getters
    public int getWidth() {return width;}
    public int getHeight() {return height;}
    public Hero getPlayer() {return hero;}
    public List<Wall> getWalls() { return walls; }
    public List<Coin> getCoinsMap() {return coinsMap;}
    public List<Block> getMaze() {return blocks;}

    /**Create walls border around the game map*/
    private List<Wall> createWalls(){
        List<Wall> walls = new ArrayList<>();

        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 3));
            walls.add(new Wall(c, height - 1));
        }

        for (int r = 4; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }

        return walls;
    }

    private List<Block> createMaze() {
        ArrayList<Block> blocks = new ArrayList<>();
        for(int i = 0; i < HEIGHT; i++){
            for(int j = 0; j < WIDTH; j++){
                if(MAZE[i][j] == 'x')
                    blocks.add(new Block(i + 1, j + 4));
                else if(MAZE[i][j] == ' ')
                    Freeindexs.add(new Pair<>(i + 1, j + 4));
            }
        }
        return blocks;
    }

    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        int key;
        for (int i = 0; i < 2; i++) {
            key = random.nextInt(Freeindexs.size() - 1);
            coins.add(new Coin(Freeindexs.get(key).getFirst(), Freeindexs.get(key).getSecond()));
        }
        return coins;
    }
    /**
     * This method checks if hero can move
     */
    private boolean heroCanMove(Position position){
        if (position.getX() < 0 || position.getX() > width - 1) return false;
        if (position.getY() < 0 || position.getY() > height - 1) return false;
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position)) return false;
        }
        for (Block block : blocks) {
            if (block.getPosition().equals(position)) return false;
        }
        return true;
    }

    /**moves the hero*/
    public void movePlayer(Position position){
        if (heroCanMove(position)){
            hero.setPosition(position);
        }
    }
    public int getGameEndCode(){return END;}


    /**Adds a coin to hero*/
    private void heroAddCoin(){
        for (Coin coin: coinsMap) {
            if(hero.getPosition().equals(coin.getPosition())){
                coinsMap.remove(coin);
                if(coinsMap.isEmpty()) {
                    Random random = new Random();
                    int key = random.nextInt(Freeindexs.size() - 1);
                    coinsMap.add(new Coin(Freeindexs.get(key).getFirst(), Freeindexs.get(key).getSecond()));
                }
                hero.heroAddCoin();
                break;
            }
        }
    }


    /**process key that was detected*/
    public String processCommand(int commandID) {
        switch (commandID) {
            case 0 -> { return "pause"; }
            case 2 -> movePlayer(hero.moveUp());
            case 3 -> movePlayer(hero.moveDown());
            case 4 -> movePlayer(hero.moveLeft());
            case 5 -> movePlayer(hero.moveRight());
        }

        heroAddCoin();

        return "false";
    }

}
