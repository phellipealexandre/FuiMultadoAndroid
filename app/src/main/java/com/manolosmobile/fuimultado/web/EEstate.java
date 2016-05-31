package com.manolosmobile.fuimultado.web;

import com.manolosmobile.fuimultado.exceptions.ParserNotFoundException;

public enum EEstate {
    RN;

    public static EEstate getEnumByString(String estate) throws ParserNotFoundException {
        for(EEstate e : EEstate.values()){
            if(estate.equals(e.name())) {
                return e;
            }
        }

        throw new ParserNotFoundException(estate);
    }
}
