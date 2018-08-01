
    // NAME: Tran Le
    // JAV1 - 1808
    // FILE NAME: MainActivity.java

package com.sunny.android.letran_ce02;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

    public class MainActivity extends AppCompatActivity {

        // Create necessary variables
        static final String TAG = "GUESSNUM";
        private final Random rnd = new Random();
        private ArrayList<Integer> randomNums = new ArrayList<>();
        private static final int MAX_NUM_VALUE = 10;
        private static final int EMPTY = -1;
        private int numOfTurns = 4;
        private Toast tst_Message = null;
        private final int[] mColorThesholds = {9,4};
        private final int[] textIDs = {R.id.txt_FirstNum, R.id.txt_SecondNum, R.id.txt_ThirdNum, R.id.txt_FourthNum};
        private boolean isWin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        randomizeNum();
        findViewById(R.id.btn_SubmitGuess).setOnClickListener(submitTapped);
    }

    // Create click listener for the Submit button
    private View.OnClickListener submitTapped = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (numOfTurns > 0) {
                numOfTurns -= 1;
            }

            if (tst_Message != null) {
                tst_Message.cancel();
            }

            EditText firstNum = (EditText)findViewById(R.id.txt_FirstNum);
            String first = firstNum.getText().toString();
            EditText secNum = (EditText)findViewById(R.id.txt_SecondNum);
            String second = firstNum.getText().toString();
            EditText thirdNum = (EditText)findViewById(R.id.txt_ThirdNum);
            String third = firstNum.getText().toString();
            EditText fourthNum = (EditText)findViewById(R.id.txt_FourthNum);
            String fourth = firstNum.getText().toString();

            if (!first.isEmpty() && !second.isEmpty() && !third.isEmpty() && !fourth.isEmpty()) {
                String remainTurns = "";

                if (numOfTurns > 0) {
                    checkTexts();

                    if (numOfTurns > 1) {
                        remainTurns = "You have " + numOfTurns + " turns left!";
                    } else if (numOfTurns > 0 && numOfTurns < 2) {
                        remainTurns = "You have " + numOfTurns + " turn left!";
                    }

                    tst_Message = Toast.makeText(MainActivity.this, remainTurns, Toast.LENGTH_SHORT);

                    if (isWin) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle(R.string.win);
                        builder.setMessage(R.string.winMessage);
                        builder.setIcon(R.drawable.win_icon);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle(R.string.lose);
                        builder.setMessage(R.string.loseMessage);
                        builder.setIcon(R.drawable.lose_icon);
                        builder.setPositiveButton(R.string.dialog_pos_btn, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                reset();
                            }
                        });

                        builder.show();
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(R.string.lose);
                    builder.setMessage(R.string.loseMessage);
                    builder.setIcon(R.drawable.lose_icon);
                    builder.setPositiveButton(R.string.dialog_pos_btn, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            reset();
                        }
                    });

                    builder.show();
                }
            } else {
                tst_Message = Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_SHORT);
            }

            tst_Message.show();
        }
    };

    // Function to compare whatever input in the edit text to the random numbers
    private void checkTexts() {
        int colorSelection = EMPTY;

        EditText firstNum = (EditText)findViewById(R.id.txt_FirstNum);
        Integer first = Integer.parseInt(firstNum.getText().toString());
        EditText secNum = (EditText)findViewById(R.id.txt_SecondNum);
        Integer second = Integer.parseInt(secNum.getText().toString());
        EditText thirdNum = (EditText)findViewById(R.id.txt_ThirdNum);
        Integer third = Integer.parseInt(thirdNum.getText().toString());
        EditText fourthNum = (EditText)findViewById(R.id.txt_FourthNum);
        Integer fourth = Integer.parseInt(fourthNum.getText().toString());

        EditText[] allInputTextField = {firstNum, secNum, thirdNum, fourthNum};
        Integer[] inputNum = {first, second, third, fourth};
        ArrayList<Integer> colorIds = new ArrayList<>();

        if (randomNums.size() > 0) {
            for (int i = 0; i < randomNums.size(); i++) {
                Log.i(TAG, "randomNum: "+randomNums.get(i) + inputNum[i]);
            }
            for (int i = 0; i < randomNums.size(); i++) {
                int colorID = EMPTY;

                if (inputNum[i] == randomNums.get(i)) {
                    Log.i(TAG, "checkTexts: green");
                    colorID = R.color.green;
                } else if (inputNum[i] > 4) {
                    Log.i(TAG, "checkTexts: red");
                    colorID = R.color.red;
                } else {
                    Log.i(TAG, "checkTexts: blue");
                    colorID = R.color.blue;
                }
                allInputTextField[i].setTextColor(getResources().getColor(colorID));
            }

            if (inputNum[0] == randomNums.get(0) && inputNum[1] == randomNums.get(1) && inputNum[2] == randomNums.get(2) && inputNum[3] == randomNums.get(3)) {
                isWin = true;
            }
        }
    }

    // Function to randomly get 4 number
    private void randomizeNum() {
        randomNums.clear();
        for (int i = 0; i < 4; i++) {
            Integer rndNum = rnd.nextInt(10);
            randomNums.add(rndNum);
        }
    }

    // Function to reset everything when user is done playing or loses
    private void reset() {
        randomizeNum();
        numOfTurns = 4;

        EditText firstNum = (EditText)findViewById(R.id.txt_FirstNum);
        firstNum.setText("");
        EditText secNum = (EditText)findViewById(R.id.txt_SecondNum);
        secNum.setText("");
        EditText thirdNum = (EditText)findViewById(R.id.txt_ThirdNum);
        thirdNum.setText("");
        EditText fourthNum = (EditText)findViewById(R.id.txt_FourthNum);
        fourthNum.setText("");
    }
}
