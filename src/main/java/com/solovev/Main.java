package com.solovev;

import com.solovev.model.TreeNode;
import com.solovev.util.TreeNodeCreator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TreeNode tree = TreeNodeCreator.buildTree(scanner," ");
        System.out.println(tree);

    }
}