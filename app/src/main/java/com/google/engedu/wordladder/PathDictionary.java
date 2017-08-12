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

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
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

    public PathDictionary(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return;
        }
        Log.i("Word ladder", "Loading dict");
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        Log.i("Word ladder", "Loading dict");
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() > MAX_WORD_LENGTH) {
                continue;
            }
            words.add(word);
        }
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

    private boolean wordsAreNeighbors(String word, String potentialNeighbor) {
        /*
        what is best way to check if two strings differ with only one word:

        1. sort them and check the length to see if only one is the difference in distance. then iterate through once to confirm. runtime O(nlogn)
        2. create a HashTable which maps a character to its count for the longer string. then iterate through the shorter string and check
           if every character is in the map with the corresponding value. Runtime: O(n)

         */
        HashMap<Character, Integer> mapLongerString = new HashMap<>();

        if(Math.abs(potentialNeighbor.length() - word.length()) != 1){
            return false;
        }
        String largerWord;
        String smallerWord;
        if(potentialNeighbor.length() > word.length()){
            largerWord = potentialNeighbor;
            smallerWord = word;
        }else {
            largerWord = word;
            smallerWord = potentialNeighbor;
        }
        for(int i = 0; i < largerWord.length();i++)
        {
            if(mapLongerString.containsKey(largerWord.charAt(i))){
                mapLongerString.put(largerWord.charAt(i),mapLongerString.get(largerWord.charAt(i))+1);
            }else{
                mapLongerString.put(largerWord.charAt(i),1);
            }
        }

        HashMap<Character, Integer> mapShorterString = new HashMap<>();

        for(int i = 0; i < smallerWord.length(); i++ ){
            if(mapShorterString.containsKey(smallerWord.charAt(i))){
                mapShorterString.put(smallerWord.charAt(i),mapShorterString.get(smallerWord.charAt(i))+1);
            }else{
                mapShorterString.put(smallerWord.charAt(i),1);
            }

        }

        for(Character c : mapShorterString.keySet()){
            if(!mapLongerString.containsKey(c) || mapLongerString.get(c) != mapShorterString.get(c)){
                return false;
            }
        }
        return true;

        }




    public String[] findPath(String start, String end) {

        List<String> wordsInPath = new ArrayList<>();
        boolean foundTarget = false;
        Queue<String> bfs = new ArrayDeque<>();
        bfs.add(start);
        String currentWord = start;
        while(!bfs.isEmpty()){
            ArrayList<String> wordsNeighbors = neighbours(currentWord);
            Collections.sort(wordsNeighbors);
            bfs.addAll(wordsNeighbors);
            currentWord = bfs.poll();

            if(currentWord.equals(end)) {
                Collections.reverse(wordsInPath);
                foundTarget = true;
                break;
            }

            wordsInPath.add(currentWord);

        }
        if(!foundTarget){
            return null;
        }

        String[] wordLadderSolution = wordsInPath.toArray(new String[wordsInPath.size()]);

        return wordLadderSolution;
    }
}
