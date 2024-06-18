package org.rmj.guanzongroup.gsecurity.constants;

import static com.google.firebase.FirebaseError.ERROR_INVALID_CUSTOM_TOKEN;

public class Messages {

    private static final String LOCAL_MESSAGE = "We apologize for the inconvenience. An error has occurred during the processing of your request";

    public static String getLocalMessage(Exception val){
        return LOCAL_MESSAGE + "\n \n" + val.getMessage();
    }

    /**
     * Default message for any unknown error messages...
     */
    public static final String UNEXPECTED_ERROR_OCCURRED = "An unexpected error occurred";

    public static final String PLEASE_WAIT = "Please wait...";

    /**
     * Firebase authentication error messages base on error codes
     * This messages are rephrase for more user friendly approach...
     */
    public static String ERROR_INVALID_CUSTOM_TOKEN = "There seems to be an issue with the format of your authentication token.";
    public static String ERROR_CUSTOM_TOKEN_MISMATCH = "There's a mismatch between the authentication token and the intended audience. Please check that you're using the correct token for this specific application or service.";
    public static String ERROR_INVALID_CREDENTIAL = "There seems to be an issue with the format or validity of your authentication credentials.";
    public static String ERROR_INVALID_EMAIL = "We encountered a problem with the email address you entered. It may be invalid or not recognized. Please check the email address and try again.";
    public static String ERROR_WRONG_PASSWORD = "There seems to be a problem with the password you entered. Please review your password and try again.";
    public static String ERROR_USER_MISMATCH = "There seems to be a discrepancy between the provided credentials and the ones previously registered for this account.";

    /**
     * getMessage() returns a user friendly error message which is parse/rephrase from firebase error codes
     * @param firebaseErrorCode
     * @return
     */
    public static String getMessage(String firebaseErrorCode){
        switch (firebaseErrorCode) {
            case "ERROR_INVALID_CUSTOM_TOKEN":
                return ERROR_INVALID_CUSTOM_TOKEN;
            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                return ERROR_CUSTOM_TOKEN_MISMATCH;
            case "ERROR_INVALID_CREDENTIAL":
                return ERROR_INVALID_CREDENTIAL;
            case "ERROR_INVALID_EMAIL":
                return ERROR_INVALID_EMAIL;
            case "ERROR_WRONG_PASSWORD":
                return ERROR_WRONG_PASSWORD;
            case "ERROR_USER_MISMATCH":
                return ERROR_USER_MISMATCH;
            default:
                return UNEXPECTED_ERROR_OCCURRED;
        }
    }
}
