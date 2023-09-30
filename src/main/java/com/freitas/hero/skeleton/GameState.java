package com.freitas.hero.skeleton;

import com.freitas.hero.skeleton.elements.*;

import static com.freitas.hero.algorithms.MazeGenerator.*;
import static java.lang.Math.max;
import static java.lang.Math.min;

import com.freitas.hero.dataStructs.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


public class GameState implements StateInterface {
    private final int width; //width of display
    private final int height; //height of display

    private final Hero hero;

    private int time; //total time hero has to complete game
    private int gameEndCode;

    private final List<Wall> walls;
    private final List<Block> blocks;
    private final List<Enemy> enemies;
    private final List<Coin> coinsMap;
    //private final List<BiggerBombs> biggerBombsMap;
    //private final List<ExtraLife> extraLivesMap;
    private final List<Bomb> bombs;
    private char[][] MAZE;

    ArrayList<Pair<Integer, Integer>> Freeindexs = new ArrayList<>();

    public GameState(int width, int height){

        this.width = width;
        this.height = height;

        this.time = 360;
        this.gameEndCode = 0; //game not ended

        hero = new Hero(width/2, height/2);
        initializeMaze();
        this.MAZE = generateMazeBFS(1, 1);
        printMaze();
        this.walls = createWalls();
        this.blocks = createBlock();
        this.enemies = createEnemies();
        this.coinsMap = createCoins();
        //this.biggerBombsMap = new ArrayList<>();
        //this.extraLivesMap = new ArrayList<>();
        this.bombs = new ArrayList<>();
    }

    //Getters
    public int getWidth() {return width;}
    public int getHeight() {return height;}
    public Hero getHero() {return hero;}
    public int getTime() {return time;}
    public List<Wall> getWalls() { return walls; }
    public List<Block> getBlocks() {return blocks;}
    public List<Bomb> getBombs() {return bombs;}
    //public List<BiggerBombs> getBiggerBombsMap() {return biggerBombsMap;}
    public List<Coin> getCoinsMap() {return coinsMap;}
    public List<Enemy> getEnemies() {return enemies;}
    //public List<ExtraLife> getExtraLivesMap() {return extraLivesMap;}

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
    private List<Coin> createCoins() {
        ArrayList<Coin> coins = new ArrayList<>();
        Random random = new Random();
        int key;
        for(int i = 0; i < 4; i++){
            key = random.nextInt(Freeindexs.size() - 1);
            coins.add( new Coin(Freeindexs.get(key).getFirst(), Freeindexs.get(key).getSecond()));
            Freeindexs.remove(key);
        }
        return coins;
    }
    /**
     * This method creates blocks in random places on map, with spawn space in mind (doesn't generate there)
     */
    private List<Block> createBlock(){
        ArrayList<Block> blocks = new ArrayList<>();
        for(int i = 0; i < HEIGHT; i++){
            for(int j = 0; j < WIDTH; j++){
                if(MAZE[i][j] == 'x')
                    blocks.add(new Block(i + 1, j + 4));
                else if(MAZE[i][j] == ' '){
                    Freeindexs.add(new Pair<>(i + 1, j + 4));
                }
            }
        }
        System.out.println(blocks.size());
        System.out.println(HEIGHT*WIDTH);
        return blocks;
    }

    /**
     * This method creates enemies in random spots on map, with spawn space in mind (doesn't generate there)
     */
    private List<Enemy> createEnemies() {
        Random random = new Random();
        ArrayList<Enemy> enemies = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int xPos = random.nextInt(width - 2) + 1;
            int yPos = random.nextInt(height -5) + 4;
            //this ensures enemies are not created on top of blocks
            boolean createEnemy = blockInPosition(new Position(xPos,yPos));
            //this ensures that enemies are not create on top of the hero or block him in the beginning
            if(!createEnemy && !(xPos > width / 2 - 2 && xPos < width / 2 + 2 && yPos > height / 2 - 2 && yPos < height / 2 + 2) ) {
                enemies.add(new Enemy(xPos, yPos));
            } else i--;
        }
        return enemies;
    }

    /**
     * This method is used to check if a position is on top of a block
     * @param position Position we want to test
     * @return returns true if there is a block in the position, false if otherwise
     */
    private boolean blockInPosition(Position position){
        for(Block block: blocks)
            if (Objects.equals(block.getPosition(), position)) {
                return true;
            }
        return false;
    }

    public void timeSubtract(){time--;}

    /**realize functions of the game automatically (enemies movements and detections)*/
    public void gameTick(){
        //detonate bombs and erase them
        for (int i = 0;i < bombs.size();i++) {

            bombs.get(i).decreaseTime();

            //see if bombs destroyed stuff
            if(bombs.get(i).getTimeToDetonate()==0){
                Position bombPosition = bombs.get(i).getPosition();
                int bombX = bombPosition.getX();
                int bombY = bombPosition.getY();
                int bombSize = bombs.get(i).getExplosionSize();
                checkPlayerDiedBomb(bombX,bombY,bombSize);
                elementDestroyed(coinsMap,bombX,bombY,bombSize);
                //elementDestroyed(biggerBombsMap,bombX,bombY,bombSize);
                //elementDestroyed(extraLivesMap,bombX,bombY,bombSize);
                elementDestroyed(enemies,bombX,bombY,bombSize);
                blockDestroyed(bombX,bombY,bombSize);
            }

            if(bombs.get(i).getTimeToDetonate()<-10){
                bombs.remove(i);
                i--;
            }
        }

        //see if hero died by enemy
        checkPlayerDiedEnemy();

        //see if hero won (no enemies left)
        checkPlayerWon();

        //game ends if time ends
        if(time<=0) {gameEndCode = 1;}

    }

    /**checks if other objects was destroyed*/
    private <T extends Element> void elementDestroyed(List<T> destroy, int bombX, int bombY, int bombSize){
        for(int i=0;i<destroy.size();i++){
            Position elemPosition = destroy.get(i).getPosition();
            int elemX = elemPosition.getX();
            int elemY = elemPosition.getY();


            if(elemX==bombX && (elemY <= bombY + 2 && elemY >= bombY - 2)) {
                destroy.remove(i);
                i--;

                continue;
            }
            if(elemY==bombY && (elemX <= bombX + 2 && elemX >= bombX - 2)){
                destroy.remove(i);
                i--;
            }

        }
    }

    /**checks if a block was destroyed*/
    private void blockDestroyed(int bombX, int bombY, int bombSize){
        for(int j=0;j<blocks.size();j++){
            Position blockPosition = blocks.get(j).getPosition();
            int blockX = blockPosition.getX();
            int blockY = blockPosition.getY();
            System.out.println(blockX + "->>" + blockY);
            //System.out.println(bombX + " xxx " + bombY);

            if(blockX==bombX && (blockY <= bombY+2 && blockY >= bombY-2)) {
                blockDrop(blockPosition);
                blocks.remove(j);
                j--;
                continue;
            }
            if(blockY==bombY && (blockX <= bombX+2 && blockX>=bombX-2)){
                blockDrop(blockPosition);
                blocks.remove(j);
                j--;
            }
        }
    }

    /**Code to perform loot box drop when a block is broken*/
    public void blockDrop(Position position){
        if(new Random().nextInt(100) <=8){
            coinsMap.add(new Coin(position.getX(), position.getY()));
        }
        /*
        else if(new Random().nextInt(100) <=3){
            biggerBombsMap.add(new BiggerBombs(position.getX(), position.getY()));
        }
        else if(new Random().nextInt(100) <=2){
            extraLivesMap.add(new ExtraLife(position.getX(), position.getY()));
        }
        */
    }

    /**calls function to subtract from hero bomb placement cool down*/
    public void heroSubtractBombCoolDown(){
        if(hero.getBombPlaceCoolDown()>0){
            hero.BombPlaceCoolDownSubtract();
        }
    }

    /**calls function to subtract from hero bomb placement cool down*/
    public void heroSubtractMoveCoolDown(){
        if(hero.getMovementCoolDown()>0){
            hero.MovementCoolDownSubtract();
        }
    }


    /**
     * This method checks if hero can move (it is also used to see if enemies can move)
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
        for (Bomb bomb : bombs) {
            if (bomb.getPosition().equals(position)) return false;
        }
        return true;
    }

    /**moves the hero*/
    public void movePlayer(Position position){
        if (heroCanMove(position)){
            if(hero.getMovementCoolDown()<=0) {
                hero.MovementCoolDownBegin();
                hero.setPosition(position);
            }
        }
    }

    /**moves all enemies at random on the map (only if it can move)*/
    public void moveEnemies() {
        for (Enemy enemy : enemies) {
            Position enemyPosition = enemy.move();
            if (heroCanMove(enemyPosition)) enemy.setPosition(enemyPosition);
        }
    }

    /**Checks if hero has won the game (eliminated all the enemies)*/
    private void checkPlayerWon(){
        if(enemies.isEmpty()){
            System.out.println("You won!");
            gameEndCode = 2;
        }
    }

    /**Checks if hero has died*/
    private void heroDied() {
        if (hero.getLives() > 0) {
            hero.setPosition(new Position(width / 2, height / 2));
            //hero.heroRemoveLife();
        } else if (hero.getLives() == 0) {
            System.out.println("You died!");
            gameEndCode = 1;
        }
    }

    /**checks if hero has bombed itself to oblivion*/
    private void checkPlayerDiedBomb(int bombX, int bombY, int bombSize){
        Position heroPos = hero.getPosition();
        int elemX = heroPos.getX();
        int elemY = heroPos.getY();

        if(elemX==bombX && (elemY <= bombY+bombSize && elemY >= bombY-bombSize)) {
            heroDied();
        }
        if(elemY==bombY && (elemX <= bombX+bombSize && elemX>=bombX-bombSize)){
            heroDied();
        }
    }

    /**returns when game has ended*/
    public int getGameEndCode(){return gameEndCode;}

    /**Adds a coin to hero*/
    private void heroAddCoin(){
        for (Coin coin: coinsMap) {
            if(hero.getPosition().equals(coin.getPosition())){
                coinsMap.remove(coin);
                Random random = new Random();
                int key = random.nextInt(Freeindexs.size() - 1);
                coinsMap.add(new Coin(Freeindexs.get(key).getFirst(), Freeindexs.get(key).getSecond()));
                hero.heroAddCoin();
                break;
            }
        }
    }
/*
    /**Makes hero bomb a bit bigger*/

    /*
    private void heroBiggerBombs(){
        for (BiggerBombs biggerBombs: biggerBombsMap) {
            if(hero.getPosition().equals(biggerBombs.getPosition())){
                biggerBombsMap.remove(biggerBombs);
                hero.addBombSize();
                break;
            }
        }
    }
    /**Adds extra life to hero*/
    /*
    private void heroExtraLife(){
        for (ExtraLife extraLife: extraLivesMap) {
            if(hero.getPosition().equals(extraLife.getPosition())){
                extraLivesMap.remove(extraLife);
                hero.heroAddLife();
                break;
            }
        }
    }
    /*

    /**adds bomb to bomb list*/
    private void addBomb(Position position, int size){
        this.bombs.add(new Bomb(position.getX(), position.getY(), size));
    }

    /**checks if an enemy has gotten to a hero and killed it*/
    private void checkPlayerDiedEnemy(){
        for (Enemy enemy: enemies)
            if (hero.getPosition().equals(enemy.getPosition())) {
                heroDied();
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
            case 6 -> { if (hero.getBombPlaceCoolDown()==0) addBomb(hero.placeBomb(),hero.getBombSize()); }
        }

        heroAddCoin();
        //heroBiggerBombs();
        //heroExtraLife();

        return "false";
    }

}