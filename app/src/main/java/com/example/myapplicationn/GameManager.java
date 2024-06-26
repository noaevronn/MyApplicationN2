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

    //קונסטרקטור שמוגדר עבור משחק תרגול.
    // השתמשתי בו במשחק המרובה משתתפים גם כדי ליצור את החדר משחק כדי שלא תהיה לי שגיאת null
    // ואחר כך אתחלתי מחדש עם הקונסטרקטור השני והנתונים הנכונים של המשחק
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


    //קונסטרקטור שמוגדר עבור משחק מרובה משתתפים. מאתחל את הBoard, IView gameId, FBwork,  ומגדיר את השחקן הנוכחי
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

    //מחזיר את תצוגת המשחק
    public IView getView()
    {
        return this.view;
    }


    //הפעולה מעדכנת את החפיסה והסיבוב הנוכחי מהנתונים שהתקבלו מהFireBase .
    // הפעולה מעדכנת את מצב המשחק ומתחיל סיבוב חדש. מוצג הלוח המעודכן. זהו תחילתו של משחק.
    public void roundFromFirebase(ArrayList<Integer> d,Round r)
    {
        //עדכון סטטוס - הצטרף שחקן 2
        if(thisPlayer==AppConstants.OTHER)
        {
            fBwork.setGameStatus(this.gameId,AppConstants.JOINED);
        }

        //יוצרים את חבילת הקלפים של המשחק
        CreateDeck();
        board.setGameManager(this);

        // נשמר מערך הקלפים כאינדקסים
        Indxs = d;

        //הצגה של שני קלפים ראשונים
        board.makeTurn(deck.get(Indxs.get(currentCard)), deck.get(Indxs.get(currentCard + 1)));

        // הצגת הלוח
        view.showBoard();

        this.currentRound = r;
        fBwork.handleGame(gameId);

    }


    //הפעולה בודקת את תשובת השחקן ומעדכנת בFireBase
    // את הסטטוס שלו (צדק או טעה)  ובהתאם לתשובה הזאת המשחק ממשיך
    // ומתעדכנים בהמשך גם מספרי הקלפים של השחקנים.
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
            // set FB with status
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

    }


// מתחיל ספירה לאחור של הפופאפ כי התקבלה הודעה שהמשחק מתחיל
    public void notifyViewGameStarted()
    {
        view.showCounter();
    }

// הפעולה יוצרת את חפיסת הקלפים למשחק.
// הקלפים נוצרים ומתווספים לחבילה.
// בתום הפעולה יש קריאה לפעולה הבודקת את תקינות החפיסה.
    public void CreateDeck()
    {
        int n = 7; //מספר הסמלים על כל קלף מלבד הזהה (הראשון)
        int numOfSymbols = n + 1; // מספר הסמלים על כל קלף
        Card card = new Card();

        //בניית הקלף הראשון והוספה שלו לחבילה
        for (int i = 1; i <= numOfSymbols; i++)  //build the first card
        {
            card.addImage(i);
        }
        deck.add(card);

        // בניית הn קלפים הבאים (7)
        //כל אחד מהקלפים הבאים מתחיל עם הסמל הראשון (1)
        // מוסיף n סמלים שונים לכל קלף. לדוגמה, הקלף השני יכיל את הסמלים 1, 8, 9, 10, 11, 12, 13
        for (int j = 1; j <= n; j++)  //build the next n number of cards
        {
            card = new Card();
            card.addImage(1);

            for (int k = 1; k <= n; k++) {
                card.addImage(n * j + (k + 1));
            }
            deck.add(card);
        }

        //בניית ה-n² קלפים הבאים

        for (int i = 1; i <= n; i++) // הלולאה החיצונית רצה מ-1 עד n
           //  ומייצגת את קבוצת הקלפים הראשונה שנוצרת
        {
            for (int j = 1; j <= n; j++) //n הלולאה הפנימית רצה גם היא מ-1 עד
                //, ומייצגת את קבוצת הקלפים השנייה שנוצרת
            {
                //יוצר קלף חדש ומוסיף את הסמל הראשון שהוא i+1
                card = new Card();
                card.addImage(i + 1);

                //הלולאה הזו רצה מ-1 עד n (כולל) ומוסיפה עוד n סמלים חדשים לקלף
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

    //מחליף קלפים
    //מאפס את הסטטוסים והזמנים של השחקנים בפיירבייס
    public void ChangeCards()
    {
        if (AppConstants.LENGTH - 2 == currentCard)
        {
            currentCard = 0;
            board.makeTurn(deck.get(Indxs.get(AppConstants.LENGTH)), deck.get(Indxs.get(currentCard)));
        }
        else if (AppConstants.LENGTH - 1 == currentCard)
        {
            board.makeTurn(deck.get(Indxs.get(currentCard)), deck.get(Indxs.get(0)));
            currentCard = 1;
        }
        else
        {
            currentCard = currentCard + 2;
            board.makeTurn(deck.get(Indxs.get(currentCard)), deck.get(Indxs.get(currentCard + 1)));
        }
        currentRound.setTime1(0);
        currentRound.setStatusP1(AppConstants.WAIT);
        currentRound.setTime2(0);
        currentRound.setStatusP2(AppConstants.WAIT);
        fBwork.updateRound(currentRound,gameId, thisPlayer);
    }


    public ArrayList<Integer> getIndxs() {
        return Indxs;
    }

    public void gameOver(int cardCounter, int status) {


        view.displayGameOver(cardCounter, status);

    }
}
