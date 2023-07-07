package com.solovev.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TreeNodeTest {

    @Test
    public void appendChild() {
        assertFalse(firstNode.appendChild(firstNode));
        assertFalse(secondA.appendChild(firstNode));
        assertFalse(thirdA3.appendChild(firstNode));


    }

    @Test
    public void wideSearch() {
    }

    @Test
    public void contains() {

        assertTrue(firstNode.contains(firstNode));
        assertTrue(firstNode.contains(secondA));
        assertTrue(secondA.contains(thirdA1));
        assertTrue(firstNode.contains(thirdA1));


        assertFalse(secondA.contains(firstNode));
        assertFalse(thirdC1.contains(firstNode));
        assertFalse(firstNode.contains(emptyNode));
    }

    @Test
    public void emptyTests() {
        assertEquals("null", emptyNode.toString());

        assertTrue(emptyNode.contains(emptyNode));
        assertFalse(emptyNode.contains(firstNode));

        assertEquals(Optional.of(emptyNode), emptyNode.wideSearch((String) null));
    }

    @Test
    public void toStringTest() {
        assertEquals(
                "1\n" +
                        "1 1A 1B 1C\n" +
                        "1A 1A1 1A2 1A3\n" +
                        "1C 1C1 1C2", firstNode.toString());

        assertEquals(secondB.getName(), secondB.toString());
        assertEquals("1C\n" +
                "1C 1C1 1C2", secondC.toString());
    }

    @ParameterizedTest
    @NullSource
    public void nullSetParentAndContainsTest(TreeNode node) {
        assertFalse(firstNode.contains(node));
        assertFalse(firstNode.appendChild(node));

        assertTrue(firstNode.setParent(node));
        assertTrue(secondA.setParent(node));

        assertFalse(firstNode.contains(secondA));
        assertFalse(firstNode.contains(thirdA1));
    }

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
    private void resetNodes() {
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