package com.example.myapplicationn;

import static com.example.myapplicationn.AppConstants.cardCounter;
import static com.example.myapplicationn.AppConstants.currentPlayer;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.util.Listener;

import java.util.ArrayList;

public class FBwork
{

    private GameManager gameManager;
    private boolean listening = false;


    FirebaseFirestore fb = FirebaseFirestore.getInstance();


        public void getRound(String ref) //מביא את המידע על הסיבוב הנוכחי מהFireBase
        // ומעביר אותו לGameManager. מאפשר לGameManager לדעת את הקלפים הנוכחיים בסיבוב המשחק
        {
            fb.collection("Rounds").document(ref).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Round r = documentSnapshot.toObject(Round.class);
                    // let game manager know the round.
                    gameManager.roundFromFirebase(r.getMyGameDeck(),r);
                }
            });
        }



        private boolean gameOver = false;
        public void handleGame(String gameID) //הפעולה מאזינה לשינויים במשחק. כל שינוי או לחיצה שקורית בלוח מובילים לפעולה הזאת.
        // הפעולה בודקת איזה שינוי היה:
    //אם זה שינוי סטטוס היא מגיבה בהתאם – מתחילה משחק/ מסיימת משחק/ מודיעה על ניצחון.
   // במידה ותשובת השחקן נכונה או שגויה היא מוסיפה ומורידה לו מהקלפים.
    {
            if(!listening) {
                listening = true;

                //ניגש לקולקשן הספציפי של ראונדס ומביא את הדוקיומנט הספציפי לפי המזהה הייחודי שלו
                fb.collection("Rounds").document(gameID).addSnapshotListener((Activity)(gameManager.getView()),new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        //בדיקה שלא מתעסקים עם null
                        if (value != null && value.exists()) {

                            // value holds Round object
                            // להמיר את אובגקט לראונד
                            Round round = value.toObject(Round.class);

                            //אם המשחק נוצר - אין מה לעשות עדיין, להמשיך להמתין לשחקן שיצטרף
                            if (round.getStatus() == AppConstants.CREATED)
                            {
                                return;
                            }


                            // אם השחקן השני הצטרף - מעדכנים שהמשחק מתחיל
                            // (שינוי סטטוס, הקאונטר של הפופאפ מתחיל לספור חמש שניות
                            if (round.getStatus() == AppConstants.JOINED) {
                                gameManager.notifyViewGameStarted();
                                if(AppConstants.currentPlayer==AppConstants.HOST)
                                // timer for 5 seconds
                                    setGameStatus(gameID,AppConstants.STARTED);

                                return;
                            }

                            //מאזין אם אחד השחקנים לחץ על אחד הקלפים
                            if (round.getStatusP1() != 0) {
                                if (round.getStatusP1() == 1) {
                                    // ההוסט  ניצח בסיבוב הזה , עולה לו מספר הקלפים - לאוטר יורד
                                    if (currentPlayer == AppConstants.HOST)
                                        cardCounter++;
                                    else

                                        cardCounter--;
                                }
                                else
                                {
                                    // ההוסט  הפסיד בסיבוב הזה , יורד לו מספר הקלפים - לאוטר עולה
                                    if (currentPlayer == AppConstants.HOST)
                                        cardCounter--;
                                    else
                                        cardCounter++;
                                }
                            } else if (round.getStatusP2() != 0) {
                                if (round.getStatusP2() == 1) {
                                    //האוטר  ניצח בסיבוב הזה , עולה לו מספר הקלפים - להוסט יורד
                                    if (currentPlayer == AppConstants.OTHER)
                                        cardCounter++;
                                    else
                                        cardCounter--;
                                } else {
                                    //האוטר  הפסיד בסיבוב הזה , יורד לו מספר הקלפים - להוסט עולה
                                    if (currentPlayer == AppConstants.OTHER)
                                        cardCounter--;
                                    else
                                        cardCounter++;
                                }
                            }

                            //בדיקה האם לאחד השחקנים נגמרו הקלפים ואם כן לשנות סטטוס ולעצור משחק
                            if (cardCounter == 0)
                            {
                                if (AppConstants.currentPlayer == AppConstants.HOST)
                                    setGameStatus(gameID,AppConstants.WINP2);
                                else
                                    setGameStatus(gameID,AppConstants.WINP1);
                            }

                            // check game over
                            // סטטוס 5 - אוטר ניצח
                            // סטטוס 4   - הוסט ניצח
                            if((round.getStatus() == 5 || round.getStatus() == 4)&&!gameOver)
                            {
                                gameManager.gameOver(cardCounter, round.getStatus());
                                gameOver=true;

                            }

                            if(round.getStatusP1()==0&& round.getStatusP2()==0)
                                return;

                            //כדי שהשחקנים יתחילו עם אותם קלפים ולא יחליפו אותם כל פעם כשיש שינוי
                            if (round.getStatus() == AppConstants.STARTED && (round.getStatusP1()!=0 || round.getStatusP2()!=0))
                                gameManager.ChangeCards();
                        }
                    }
                });
            }
        }


        // מעדכן סטטוס
        public void setGameStatus(String ref,int status)
        {
            fb.collection("Rounds").document(ref).update("status",status);
        }

    public void setGameManager(GameManager gameManager) {

        this.gameManager = gameManager;
        cardCounter=10;
    }
    /*
    public void setRound(Round currentRound, String gameId) {

        fb.collection("Rounds").document(gameId).set(currentRound);
    }
*/

    //מעדכן את סטטוס השחקנים בFireBase
    public void updateRound(Round currentRound, String gameId, int player)
    {
        if (player == AppConstants.HOST)
        {
            //fb.collection("Rounds").document(gameId).update("time1",currentRound.time1);
            fb.collection("Rounds").document(gameId).update("statusP1",currentRound.statusP1);
        }
        else
        {
            //fb.collection("Rounds").document(gameId).update("time2",currentRound.time2);
            fb.collection("Rounds").document(gameId).update("statusP2",currentRound.statusP2);
        }
    }


}
