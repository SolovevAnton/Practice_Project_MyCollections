package com.solovev.util;

import com.solovev.model.TreeNode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Creates a tree based on string.
 */
public class TreeNodeCreator {
    /**
     * Builds treeNode based on scanner instance of multiline string, where first line is the main root tree,
     * and all rest represent its child nodes
     * @param input scanner input in the form of multiline string. it stops when first word in line equals to second
     * @param delimiter delimiter between words
     * @return build tree
     * @throws NoSuchElementException if any of the bottom line first arguments does not represent
     * previously created child or sub child node of the main node
     */
    public static TreeNode buildTree(Scanner input, String delimiter){
        Queue<String> lines = new ArrayDeque<>();
        boolean resume = true;
        while(resume){
            String line = input.nextLine();
            String words[] = line.split(delimiter);
            if(words.length > 1 && words[0].equals(words[1])){
                resume = false;
            }
        }
     return  null;
    }
    /**
     * Builds tree based on the string.
     * Example: with root of Object_Root and input: "Object_root Object_1 Object_2" and delimiter = " "
     * will append Object_1 and Object_2 as newly created TreeNodes to Object_root TreeNode and return Object_root
     *
     * @param input     sting to analyse. First word in each string is a local root. It must be part of the main root;
     *                  all else trees will be appended to it
     * @param delimiter to separate words
     * @param mainRoot  the main root tree
     * @return built tree node or empty treeNode if string is empty
     * @throws java.util.NoSuchElementException if main root does not contain local root(first argument in the string)
     */
    public static TreeNode buildTree(String input, String delimiter, TreeNode mainRoot) {
        if (input == null) {
            return new TreeNode();
        }
        Queue<String> inputQueue = Arrays
                .stream(input.split(delimiter))
                .filter(str -> !str.isEmpty()) // to filter empty
                .collect(Collectors.toCollection(ArrayDeque::new));

        if (inputQueue.isEmpty()) {
            return new TreeNode("");
        }
        TreeNode localRoot = mainRoot.wideSearch(inputQueue.poll()).orElseThrow();
        inputQueue.forEach(name -> localRoot.appendChild(searchOrCreate(name, mainRoot)));
        return localRoot;
    }

    /**
     * Searches tree in the given tree by name
     *
     * @param name     to search
     * @param treeNode tree node to search in to
     * @return found tree node or created new one with the given name if nothing was found
     */
    public static TreeNode searchOrCreate(String name, TreeNode treeNode) {
        Optional<TreeNode> foundNode;
        return treeNode != null && (foundNode = treeNode.wideSearch(name)).isPresent() ? foundNode.get()
                : new TreeNode(name);
    }
}
