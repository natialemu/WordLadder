package com.google.engedu.wordladder;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.engedu.worldladder.R;

import org.w3c.dom.Text;

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

        TextView firstTextView = new TextView(this);

        LinearLayout.LayoutParams textViewLinearLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        firstTextView.setLayoutParams(textViewLinearLayoutParams);

        firstTextView.setText(startWord);

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.content_linear_layout);

        linearLayout.addView(firstTextView);

        TextInputLayout textInputLayout = new TextInputLayout(this);

        LinearLayout.LayoutParams textInputLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.addView(textInputLayout);

        editTexts = new EditText[path.length - 2];

        for(int i =0; i < editTexts.length;i++){
            editTexts[i] = new EditText(this);
        }

        createEditText(textInputLayout);



        TextView secondTextView = new TextView(this);


        secondTextView.setLayoutParams(textViewLinearLayoutParams);

        secondTextView.setText(endWord);

        linearLayout.addView(secondTextView);
        //this will need information about the size of the path, if one exits
        //a text View for the first word
        // dynamically create and populate that many edit texts
        // a text view for the final word
        //a button to submit once the person clicks all


    }

    private void createEditText(TextInputLayout textInputLayout) {

        TextInputLayout.LayoutParams textLayoutParams = new TextInputLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        textLayoutParams.weight = 0.2f;


        //add the edit Texts in the textInput Layoout

        for(int i = 1; i < path.length-1;i++){

            EditText editText = editTexts[i-1];
            editText.setText(path[i]);
            editText.setHint("Word");
            editText.setHintTextColor(getResources().getColor(R.color.colorAccent));
            textInputLayout.addView(editText);
        }

        //get t
    }


    //on click handler for submit
}
