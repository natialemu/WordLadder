/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.wordladder;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.engedu.worldladder.R;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class WordSelectionActivity extends AppCompatActivity {

    private PathDictionary dictionary;

    private final String PATH_KEY = "path";
    private final String STARTING_WORD_KEY = "firstWord";
    private final String ENDING_WORD_KEY = "endingWord";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            dictionary = new PathDictionary(inputStream);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public boolean onStart(View view) {
        EditText startWordView = (EditText) findViewById(R.id.startWord);
        EditText endWordView = (EditText) findViewById(R.id.endWord);
        String startWord = startWordView.getText().toString();
        String endWord = endWordView.getText().toString();
        List<String> wordsList = dictionary.findPath(startWord, endWord);

        String[] words = new String[wordsList.size()];
        words = wordsList.toArray(words);
        if (wordsList != null) {
            // TODO: Launch new activity here
            //call a new activity

            Intent intent = new Intent(this,WordLadderActivity.class);
            intent.putExtra(PATH_KEY,words);
            intent.putExtra(STARTING_WORD_KEY,startWordView.getText().toString());
            intent.putExtra(ENDING_WORD_KEY,endWordView.getText().toString());

            startActivity(intent);
            Toast toast = Toast.makeText(this, "Found a path between the two given words",
                    Toast.LENGTH_SHORT);
            toast.show();
            //pass
        } else {
            Log.i("Word ladder", "Word combination is not possible");
            Toast toast = Toast.makeText(this, "Couldn't find path between the two given words",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
        return true;
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

        return super.onOptionsItemSelected(item);
    }
}
