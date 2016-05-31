package com.manolosmobile.fuimultado.web;

import android.content.Context;

import com.manolosmobile.fuimultado.exceptions.ParserNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class DetranParserFactory {

    public static List<String> getAvailableEstates() {
        List<String> list = new ArrayList<>();

        for (EEstate e : EEstate.values()) {
            list.add(e.name());
        }

        return list;
    }

    public static DetranParser createParser(Context context, EEstate estate) throws ParserNotFoundException {
        DetranParser parser;
        switch (estate) {
            case RN:
                parser = new DetranParserRN(context);
                break;
            default:
                throw new ParserNotFoundException(estate.name());
        }

        return parser;
    }
}
