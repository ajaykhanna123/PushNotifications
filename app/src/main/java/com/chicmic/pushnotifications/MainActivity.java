package com.chicmic.pushnotifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {



    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;

    public static final String CHANNEL_ID="Chicmic";
    private static final String CHANNEL_NAME="android";
    private static final String CHANNEL_DESC="chicmic notifications";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // textView=findViewById(R.id.btnShowToken);

        edtEmail=findViewById(R.id.edtEmail);
        edtPassword=findViewById(R.id.edtPassword);
        btnLogin=findViewById(R.id.btnLogin);
        progressBar=findViewById(R.id.progressBar);
        initialize();

        firebaseAuth=FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser()!=null)
        {
            startProfileActivity();
        }

    }
    private void createUser()
    {
        final String email=edtEmail.getText().toString().trim();
        final String password=edtPassword.getText().toString().trim();
        if(TextUtils.isEmpty(email))
        {
            edtEmail.setError("Email Required");
            edtEmail.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            edtPassword.setError("password Required");
            edtPassword.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            edtPassword.setError("password should be atLeast 6 characters");
            edtPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {

                            startProfileActivity();
                        }
                        else if(task.getException() instanceof FirebaseAuthUserCollisionException)
                        {
                            userLogin(email,password);
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(),
                                    task.getException().getMessage(),Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });

    }
    private void userLogin(String email,String password)
    {
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                startProfileActivity();
                            }
                            else {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(),
                                        task.getException().getMessage(),Toast.LENGTH_SHORT)
                                        .show();
                            }
                    }
                });
    }
    private void startProfileActivity()
    {
        Intent intent=new Intent(this,ProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void initialize(){

        initNotificationChannelIfOreo();
    }


    private void initNotificationChannelIfOreo(){

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager
                        .IMPORTANCE_DEFAULT);
                channel.setDescription(CHANNEL_DESC);
                NotificationManager manager = getSystemService(NotificationManager.class);
                manager.createNotificationChannel(channel);
            }

        }catch (Exception ex){
            Log.e(MainActivity.class.getSimpleName(), "initNotificationChannelIfOreo: ",ex);
            //textView.setText("initNotificationChannelIfOreo: "+ex.getMessage());
        }

    }






}
