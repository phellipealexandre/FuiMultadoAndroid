package com.manolosmobile.fuimultado.web;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;

import com.manolosmobile.fuimultado.exceptions.ParserNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DetranParserFactoryTest extends AndroidTestCase {

    @Test
    public void DetranParseFactory_CreateDetranRNParser_Success() throws ParserNotFoundException {
        Context context = new MockContext();
        setContext(context);

        DetranParser detranParser = DetranParserFactory.createParser(context, EEstate.RN);

        assertTrue(detranParser instanceof DetranParserRN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void DetranParseFactory_CreateDetranInvalidParser_IllegalArgumentException() throws ParserNotFoundException {
        Context context = new MockContext();
        setContext(context);

        DetranParserFactory.createParser(context, EEstate.valueOf("XX"));
    }

    @Test(expected = ParserNotFoundException.class)
    public void DetranParseFactory_CreateDetranInvalidParser_ParserNotFoundException() throws ParserNotFoundException {
        Context context = new MockContext();
        setContext(context);

        DetranParserFactory.createParser(context, EEstate.getEnumByString("XX"));
    }
}