package com.chicmic.pushnotifications;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    public static String USERS="Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseMessaging.getInstance().subscribeToTopic("Updates");
        initToken();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser()==null)
        {
            Intent intent=new Intent(this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

    }

    private void initToken(){

        String token = MessagingService.getRefreshedToken();
        if(TextUtils.isEmpty(token)){

            // textView.setText("Refreshing token....");



            FirebaseInstanceId.getInstance(FirebaseApp.getInstance()).getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if(task.isSuccessful())
                            {
                                String token=task.getResult().getToken();
                                MessagingService.setRefreshedToken(token);
                                saveToken(token);
                                //  textView.setText(token);
                            }else
                            {
                                //        textView.setText(task.getException().getMessage());
                            }
                        }
                    });

        }else {
            //textView.setText(token);
        }


    }
    private void saveToken(String token)
    {
        String email=firebaseAuth.getCurrentUser().getEmail();
        User user=new User(email,token);

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(USERS);
        databaseReference.child(firebaseAuth.getCurrentUser().getUid())
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(ProfileActivity.this,"Token saved",Toast.LENGTH_SHORT)
                                .show();
                    }
            }
        });
    }
}
