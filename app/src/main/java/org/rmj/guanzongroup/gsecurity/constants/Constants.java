package org.rmj.guanzongroup.gsecurity.constants;

public class Constants {

    public static final String WRITE_NFC_DATA_PAYLOAD = "nfc_data_payload";
    public static final String WRITE_NFC_DATA_WAREHOUSE_ID = "warehouse";
    public static final String WRITE_NFC_DATA_CATEGORY_ID = "category";
    public static final String WRITE_NFC_DATA_DESCRIPTION = "description";

    public static final String QR_CODE_DATA = "intent_qr_code_extra";

    public static final String READ_NFC_PAYLOAD = "intent_qr_code_extra";

    // This constant values are temporary for notification...
    // Main purpose of this being temporary is to present
    // what is the current progress of this project...

    /*
        Purpose of this notification type is to notify and alarm the
        user that the next scheduled patrol will be in 5 minutes soon...
     */
    public static final String NOTIFICATION_ALARM = "_notice_next_patrol_schedule";

    /*
        Purpose of this notification type is to notify the user that there's
        a request for visiting a specific site...
     */
    public static final String NOTIFICATION_VISIT = "_notice_visit_request";
}
