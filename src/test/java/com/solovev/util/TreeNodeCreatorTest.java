package com.solovev.util;

import com.solovev.model.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static com.solovev.util.TreeNodeCreator.*;

public class TreeNodeCreatorTest {
    @Test
    public void buildTreeScannerTest(){
        String firstNodeCreator = firstNode.toString() + "\nfinish finish";
        Scanner scan = new Scanner(firstNodeCreator);

        assertEquals(firstNode,buildTree(scan,delimiter));

        scan = new Scanner("""
                Object_root
                Object_root Object_1
                Object_root Object_2
                Object_root Object_3
                Object_3 Object_4
                Object_3 Object_5
                Object_6 Object_6
                """);

        assertEquals("""
                Object_root
                Object_root Object_1 Object_2 Object_3
                Object_3 Object_4 Object_5""",buildTree(scan,delimiter).toString());

        scan = new Scanner("""
                1
                1 a c s d
                1 f r
                a a1 a2 a3
                a1 a1a a1b a1c
                f f1 f2
                a1c a
                a a""");

        assertEquals("""
                1
                1 a c s d f r
                a a1 a2 a3
                f f1 f2
                a1 a1a a1b a1c""", buildTree(scan,delimiter).toString());
    }

    @Test
    void buildTreeTest() {
        String thirdC1Creator = thirdC1.getName();
        String secondCCreator = secondC.getName() + delimiter + thirdC1Creator + delimiter + thirdC2.getName();
        String equalWords = thirdA1.getName() + delimiter + thirdA1.getName();

        assertEquals(thirdC1, buildTree(thirdC1Creator, delimiter, firstNode));
        assertEquals(secondC, buildTree(secondCCreator, delimiter, firstNode));
        assertEquals(secondC, buildTree(secondCCreator, delimiter, firstNode));

        assertEquals(thirdA1,buildTree(equalWords,delimiter,thirdA1));

        assertThrows(NoSuchElementException.class, () -> buildTree(secondCCreator, delimiter, thirdC1));
    }

    @Test
    void searchOrCreateTest() {
        assertEquals(secondA, searchOrCreate(secondA.getName(), firstNode));
        assertEquals(new TreeNode("name"), searchOrCreate("name", firstNode));
        assertEquals(new TreeNode(secondA.getName()), searchOrCreate(secondA.getName(), null));
    }

    @ParameterizedTest
    @NullSource
    public void nullTests(String str) {
        assertEquals(emptyNode, searchOrCreate(str, firstNode));
        assertEquals(new TreeNode(), searchOrCreate(str, null));
        assertEquals(emptyNode, buildTree(str,delimiter,firstNode));
        assertEquals(new TreeNode(), buildTree(str,delimiter,null));
    }
    @ParameterizedTest
    @EmptySource
    public void emptyTests(String str) {
        assertEquals(new TreeNode(str), searchOrCreate(str, firstNode));
        assertEquals(new TreeNode(str), searchOrCreate(str, null));
        assertEquals(new TreeNode(str), buildTree(str,delimiter,firstNode));
        assertEquals(new TreeNode(str), buildTree(str,delimiter,null));
    }
    String delimiter = " ";
    private TreeNode emptyNode;
    private TreeNode firstNode;
    private TreeNode secondA;
    private TreeNode secondB;
    private TreeNode secondC;
    private TreeNode thirdA1;
    private TreeNode thirdA2;
    private TreeNode thirdA3;
    private TreeNode thirdC1;
    private TreeNode thirdC2;

    /**
     * Resets all nodes to their default state:
     * "1
     * 1 1A 1B 1C
     * 1A 1A1 1A2 1A3
     * 1C 1C1 1C2"
     */
    @BeforeEach
    public void resetNodes() {
        emptyNode = new TreeNode();
        firstNode = new TreeNode("1");
        secondA = new TreeNode("1A", firstNode);
        secondB = new TreeNode("1B", firstNode);
        secondC = new TreeNode("1C", firstNode);
        thirdA1 = new TreeNode("1A1", secondA);
        thirdA2 = new TreeNode("1A2", secondA);
        thirdA3 = new TreeNode("1A3", secondA);
        thirdC1 = new TreeNode("1C1", secondC);
        thirdC2 = new TreeNode("1C2", secondC);

    }
}