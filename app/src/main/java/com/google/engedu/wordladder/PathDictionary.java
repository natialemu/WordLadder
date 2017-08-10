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

import android.support.v4.app.INotificationSideChannel;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.zip.CheckedInputStream;

public class PathDictionary {
    private static final int MAX_WORD_LENGTH = 4;
    private static HashSet<String> words = new HashSet<>();
    private HashMap<String,String> edgeTo;

    public PathDictionary(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return;
        }
        //Log.i("Word ladder", "Loading dict");
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        //Log.i("Word ladder", "Loading dict");
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() > MAX_WORD_LENGTH) {
                continue;
            }
            words.add(word);
        }
        edgeTo = new HashMap<>();
    }

    public boolean isWord(String word) {
        return words.contains(word.toLowerCase());
    }

    private ArrayList<String> neighbours(String word) {

        ArrayList<String> neighboringWords = new ArrayList<>();



        Iterator<String> iterator = words.iterator();

        while(iterator.hasNext()){
            String potentialNeighbor = iterator.next();
            if(wordsAreNeighbors(word.toLowerCase(),potentialNeighbor.toLowerCase())){
                neighboringWords.add(potentialNeighbor);
            }
        }
        return neighboringWords;
    }

    public boolean wordsAreNeighbors(String word, String potentialNeighbor) {
        /*
        what is best way to check if two strings differ with only one word:

            their length must be the same
            go throgh one of them, compare how many
         */

        //firs
        if(word.length() != potentialNeighbor.length()){
            return false;
        }
        int characterDifference = 0;
        for(int i = 0; i < word.length(); i++){
            if(word.charAt(i) != potentialNeighbor.charAt(i)){
                characterDifference++;
            }
        }
        if(characterDifference != 1){
            return false;
        }
        return true;


    }




    public List<String> findPath(String start, String end) {

        bfs(start,end);


        Stack<String> path = new Stack<String>();
        String x;
        for(x = end; !x.equals(start);x=edgeTo.get(x)){
            path.push(x);

        }
        path.push(x);

        return path;



    }
    private void bfs(String start, String end){
        Set<String> visited = new HashSet<>();
        List<String> queue = new ArrayList<>();
        visited.add(start);
        queue.add(start);
        while(!queue.isEmpty()){
            String v = queue.remove(0);
            for(String w : neighbours(v)){
                if(!visited.contains(w)){
                    edgeTo.put(w,v);
                    if(w.equals(end)){
                        return;
                    }
                    visited.add(w);
                    queue.add(w);
                }
            }
        }
    }
}
