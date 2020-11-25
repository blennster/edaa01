package testbst;

import bst.BinarySearchTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class TestBinarySearchTree {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private BinarySearchTree<Integer> tree;

    @BeforeEach
    public void setStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @BeforeEach
    public void setup() {
        tree = new BinarySearchTree<>(Integer::compareTo);
    }

    @Test
    public void testAdd() {
        tree.add(10);
        tree.add(11);
        tree.add(9);
        assertTrue(tree.add(12), "Test adding a new leaf in depth 2");
        assertFalse(tree.add(12), "Duplicates not allowed.");

        assertEquals(4, tree.size());
    }

    @Test
    public void testHeight() {
        assertEquals(0, tree.height());

        tree.add(10);
        assertEquals(1, tree.height());

        tree.add(11);
        tree.add(9);
        assertEquals(2, tree.height());

        tree.add(3);
        assertEquals(3, tree.height());

        tree.add(2);
        assertEquals(4, tree.height());

        tree.add(4);
        assertEquals(4, tree.height());
    }

    @Test
    public void testPrint() {
        tree.add(10);
        tree.add(11);
        tree.add(9);

        tree.printTree();

        assertEquals("9\n10\n11\n", outContent.toString());
    }

    @Test
    public void testClear() {
        tree.add(1);
        tree.clear();

        tree.printTree();

        assertEquals("", outContent.toString());
    }

    @Test
    public void testConstructor() {
        BinarySearchTree<Integer> t1 = new BinarySearchTree<>();
        t1.add(1);
        t1.add(2);
        t1.add(3);
        t1.printTree();
        assertEquals("1\n2\n3\n", outContent.toString());

        // Clear stream
        outContent.reset();

        // Try a reverse order comparator
        BinarySearchTree<Integer> t2 = new BinarySearchTree<>((e1, e2) -> e2.compareTo(e1));
        t2.add(1);
        t2.add(2);
        t2.add(3);
        t2.printTree();
        assertEquals("3\n2\n1\n", outContent.toString());
    }
}
