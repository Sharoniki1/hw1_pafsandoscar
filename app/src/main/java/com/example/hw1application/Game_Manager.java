package com.example.hw1application;

public class Game_Manager {

    private int life;
    private int number_of_rows;
    private int number_of_columns;
    private int oscarIndex;
    private int[] pafsIndex;


    public Game_Manager(int life, int number_of_rows, int number_of_columns){
        this.life= life;
        this.number_of_rows = number_of_rows;
        this.number_of_columns = number_of_columns;
        this.oscarIndex = number_of_columns/2;
        this.pafsIndex = new int[number_of_columns];

        for(int i=0; i<number_of_columns;i++)
            pafsIndex[i] = (-i-3);

    }

    public int getLife() {
        return life;
    }

    public int[] getPafsIndex(){
        return pafsIndex;
    }

    public int getOscarIndex(){
        return oscarIndex;
    }

    public int getNumber_of_columns(){
        return number_of_columns;
    }

    public boolean isInArray(int index) {
        return index>=0 && index< number_of_rows;
    }

    public void moveOscars(boolean direction) {
        if(oscarIndex < number_of_columns -1 && direction)
            oscarIndex++;

        if(oscarIndex > 0 && !direction)
            oscarIndex--;
    }

    public void movePafs() {

        for(int i = 0; i< number_of_columns; i++){
            if(pafsIndex[i] < number_of_rows)
                pafsIndex[i]++;
            else
                pafsIndex[i] = (-i-2);
        }

    }

    public boolean isCrash() {
        if(pafsIndex[oscarIndex] == number_of_rows){
            life--;
            return true;
        }
        return false;
    }

    public boolean isGameOver() {
        return life==0;
    }



}
