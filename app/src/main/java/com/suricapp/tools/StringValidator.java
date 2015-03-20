package com.suricapp.tools;

/**
 * Created by maxence on 19/03/15.
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringValidator {

    private Pattern patternEmail;
    private Pattern patternPassword;
    private Matcher matcher;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_PATTERN =
            "((?=.*\\\\d)(?=.*[a-z])(?=.*[@#$%]).{6,20})";

    public StringValidator()
    {
        patternEmail = Pattern.compile(EMAIL_PATTERN);
        patternPassword = Pattern.compile(PASSWORD_PATTERN);
    }

    /**
     * Validate hex with regular expression
     *
     * @param hex
     *            hex for validation
     * @return true valid hex, false invalid hex
     */
    public boolean validateEmail(final String hex) {

        matcher = patternEmail.matcher(hex);
        return matcher.matches();

    }

    /**
     * Validate hex with regular expression
     *
     * @param hex
     *            hex for validation
     * @return true valid hex, false invalid hex
     */
    public boolean validatePassword(final String hex) {

        matcher = patternPassword.matcher(hex);
        return matcher.matches();

    }
}