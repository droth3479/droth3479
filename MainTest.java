package edu.touro.cs.mcon364;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordLadderTest {

    @org.junit.jupiter.api.Test
    void breadth() throws FileNotFoundException {
        WordLadder.Tuple<List<String>, Integer> result = WordLadder.transformationList("pride", "bride", false);
        List<String> resultPath = result.x;

        for (String s :
                resultPath) {
            System.out.println(s + "-");
        }
        System.out.println(result.y);

        assertEquals(resultPath.size()-1, result.y);
        assertEquals("pride", resultPath.get(0));
        assertEquals("bride", resultPath.get(resultPath.size()-1));
    }

    @org.junit.jupiter.api.Test
    void depth() throws FileNotFoundException {
        WordLadder.Tuple<List<String>, Integer> result = WordLadder.transformationList("pride", "bride", true);
        List<String> resultPath = result.x;

        for (String s :
                resultPath) {
            System.out.println(s + "-");
        }
        System.out.println(result.y);

        assertEquals(resultPath.size()-1, result.y);
        assertEquals("pride", resultPath.get(0));
        assertEquals("bride", resultPath.get(resultPath.size()-1));
    }
}