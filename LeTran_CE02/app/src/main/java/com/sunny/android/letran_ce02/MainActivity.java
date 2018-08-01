
    // NAME: Tran Le
    // JAV1 - 1808
    // FILE NAME: MainActivity.java

package com.sunny.android.letran_ce02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
            EditText secNum = (EditText)findViewById(R.id.txt_FirstNum);
            String second = firstNum.getText().toString();
            EditText thirdNum = (EditText)findViewById(R.id.txt_FirstNum);
            String third = firstNum.getText().toString();
            EditText fourthNum = (EditText)findViewById(R.id.txt_FirstNum);
            String fourth = firstNum.getText().toString();

            if (!first.isEmpty() && !second.isEmpty() && !third.isEmpty() && !fourth.isEmpty()) {
                String remainTurns = "";
                if (numOfTurns > 1) {
                    remainTurns = numOfTurns + "turns remaining";
                } else {
                    remainTurns = numOfTurns + "turn remaining";
                }
                tst_Message = Toast.makeText(MainActivity.this, remainTurns, Toast.LENGTH_SHORT);
            } else {
                tst_Message = Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_SHORT);
            }

            tst_Message.show();
        }
    };

    // Function to randomly get 4 number
    private void randomizeNum() {
        randomNums.clear();
        for (int i = 0; i < 4; i++) {
            Integer rndNum = rnd.nextInt(10);
            randomNums.add(rndNum);
        }
    }
}
