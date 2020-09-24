package testqueue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import queue_singlelinkedlist.FifoQueue;

import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class TestAppendFifoQueue {
    private FifoQueue<Integer> myIntQueue1;
    private FifoQueue<Integer> myIntQueue2;

    @BeforeEach
    void setUp() {
        myIntQueue1 = new FifoQueue<Integer>();
        myIntQueue2 = new FifoQueue<Integer>();
    }

    @AfterEach
    void tearDown(){
        myIntQueue1 = null;
        myIntQueue2 = null;
    }

    @Test
    void testConcatWithSelf() {
        assertThrows(IllegalArgumentException.class, () -> myIntQueue1.append(myIntQueue1));
    }

    @Test
    void testEmptyConcat() {
        myIntQueue1.append(myIntQueue2);
        assertEquals(0, myIntQueue1.size());
    }

    @Test
    void testNonEmptyConcatWithEmpty() {
        myIntQueue1.offer(1);
        myIntQueue1.offer(2);
        myIntQueue1.offer(3);

        myIntQueue1.append(myIntQueue2);
        assertEquals(3, myIntQueue1.size());
    }

    @Test
    void testEmptyConcatWithNonEmpty() {
        myIntQueue2.offer(1);
        myIntQueue2.offer(2);
        myIntQueue2.offer(3);

        myIntQueue1.append(myIntQueue2);
        assertEquals(3, myIntQueue1.size());
        assertEquals(0, myIntQueue2.size());
    }

    @Test
    void testConcat() {
        myIntQueue1.offer(1);
        myIntQueue1.offer(2);
        myIntQueue1.offer(3);

        myIntQueue2.offer(4);
        myIntQueue2.offer(5);
        myIntQueue2.offer(6);

        myIntQueue1.append(myIntQueue2);

        assertEquals(6, myIntQueue1.size());
        assertEquals(0, myIntQueue2.size());

        for (int i = 1; i <= 6; i++) {
            assertEquals(i, myIntQueue1.poll());
        }
    }
}