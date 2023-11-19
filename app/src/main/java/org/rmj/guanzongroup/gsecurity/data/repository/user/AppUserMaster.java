package org.rmj.guanzongroup.gsecurity.data.repository.user;

import com.google.firebase.firestore.FirebaseFirestore;

import org.rmj.guanzongroup.gsecurity.data.remote.collection.Collections;

public class AppUserMaster {
    private static final String TAG = AppUserMaster.class.getSimpleName();

    private FirebaseFirestore firestore;

    private String message;

    public AppUserMaster() {
        firestore = FirebaseFirestore.getInstance();
    }

    public boolean saveUserInfo(){
        try{
//            firestore.collection(Collections.APP_USER_MASTER).document()
//                    .set()
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }
}
