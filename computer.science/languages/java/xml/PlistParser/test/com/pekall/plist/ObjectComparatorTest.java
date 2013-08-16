package com.pekall.plist;

import com.pekall.plist.beans.BeanBasicType;
import junit.framework.TestCase;

public class ObjectComparatorTest extends TestCase {
     public void testNull() throws Exception {
        assertTrue(ObjectComparator.equals(null, null));

        assertFalse(ObjectComparator.equals(new Object(), null));
        assertFalse(ObjectComparator.equals(null, new Object()));

        assertFalse(ObjectComparator.equals(null,new BeanBasicType()));
    }
    // todo: test
}
