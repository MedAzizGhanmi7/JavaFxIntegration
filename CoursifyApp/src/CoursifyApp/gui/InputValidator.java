/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deltaVelorum.coursify.messagerie.gui;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {

    private static final String ID_PATTERN = "\\d+";
    private static final String DATE_PATTERN = 
        "^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/(19|20)\\d\\d$";

    private final Pattern idPattern;
    private final Pattern datePattern;

    public InputValidator() {
        idPattern = Pattern.compile(ID_PATTERN);
        datePattern = Pattern.compile(DATE_PATTERN);
    }

    public boolean validateID(String id) {
        Matcher matcher = idPattern.matcher(id);
        return matcher.matches();
    }

    public boolean validateDate(String date) {
        Matcher matcher = datePattern.matcher(date);
        return matcher.matches();
    }
}

