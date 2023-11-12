package org.rmj.guanzongroup.gsecurity.constants;

public class Messages {

    private static final String LOCAL_MESSAGE = "We apologize for the inconvenience. An error has occurred during the processing of your request";

    public static String getLocalMessage(Exception val){
        return LOCAL_MESSAGE + "\n \n" + val.getMessage();
    }
}
