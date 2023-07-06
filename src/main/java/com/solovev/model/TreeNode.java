package com.solovev.model;

import java.util.*;

public class TreeNode {
        private final String name;
        private TreeNode parent;
        private final List<TreeNode> children = new ArrayList<>();

        /**
         * Constructor
         * @param name cannot be null
         */
        public TreeNode(String name) {
                if(name == null) {
                        throw new NullPointerException("Name cannot be null");
                }
                this.name = name;
        }
        public TreeNode(String name, TreeNode parent) {
                this(name);
                setParent(parent);
        }

        /**
         * Adds child to the tree node;
         * all children must be unique;
         * Child cannot be this node or its parent
         * @param child to append
         * @return true if successful append, false otherwise
         */
        public boolean appendChild(TreeNode child) {
                return parent != child
                        && !this.equals(child)
                        && children.add(child);
        }

        /**
         * Searches tree from top to bottom and from one to left
         * @param nameToFind name of the tree to search
         * @return first found treeNode with matching name or empty optional if nothing was found
         */
        public Optional<TreeNode> wideSearch(String nameToFind){
                if(name.equals(nameToFind)){
                        return Optional.of(this);
                }
                Queue<TreeNode> searchQueue = new ArrayDeque<>(children);
                List<TreeNode> checked = new ArrayList<>();
                checked.add(this);
                return wideSearch(searchQueue,checked,nameToFind);
        }

        private Optional<TreeNode> wideSearch(Queue<TreeNode> searchQueue, List<TreeNode> checked, String nameToFind){
                if(searchQueue.isEmpty()){
                        return Optional.empty();
                }
                if(searchQueue.poll().getName().equals())

        }

        public String getName() {
                return name;
        }

        public TreeNode getParent() {
                return parent;
        }

        /**
         * Stets parent
         * @param parent  parent to be set to this one
         * @throws IllegalArgumentException if parent equals to this or to its children, or children of the children
         */
        public void setParent(TreeNode parent) {
                if(this.equals(parent)) {
                        throw new IllegalArgumentException("Node cannot be its own parent");
                } else if (false) {

                }
                this.parent = parent;
        }

        public List<TreeNode> getChildren() {
                return children;
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                TreeNode treeNode = (TreeNode) o;

                if (!Objects.equals(name, treeNode.name)) return false;
                if (!Objects.equals(parent, treeNode.parent)) return false;
                return children.equals(treeNode.children);
        }

        @Override
        public int hashCode() {
                int result = name != null ? name.hashCode() : 0;
                result = 31 * result + (parent != null ? parent.hashCode() : 0);
                result = 31 * result + children.hashCode();
                return result;
        }
}
