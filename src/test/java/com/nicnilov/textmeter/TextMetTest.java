package com.nicnilov.textmeter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Created as part of textmeter project
 * by Nic Nilov on 25.10.13 at 20:46
 */

public class TextMetTest
    extends TestCase
{
    public TextMetTest(String testName)
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( TextMetTest.class );
    }

    public void testApp()
    {
        assertTrue( true );
    }
}
