package edu.touro.cs.mcon364;

import java.io.*;
import java.util.*;

public class WordLadder {
    public static Tuple<List<String>, Integer> transformationList(String source, String destination, Boolean depth) throws FileNotFoundException {
        //Create word queue
        LinkedList<Node> wordQueue = new LinkedList<>();

        //Ensure case-type
        source = source.toLowerCase();
        destination = destination.toLowerCase();

        //Create list of 5-letter words, to enable deletion of words as they are added to the tree.
        LinkedList<String> dictionary = new LinkedList<>();
        Scanner textFile = new Scanner(new File("slimDictionary.txt"));
        while (textFile.hasNext()) {
            dictionary.add(textFile.next().trim());
        }

        //Check solution is possible
        if(!(dictionary.contains(source) && dictionary.contains(destination))) {
            return new Tuple(null, null);
        }

        //Check if source and destination are only one off
        if(oneOff(source, destination)) {
            return getTuple(new Node(source, null), destination);
        }

        //Create first node
        wordQueue.add(new Node(source, null));

        //Init values
        Node currentNode;
        String testWord;
        Iterator<String> it = dictionary.iterator();

        do{
            currentNode = wordQueue.pop();
            String currentWord = currentNode.word;
            while (it.hasNext()) {
                if(oneOff(currentWord, testWord = it.next())) {
                    //Remove off-by-one match, as there is no need to include it in two paths
                    it.remove();
                    //Test if word is last step in path, i.e. one letter away from dest, and return path if it is
                    if(oneOff(testWord, destination)) {
                        return getTuple(new Node(testWord, currentNode), destination);
                    }
                    //Else, add it and proceed. Append to end for breadth
                    if(!depth){
                        wordQueue.add(new Node(testWord, currentNode));
                    }
                    //Append to front for depth
                    else {
                        wordQueue.push(new Node(testWord, currentNode));
                    }
                }
            }
            //Reset iterator
            it = dictionary.iterator();
        }while(!wordQueue.isEmpty());

        //If no solution is found, return empty tuple
        return new Tuple(null, null);
    }

    private static Tuple<List<String>, Integer> getTuple(Node node, String destination) {
        LinkedList<String> path = new LinkedList<>();
        int transversalCount = 0;
        Node currentNode = node;

        //Add the destination word, if needed, and a transversal increment
        if(!node.word.equals(destination)){
            path.push(destination);
            transversalCount++;
        }
        //Add each word in the path
        while(currentNode.ancestor != null){
            path.push(currentNode.word);
            transversalCount++;
            currentNode = currentNode.ancestor;
        };
        //Add original word, without transversal increment
        path.push(currentNode.word);

        return new Tuple(path, transversalCount);
    }

    private static boolean oneOff(String currentWord, String testWord) {
        int diffs = 0;
        for (int i = 0; i < testWord.length(); i++) {
            if(currentWord.charAt(i) != testWord.charAt(i)) {
                diffs++;
            }
        }
        return diffs == 1;
    }

    static class Tuple<X, Y> {
        public final X x;
        public final Y y;
        public Tuple(X x, Y y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class Node{
        public final String word;
        public final Node ancestor;
        public Node(String word, Node ancestor) {
            this.word = word;
            this.ancestor = ancestor;
        }
    }

    public static void main(String[] args) throws IOException {
        Tuple<List<String>, Integer> result = WordLadder.transformationList("dread", "pride", false);
        if(result.x == null) {
            System.out.println("No path found");
        }
        else {
            List<String> resultPath = result.x;
            for (String s :
                    resultPath) {
                System.out.println(s + "-");
            }
            System.out.println(result.y);
        }
    }
}
