package com.example.myapplicationn;

import android.os.SystemClock;
import android.util.Log;

import com.google.firestore.admin.v1.Index;

import java.util.ArrayList;
import java.util.Collections;

public class GameManager implements IGame{

    Board board;

    private Round currentRound=null;

    private int thisPlayer=0;
    IView view;
    ArrayList<Card> deck = new ArrayList<>();
    int currentCard = 0;
    FBwork fBwork;

    String gameId="";
    ArrayList<Integer> Indxs = new ArrayList<>(); //מערך של האינדקסים של הקלפים ואחכ מערבבים אותו
    public GameManager(Board b) //one player - practice
    {
        board = b;
        CreateDeck();
        b.setGameManager(this);
        board.makeTurn(deck.get(currentCard), deck.get(currentCard + 1));
        for (int i = 0; i < deck.size(); i++)
        {
            Indxs.add(i);
        }
        Collections.shuffle(Indxs);
    }



    public GameManager(Board board, String gameID, int player, IView v) //two players
    {
        this.board = board;
        this.view = v;
        this.gameId =gameID;
        fBwork = new FBwork();
        fBwork.setGameManager(this);

        thisPlayer = player;

        //if it is the player that joined - > change status to joined@
        // listen for changes

        fBwork.getRound(gameId);


    }

    public void roundFromFirebase(ArrayList<Integer> d,Round r)
    {
        if(thisPlayer==AppConstants.OTHER)
        {
            fBwork.setGameStatus(this.gameId,AppConstants.JOINED);
        }

        CreateDeck(); //???
        board.setGameManager(this);
        Indxs = d;// ???

        board.makeTurn(deck.get(Indxs.get(currentCard)), deck.get(Indxs.get(currentCard + 1)));

        view.showBoard();

        this.currentRound = r;
        fBwork.handleGame(gameId);

    }


    @Override
    public void userResult(boolean res)
    {
        //if true - check time
        //if false - check other choice

        AppConstants.endTime  = System.currentTimeMillis();

        int totalTime = (int)(AppConstants.endTime-AppConstants.startTime);
        // we have time
        // we have result

        if (res == true)
        {
            // check if host or other
            // set FB with status and time
            if (AppConstants.currentPlayer == AppConstants.HOST)
            {
                currentRound.setTime1(totalTime);
                currentRound.setStatusP1(AppConstants.WIN);
              //  fBwork.setRound(currentRound,gameId);
                fBwork.updateRound(currentRound,gameId,thisPlayer);
            }
            else
            {
                currentRound.setTime2(totalTime);
                currentRound.setStatusP2(AppConstants.WIN);
               // fBwork.setRound(currentRound,gameId);
                fBwork.updateRound(currentRound,gameId, thisPlayer);
            }
        }
        else
        {
            if (AppConstants.currentPlayer == AppConstants.HOST)
            {
                currentRound.setTime1(totalTime);
                currentRound.setStatusP1(AppConstants.LOST);
                //fBwork.setRound(currentRound,gameId);
                fBwork.updateRound(currentRound,gameId, thisPlayer);

            }
            else
            {
                currentRound.setTime2(totalTime);
                currentRound.setStatusP2(AppConstants.LOST);
                //fBwork.setRound(currentRound,gameId);
                fBwork.updateRound(currentRound,gameId, thisPlayer);
            }
        }

        //DEBUG ONLY!!!
       // board.makeTurn(deck.get(Indxs.get(currentCard)), deck.get(Indxs.get(currentCard + 1)));
    }



    public void notifyViewGameStarted()
    {
        view.showCounter();
    }


    public void CreateDeck()
    {
        int n = 7;
        int numOfSymbols = n + 1;
        Card card = new Card();

        for (int i = 1; i <= numOfSymbols; i++)  //build the first card
        {
            card.addImage(i);
        }
        deck.add(card);

        for (int j = 1; j <= n; j++)  //build the next n number of cards
        {
            card = new Card();
            card.addImage(1);

            for (int k = 1; k <= n; k++) {
                card.addImage(n * j + (k + 1));
            }
            deck.add(card);
        }

        for (int i = 1; i <= n; i++) //build the next n² number of cards
        {
            for (int j = 1; j <= n; j++)
            {
                card = new Card();
                card.addImage(i + 1);

                for (int k = 1; k <= n; k++) {
                    card.addImage(n + 2 + n * (k - 1) + (((i - 1) * (k - 1) + j - 1) % n));
                }
                deck.add(card);
            }
        }
        CheckDeck();
    }

    public boolean CheckDeck()
    {
        for (int i = 0; i < deck.size(); i++)
        {
            int[] arr1  = deck.get(i).getCard();
            int index = i;
            int counter = 0;


            for (int j = 0; j < deck.size(); j++)
            {
                if (index != j)
                {
                    int[] arr2  = deck.get(j).getCard();
                    int count = checkArr(arr1, arr2);
                    if (count != 1) {
                        Log.d("Card Check", "CheckDeck: " + i +"," +j);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public int checkArr(int[] arr1, int[] arr2)
    {
        int count = 0;
        for (int i = 0; i < arr1.length; i++)
        {
            for (int k = 0; k < arr2.length; k++)
            {
                if (arr1[i] == arr2[k])
                    count ++;
            }
        }

        return count;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    public int getCurrentCard() {
        return currentCard;
    }

    public void setCurrentCard(int currentCard) {
        this.currentCard = currentCard;
    }

    public ArrayList<Integer> getIndxs() {
        return Indxs;
    }

    public void setIndxs(ArrayList<Integer> indxs) {
        Indxs = indxs;
    }

    public void setGameDeck(ArrayList<Integer> myGameDeck) {
    }

    public void ChangeCards()
    {
        currentCard = currentCard + 2;
        board.makeTurn(deck.get(Indxs.get(currentCard)), deck.get(Indxs.get(currentCard + 1)));
    }
}
