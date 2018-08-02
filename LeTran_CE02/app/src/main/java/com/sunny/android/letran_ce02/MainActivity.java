
    // NAME: Tran Le
    // JAV1 - 1808
    // FILE NAME: MainActivity.java

package com.sunny.android.letran_ce02;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Selection;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;
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
        private final int[] textIDs = {R.id.txt_FirstNum, R.id.txt_SecondNum, R.id.txt_ThirdNum, R.id.txt_FourthNum};
        private ArrayList<Integer> inputNum = new ArrayList<>();
        private int originalColor = -1;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            originalColor = ((EditText)findViewById(R.id.txt_FirstNum)).getCurrentTextColor();
            randomizeNum();
            findViewById(R.id.btn_SubmitGuess).setOnClickListener(submitTapped);
        }

        // Create click listener for the Submit button
        private View.OnClickListener submitTapped = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tst_Message != null) {
                    tst_Message.cancel();
                }

                EditText firstNum = (EditText) findViewById(R.id.txt_FirstNum);
                String first = firstNum.getText().toString();
                EditText secNum = (EditText) findViewById(R.id.txt_SecondNum);
                String second = secNum.getText().toString();
                EditText thirdNum = (EditText) findViewById(R.id.txt_ThirdNum);
                String third = thirdNum.getText().toString();
                EditText fourthNum = (EditText) findViewById(R.id.txt_FourthNum);
                String fourth = fourthNum.getText().toString();

                if (!first.isEmpty() && !second.isEmpty() && !third.isEmpty() && !fourth.isEmpty()) {

                    checkTexts();

                    if (inputNum.size() == 4) {
                        if (numOfTurns > 0) {
                            numOfTurns -= 1;

                            String remainTurns = "";

                            if (numOfTurns > 1) {
                                remainTurns = "You have " + numOfTurns + " turns left!";
                            } else if (numOfTurns > 0 && numOfTurns < 2) {
                                remainTurns = "You have " + numOfTurns + " turn left!";
                            }

                            tst_Message = Toast.makeText(MainActivity.this, remainTurns, Toast.LENGTH_SHORT);
                        }




                        if ((inputNum.get(0) == randomNums.get(0)) && (inputNum.get(1) == randomNums.get(1)) && (inputNum.get(2) == randomNums.get(2)) && (inputNum.get(3) == randomNums.get(3))) {
                            alertDialog(R.string.win, R.string.winMessage, R.drawable.win_icon);
                        } else if (numOfTurns == 0){
                            alertDialog(R.string.lose, R.string.loseMessage, R.drawable.lose_icon);
                        }

                    }
                } else {
                    tst_Message = Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_SHORT);
                }

                if (tst_Message != null) {
                    tst_Message.show();
                }
            }
        };

        private void alertDialog(int title, int message, int icon) {
            tst_Message.cancel();
            tst_Message = null;
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(title);
            builder.setMessage(message);
            builder.setIcon(icon);
            builder.setPositiveButton(R.string.dialog_pos_btn, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    reset();
                }
            });
            builder.show();
        }
        // Function to compare whatever input in the edit text to the random numbers
        private void checkTexts() {
            int colorSelection = EMPTY;

            EditText firstText = (EditText) findViewById(R.id.txt_FirstNum);
            EditText secText = (EditText) findViewById(R.id.txt_SecondNum);
            EditText thirdText = (EditText) findViewById(R.id.txt_ThirdNum);
            EditText fourthText = (EditText) findViewById(R.id.txt_FourthNum);

            EditText[] allInputTextField = {firstText, secText, thirdText, fourthText};
            inputNum.clear();

            try {
                for (int i = 0; i < 4; i++) {
                    Integer number = Integer.parseInt(allInputTextField[i].getText().toString());
                    inputNum.add(number);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (randomNums.size() > 0 && inputNum.size() == 4) {
                for (int i = 0; i < randomNums.size(); i++) {
                    Log.i(TAG, "randomNum: " + randomNums.get(i));

                }
                for (int i = 0; i < randomNums.size(); i++) {
                    int colorID = EMPTY;

                    if (inputNum.get(i) == randomNums.get(i)) {
                        colorID = R.color.green;
                    } else if (inputNum.get(i) > 4) {
                        colorID = R.color.red;
                    } else {
                        colorID = R.color.blue;
                    }
                    allInputTextField[i].setTextColor(getResources().getColor(colorID));
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


            EditText firstNum = (EditText) findViewById(R.id.txt_FirstNum);
            firstNum.setText("");
            firstNum.setTextColor(originalColor);
            EditText secNum = (EditText) findViewById(R.id.txt_SecondNum);
            secNum.setText("");
            secNum.setTextColor(originalColor);
            EditText thirdNum = (EditText) findViewById(R.id.txt_ThirdNum);
            thirdNum.setText("");
            thirdNum.setTextColor(originalColor);
            EditText fourthNum = (EditText) findViewById(R.id.txt_FourthNum);
            fourthNum.setText("");
            fourthNum.setTextColor(originalColor);

            Selection.setSelection(firstNum.getText(), firstNum.getSelectionStart());
            firstNum.requestFocus();
        }
    }
