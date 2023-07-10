package com.solovev.model;

import java.util.*;
import java.util.function.Predicate;

public class TreeNode implements Iterable<TreeNode> {
    private String name;
    private TreeNode parent;
    private final List<TreeNode> children = new ArrayList<>();

    public TreeNode() {
    }

    public TreeNode(String name) {
        this.name = name;
    }

    public TreeNode(TreeNode parent) {
        setParent(parent);
    }

    public TreeNode(String name, TreeNode parent) {
        this(name);
        setParent(parent);
    }

    /**
     * Adds child to the tree node;
     * all children must be unique;
     * Child cannot be this node or its parent
     *
     * @param child to append, Cannot be null
     * @return true if successful append, false otherwise
     */
    public boolean appendChild(TreeNode child) {
        return child != null
                && child.setParent(this);
    }

    /**
     * Searches tree from top to bottom and from one to left
     *
     * @param predicate for tree node to check
     * @return first found treeNode for which predicate is true or empty optional if nothing was found
     */
    public Optional<TreeNode> wideSearch(Predicate<TreeNode> predicate) { //toDo can be done with iterator, but to practice is kept like this
        if (predicate.test(this)) {
            return Optional.of(this);
        }
        Queue<TreeNode> searchQueue = new ArrayDeque<>(children);
        return wideSearch(searchQueue, predicate);
    }

    private Optional<TreeNode> wideSearch(Queue<TreeNode> searchQueue, Predicate<TreeNode> predicate) {
        if (searchQueue.isEmpty()) {
            return Optional.empty();
        }
        TreeNode toCheck = searchQueue.poll();
        searchQueue.addAll(toCheck.getChildren());
        return predicate.test(toCheck) ? Optional.of(toCheck) : wideSearch(searchQueue, predicate);
    }

    /**
     * Searches tree from top to bottom and from one to left
     *
     * @param nameToFind to check
     * @return first found treeNode with this name or empty optional if nothing was found
     */
    public Optional<TreeNode> wideSearch(String nameToFind) {
        return wideSearch(node -> Objects.equals(node.getName(), nameToFind));
    }

    /**
     * tests if node is presented in this tree
     *
     * @param node node to test
     * @return true if node is found somewhere in the tree
     */
    public boolean contains(TreeNode node) {
        return wideSearch(n -> n.equals(node)).isPresent();
    }

    /**
     * Additional equals to overcome stack overflow problem: child -> parent -> child..;
     * checks nodes based only on their names. Their parent and children quality are guaranteed by iterator;
     * Note: if tree node will have more data it should be added to this method
     */
    private boolean equalsNoParentNoChildren(TreeNode node1, TreeNode node2) {
        return Objects.equals(node1.getName(), node2.getName());
    }

    /**
     * Method to construct toString
     *
     * @return list of all children names, or emptyList if it is empty
     */
    private List<String> getChildrenNames() {
        return children
                .stream()
                .map(TreeNode::getName)
                .toList();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TreeNode getParent() {
        return parent;
    }

    /**
     * Sets parent;
     * If parent is not null adds this node to its children;
     * If previous parent is not null removes this node from its children
     *
     * @param parent to be set to this one
     * @return true if parent was set successfully, false if node contains this parent
     */
    public boolean setParent(TreeNode parent) {
        boolean doesNotContainsParent = !this.contains(parent);
        if (doesNotContainsParent) {
            //removes this node from previous parent if it is not null
            if (this.parent != null) {
                this.parent.children.remove(this);
            }
            this.parent = parent;
            if (parent != null) {
                parent.getChildren().add(this);
            }
        }

        return doesNotContainsParent;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    /**
     * Provides wide going iterator for this tree
     *
     * @return wide going iterator; if hasNext is false next  will return null;
     */
    @Override
    public Iterator<TreeNode> iterator() {
        return new Iterator<>() {
            final Queue<TreeNode> treeNodes = new ArrayDeque<>();

            {
                treeNodes.add(TreeNode.this);
            }

            @Override
            public boolean hasNext() {
                return !treeNodes.isEmpty();
            }

            @Override
            public TreeNode next() {
                TreeNode polled = null;
                if (hasNext()) {
                    polled = treeNodes.poll();
                    treeNodes.addAll(polled.children);
                }
                return polled;
            }
        };
    }

    /**
     * Trees are equal ONLY if order of all their children matches also
     * @param o object to compare
     * @return treu if objects considered equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TreeNode treeNode = (TreeNode) o;

        if (!Objects.equals(parent, treeNode.parent)) return false;

        Iterator<TreeNode> thisTree = iterator();
        Iterator<TreeNode> otherTree = treeNode.iterator();
        while (thisTree.hasNext() || otherTree.hasNext()) {
            if (thisTree.hasNext() != otherTree.hasNext() //if one queue is shorter than other trees are not equal
                    ||!equalsNoParentNoChildren(thisTree.next(), otherTree.next())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        result = 31 * result + children.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String delimiterLines = "\n";
        String delimiterWords = " ";
        sb.append(getName());

        if (!getChildren().isEmpty()) { //if node does not have children shows only node name
            for (TreeNode treeNode : this) {
                //skips all nodes without children
                if (treeNode.getChildren().isEmpty()) {
                    continue;
                }
                sb.append(delimiterLines);
                sb.append(treeNode.getName());
                sb.append(delimiterWords)
                        .append(String.join(delimiterWords, treeNode.getChildrenNames()));
            }
        }

        return sb.toString();
    }
}
