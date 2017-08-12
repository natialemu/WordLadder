package com.google.engedu.wordladder;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.FileHandler;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Nathnael on 8/8/2017.
 */
public class PathDictionaryTest {

    PathDictionary pathDictionary;



    @Before
    public void setUp() throws Exception {



        pathDictionary = new PathDictionary(new FileInputStream("C:\\Users\\Nathnael\\Documents\\GoogleAndroid\\WordLadder\\app\\src\\main\\assets\\words.txt"));

    }

    @Test
    public void findPath() throws Exception {
        String startWord = "gain";

        String endWord = "fire";

        List<String> paths = pathDictionary.findPath(startWord,endWord);

        assert (paths.contains(startWord));
        assert(paths.contains("gait"));
        assert(paths.contains("wait"));
        assert(paths.contains("wart"));
        assert(paths.contains("ware"));
        assert(paths.contains("wire"));
        assert (paths.contains(endWord));

        assertEquals(paths.size(),7);




    }

    @Test
    public void wordsAreNeighbors() throws Exception{

        String firstWord = "a";


        String potentialNeighbor = "b";
        assert(pathDictionary.wordsAreNeighbors(firstWord,potentialNeighbor));
        potentialNeighbor = "c";
        assert(pathDictionary.wordsAreNeighbors(firstWord,potentialNeighbor));

        firstWord = "ab";
        potentialNeighbor = "bb";
        assert(pathDictionary.wordsAreNeighbors(firstWord,potentialNeighbor));

        potentialNeighbor = "cb";
        assert(pathDictionary.wordsAreNeighbors(firstWord,potentialNeighbor));

        potentialNeighbor = "bc";
        assertFalse(pathDictionary.wordsAreNeighbors(firstWord,potentialNeighbor));

        firstWord = "abc";
        potentialNeighbor = "dbc";
        assert(pathDictionary.wordsAreNeighbors(firstWord,potentialNeighbor));

        potentialNeighbor = "abd";
        assert(pathDictionary.wordsAreNeighbors(firstWord,potentialNeighbor));

        potentialNeighbor = "bad";
        assertFalse(pathDictionary.wordsAreNeighbors(firstWord,potentialNeighbor));

        firstWord = "";
        potentialNeighbor = "a";
        assertFalse(pathDictionary.wordsAreNeighbors(firstWord,potentialNeighbor));

        potentialNeighbor = "z";
        assertFalse(pathDictionary.wordsAreNeighbors(firstWord,potentialNeighbor));

        potentialNeighbor = "";
        assert(pathDictionary.wordsAreNeighbors(firstWord,potentialNeighbor));

    }

}