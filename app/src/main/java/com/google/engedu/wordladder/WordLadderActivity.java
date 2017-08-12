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
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
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
import java.util.HashSet;
import java.util.Set;

public class WordLadderActivity extends AppCompatActivity {



    private final String PATH_KEY = "path";
    private final String STARTING_WORD_KEY = "firstWord";
    private final String ENDING_WORD_KEY = "endingWord";

    // an array of text views
    // the array of paths
    //the start word
    //the final word
    private Set<String> path;
    private String startWord;
    private String endWord;

    private EditText[] editTexts;
    private boolean fabExpanded = false;
    private FloatingActionButton floatingActionButton;

    //Linear layout holding the Save submenu
    private LinearLayout layoutFabSubmit;

    //Linear layout holding the Edit submenu
    private LinearLayout layoutFabGiveup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_ladder);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        String[] paths = intent.getStringArrayExtra(PATH_KEY);
        path = new HashSet<>();
        for(String p: paths){
            path.add(p);
        }
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



        editTexts = new EditText[path.size() - 2];

        for(int i =0; i < editTexts.length;i++){
            editTexts[i] = new EditText(this);
        }

        createEditText(linearLayout);





        TextView secondTextView = new TextView(this);


        secondTextView.setLayoutParams(textViewLayoutParams);

        secondTextView.setText(endWord);

        linearLayout.addView(secondTextView);


       /* FrameLayout floatingActionButtonLayout = (FrameLayout)(findViewById(R.id.fabFrame));

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout)findViewById(R.id.activity_word_ladder
        );

        linearLayout.addView(floatingActionButtonLayout);*/

        floatingActionButton = (FloatingActionButton)findViewById(R.id.mainFab);

        layoutFabSubmit = (LinearLayout) findViewById(R.id.layoutFabSubmit);
        layoutFabGiveup = (LinearLayout) findViewById(R.id.layoutFabGiveup);

        closeSubMenusFab();


        floatingActionButton.setImageResource(R.drawable.ic_add_black_24dp);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fabExpanded){
                    closeSubMenusFab();
                }else{
                    openSubMenusFab();
                }


            }
        });

        FloatingActionButton submitFab = (FloatingActionButton)findViewById(R.id.fabSolve);

        FloatingActionButton giveupFab = (FloatingActionButton)findViewById(R.id.fabGiveup);


        submitFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fabExpanded){
                    if(gameSolved()){
                        Toast.makeText(getApplicationContext(),"Congrats! You solved it",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Tough luck! Try again",Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
        giveupFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fabExpanded){
                    onSolveClick(view);
                }
            }
        });

        //this will need information about the size of the path, if one exits
        //a text View for the first word
        // dynamically create and populate that many edit texts
        // a text view for the final word
        //a button to submit once the person clicks all




    }

    private void onSolveClick(View view) {
        //TODO: implement solver
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_word_selection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.game_help){
            Intent intent = new Intent(this, HelpActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    private boolean gameSolved() {
        for(EditText editText: editTexts){
            if(!path.contains(editText.getText().toString())){

                return false;
            }
        }
        return true;
    }

    private void createEditText(ViewGroup layout) {


        LinearLayout.LayoutParams textInputLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        TextInputLayout.LayoutParams textLayoutParams = new TextInputLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        textLayoutParams.weight = 0.2f;


        //add the edit Texts in the textInput Layoout

        for (int i = 1; i < path.size() - 1; i++) {
            TextInputLayout textInputLayout = new TextInputLayout(this);
            textInputLayout.setLayoutParams(textInputLayoutParams);

            EditText editText = editTexts[i - 1];
            //editText.setText(path[i]);
            editText.setHint("Enter answer");
            editText.setHintTextColor(getResources().getColor(R.color.colorAccent));
            editText.setLayoutParams(textLayoutParams);

            textInputLayout.addView(editText);
            layout.addView(textInputLayout);
        }

        //get t
    }

    private void closeSubMenusFab(){
        layoutFabSubmit.setVisibility(View.INVISIBLE);
        layoutFabGiveup.setVisibility(View.INVISIBLE);

        floatingActionButton.setBackgroundResource(R.drawable.ic_add_black_24dp);
        fabExpanded = false;
    }

    //Opens FAB submenus
    private void openSubMenusFab() {
        layoutFabSubmit.setVisibility(View.VISIBLE);
        layoutFabGiveup.setVisibility(View.VISIBLE);
        floatingActionButton.setBackgroundResource(R.drawable.ic_close_black_24dp);
        fabExpanded = true;
    }

    //on click handler for submit
}
