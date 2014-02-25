package com.github.samplett.pg.collection;

import java.util.Collection;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Testcase for {@link com.github.samplett.pg.collection.SimpleStringTrie}
 */
public class SimpleStringTrieTest {

    @Test
    public void testCreation() {
        SimpleStringTrie stringTrie = new SimpleStringTrie();
        stringTrie.add("/ab/cd");
        stringTrie.add("/ab/de");
        stringTrie.add("/zy");
        stringTrie.add("/zy/xz");
        stringTrie.add("/zy/xyz");
        assertNotNull(stringTrie);
    }

    @Test
    public void testSearch() {
        SimpleStringTrie stringTrie = new SimpleStringTrie();
        stringTrie.add("/etc/config1");
        stringTrie.add("/etc/config2");
        stringTrie.add("/content");
        stringTrie.add("/content/blog");
        stringTrie.add("/content/bag");
        Collection<String> results = stringTrie.search("/etc");
        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals("/etc/config1", results.toArray()[0]);
        assertEquals("/etc/config2", results.toArray()[1]);
    }

    @Test
    public void testRemove() {
        SimpleStringTrie stringTrie = new SimpleStringTrie();
        stringTrie.add("/etc/config11");
        stringTrie.add("/etc/config22");
        stringTrie.add("/content");
        stringTrie.add("/content/blog");
        stringTrie.add("/content/bag");
        assertTrue(stringTrie.search("/etc/config1112312").isEmpty());
        assertFalse(stringTrie.search("/etc/config22").isEmpty());
        assertFalse(stringTrie.search("/etc/config11").isEmpty());
        String removedElement = stringTrie.remove("/etc/config11");
        assertNotNull(removedElement);
        removedElement = stringTrie.remove("/etc/config11");
        assertNull(removedElement);
        assertFalse(stringTrie.search("/etc").isEmpty());
        assertFalse(stringTrie.search("/etc/config").isEmpty());
        assertTrue(stringTrie.search("/etc/config1").isEmpty());
        assertTrue(stringTrie.search("/etc/config11").isEmpty());
        assertFalse(stringTrie.search("/etc/config22").isEmpty());
        assertFalse(stringTrie.search("/etc/config2").isEmpty());
    }
}
