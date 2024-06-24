package com.example.myapplicationn;

public class User
{
    private int wins;
    private int losts;
    private int numGames;
    private String name;
    //email

    public User(String name)
    {
        this.wins =0;
        this.losts = 0;
        this.numGames = 0;
        this.name = name;
    }
    public User(){
        this.wins =0;
        this.losts = 0;
        this.numGames = 0;
        this.name = "";
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosts() {
        return losts;
    }

    public void setLosts(int losts) {
        this.losts = losts;
    }

    public int getNumGames() {
        return numGames;
    }

    public void setNumGames(int numGames) {
        this.numGames = numGames;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
