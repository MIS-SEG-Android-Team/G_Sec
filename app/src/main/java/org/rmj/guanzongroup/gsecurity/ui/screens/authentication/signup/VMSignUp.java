package org.rmj.guanzongroup.gsecurity.ui.screens.authentication.signup;

import static org.rmj.guanzongroup.gsecurity.constants.Messages.PLEASE_WAIT;
import static org.rmj.guanzongroup.gsecurity.constants.Messages.getLocalMessage;
import static org.rmj.guanzongroup.gsecurity.constants.Messages.getMessage;
import static org.rmj.guanzongroup.gsecurity.data.remote.collection.Collections.APP_USER_MASTER;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.rmj.guanzongroup.gsecurity.pojo.signup.SignUpCredentials;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogResult;

import java.util.HashMap;
import java.util.Map;

public class VMSignUp extends AndroidViewModel {

    public VMSignUp(@NonNull Application application) {
        super(application);
    }

    public void signup(SignUpCredentials credentials, SignUpCallback callback){
        callback.onLoad(PLEASE_WAIT);

        SignUpCredentials.Validator validator = new SignUpCredentials.Validator();

        if(!validator.isDataValid(credentials)) {
            callback.onFailed(validator.getMessage());
            return;
        }

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(
                String.valueOf(credentials.getEmailAdd()),
                String.valueOf(credentials.getPassword())
        ).addOnCompleteListener(task -> {
            if(!task.isSuccessful()){
                FirebaseAuthException firebaseAuthException = (FirebaseAuthException) task.getException();
                callback.onFailed(
                        getMessage(firebaseAuthException.getErrorCode())
                );
                return;
            }

            Map<String, Object> user = new HashMap<>();
            user.put("nUserLevel", "0");
            user.put("sEmailAdd", credentials.getEmailAdd());
            user.put("sPosition", "");
            user.put("sUserIDxx", "");
            user.put("sUserName", credentials.getUserName());

            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseFirestore
                    .collection(APP_USER_MASTER)
                    .add(user)
                    .addOnCompleteListener(task1 -> {
                       if(!task1.isSuccessful()){
                           FirebaseFirestoreException firebaseFirestoreException = (FirebaseFirestoreException) task1.getException();
                           callback.onFailed(
                                   firebaseFirestoreException.getMessage()
                           );
                           return;
                       }
                    });

            callback.onSuccess();
        });
    }
}