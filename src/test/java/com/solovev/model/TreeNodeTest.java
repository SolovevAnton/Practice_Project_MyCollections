package com.solovev.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TreeNodeTest {
    @Test
    public void setParentTest() {
        assertFalse(firstNode.setParent(firstNode));
        assertFalse(firstNode.setParent(secondA));
        assertFalse(firstNode.setParent(thirdA1));
        assertNull(firstNode.getParent());

        assertTrue(secondA.setParent(firstNode));
        //test that it does not duplicate secondA
        assertEquals(1, firstNode.getChildren().stream().filter(node -> node.equals(secondA)).count());
        assertTrue(thirdA1.setParent(firstNode));
        assertFalse(secondA.contains(thirdA1));

        assertFalse(secondB.contains(thirdA2));
        assertTrue(secondA.setParent(secondB));
        assertTrue(secondB.contains(thirdA2));

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

    @Test
    public void containsTest() {

        assertTrue(firstNode.contains(firstNode));
        assertTrue(firstNode.contains(secondA));
        assertTrue(secondA.contains(thirdA1));
        assertTrue(firstNode.contains(thirdA1));


        assertFalse(secondA.contains(firstNode));
        assertFalse(thirdC1.contains(firstNode));
        assertFalse(firstNode.contains(emptyNode));
    }

    @Test
    public void appendChild() {
        assertFalse(firstNode.appendChild(firstNode));
        assertFalse(secondA.appendChild(firstNode));
        assertFalse(thirdA3.appendChild(firstNode));

        assertTrue(firstNode.appendChild(thirdA1));
        assertTrue(thirdA1.appendChild(secondA));


        assertTrue(thirdA1.appendChild(emptyNode));
        assertTrue(firstNode.appendChild(emptyNode));
        assertTrue(firstNode.contains(emptyNode));
        assertFalse(thirdA1.contains(emptyNode));

    }

    @Test
    public void namesWideSearch() { //tests only for names
        assertEquals(Optional.of(firstNode), firstNode.wideSearch("1"));
        assertEquals(Optional.empty(), firstNode.wideSearch("0"));

        assertEquals(Optional.of(thirdC2), firstNode.wideSearch("1C2"));

        assertEquals(Optional.empty(), firstNode.wideSearch((String) null));
        thirdC2.appendChild(emptyNode);
        assertEquals(Optional.of(emptyNode), firstNode.wideSearch((String) null));
    }

    @Test
    public void emptyTests() {
        assertEquals("null", emptyNode.toString());

        assertTrue(emptyNode.contains(emptyNode));
        assertFalse(emptyNode.contains(firstNode));

        assertEquals(Optional.of(emptyNode), emptyNode.wideSearch((String) null));
    }
    @Test
    public void equalsTest(){
        assertEquals(firstNode, firstNode);
        assertNotEquals(firstNode, secondA);
        assertNotEquals(secondB, secondA);

        assertEquals(new TreeNode("equals"),new TreeNode("equals"));
        assertNotEquals(new TreeNode("equals",new TreeNode("parent")),new TreeNode("equals"));
        assertEquals(new TreeNode("equals",new TreeNode("parent")),new TreeNode("equals", new TreeNode("parent")));


        TreeNode copySecondC = new TreeNode("1C",firstNode);
        TreeNode copyThirdC1 = new TreeNode("1C1",copySecondC);
        assertNotEquals(secondC,copySecondC);
        TreeNode copyThirdC2 = new TreeNode("1C2",copySecondC);
        assertEquals(secondC,copySecondC);
    }

    @Test
    public void toStringTest() {
        assertEquals(
                """
                        1
                        1 1A 1B 1C
                        1A 1A1 1A2 1A3
                        1C 1C1 1C2""", firstNode.toString());

        assertEquals(secondB.getName(), secondB.toString());
        assertEquals("1C\n" +
                "1C 1C1 1C2", secondC.toString());
    }


    /**
     * Checks that nodes does not go to infinite loop after any actions
     */
    @AfterEach
    @Timeout(1000)
    public void testNotInfiniteLoop() {
        firstNode.forEach(n -> {
        });
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