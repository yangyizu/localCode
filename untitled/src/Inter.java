
import org.hamcrest.core.Is;
import org.junit.Test;
import sun.font.CreatedFontTracker;
import util.JDBCUtils;

import javax.swing.plaf.ButtonUI;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class TreeLinkNode {
    int val;
    TreeLinkNode left = null;
    TreeLinkNode right = null;
    TreeLinkNode next = null;

    TreeLinkNode(int val) {
        this.val = val;
    }
}

class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}


public class Inter {
    public static void main(String[] args) {
        new son().print();
    }
}

class father{
    void print(){
        System.out.println(this);
    }
}

class son extends father{
    void print(){
        System.out.println(this);
        super.print();
    }
}

