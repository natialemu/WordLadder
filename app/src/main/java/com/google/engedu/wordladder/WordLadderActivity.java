package com.google.engedu.wordladder;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.engedu.worldladder.R;

import org.w3c.dom.Text;

import java.util.Collections;

public class WordLadderActivity extends AppCompatActivity {



    private final String PATH_KEY = "path";
    private final String STARTING_WORD_KEY = "firstWord";
    private final String ENDING_WORD_KEY = "endingWord";

    // an array of text views
    // the array of paths
    //the start word
    //the final word
    private String[] path;
    private String startWord;
    private String endWord;

    private EditText[] editTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_ladder);


        Intent intent = getIntent();
        path = intent.getStringArrayExtra(PATH_KEY);
        startWord = intent.getStringExtra(STARTING_WORD_KEY);
        endWord = intent.getStringExtra(ENDING_WORD_KEY);

        LinearLayout.LayoutParams textViewLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textViewLayoutParams.bottomMargin = 10;
        textViewLayoutParams.topMargin = 10;
        textViewLayoutParams.gravity= Gravity.CENTER;

        TextView firstTextView = new TextView(this);


        firstTextView.setLayoutParams(textViewLayoutParams);

        firstTextView.setText(startWord);

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.content_linear_layout);

        linearLayout.addView(firstTextView);



        editTexts = new EditText[path.length - 2];

        for(int i =0; i < editTexts.length;i++){
            editTexts[i] = new EditText(this);
        }

        createEditText(linearLayout);





        TextView secondTextView = new TextView(this);


        secondTextView.setLayoutParams(textViewLayoutParams);

        secondTextView.setText(endWord);

        linearLayout.addView(secondTextView);


        FrameLayout floatingActionButtonLayout = (FrameLayout)(findViewById(R.id.fabFrame));

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout)findViewById(R.id.activity_word_ladder
        );

        coordinatorLayout.addView(floatingActionButtonLayout);

        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.mainFab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //this will need information about the size of the path, if one exits
        //a text View for the first word
        // dynamically create and populate that many edit texts
        // a text view for the final word
        //a button to submit once the person clicks all




    }

    private void createEditText(ViewGroup layout) {


        LinearLayout.LayoutParams textInputLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);



        TextInputLayout.LayoutParams textLayoutParams = new TextInputLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        textLayoutParams.weight = 0.2f;


        //add the edit Texts in the textInput Layoout

        for(int i = 1; i < path.length-1;i++){
            TextInputLayout textInputLayout = new TextInputLayout(this);
            textInputLayout.setLayoutParams(textInputLayoutParams);

            EditText editText = editTexts[i-1];
            //editText.setText(path[i]);
            editText.setHint("Enter answer");
            editText.setHintTextColor(getResources().getColor(R.color.colorAccent));
            editText.setLayoutParams(textLayoutParams);

            textInputLayout.addView(editText);
            layout.addView(textInputLayout);
        }

        //get t
    }


    //on click handler for submit
}
