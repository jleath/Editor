import editor.FastLinkedList;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestFastLinkedList {

    @Test
    public void testInsertion() {
        FastLinkedList<String> tb = new FastLinkedList<>();
        tb.insert("0");
        tb.insert("1");
        tb.insert("2");
        assertEquals("012", tb.toString());
        assertTrue(tb.size() == 3);
    }

    @Test
    public void testIsEmpty() {
        FastLinkedList<String> tb = new FastLinkedList<>();
        assertTrue(tb.isEmpty());
        tb.insert("10");
        assertFalse(tb.isEmpty());
        tb.delete();
        assertTrue(tb.isEmpty());
    }

    @Test
    public void testDeletion() {
        FastLinkedList<String> tb = new FastLinkedList<>();
        tb.insert("0");
        tb.insert("1");
        tb.insert("2");
        FastLinkedList<String>.Node cursor = tb.get(2);
        tb.setInsertionPoint(cursor);
        assertEquals("2", tb.delete());
        assertEquals("1", tb.delete());
        assertEquals("0", tb.delete());
        assertTrue(tb.size() == 0);
    }
}
