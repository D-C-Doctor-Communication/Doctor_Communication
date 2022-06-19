package com.dc.doctor_communication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignActivity extends AppCompatActivity{
    Button mLoginBtn;
    Button mResigettxt;
    EditText mEmailText;
    EditText mPasswordText;

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        // 버튼 등록하기
        mResigettxt = findViewById(R.id.register_t2);
        mLoginBtn = findViewById(R.id.login_btn);
        mEmailText = findViewById(R.id.emailEt);
        mPasswordText = findViewById(R.id.et_password);

        // 파이어베이스 인증 객체 선언
        firebaseAuth = FirebaseAuth.getInstance();

        // 회원가입으로 이동
        mResigettxt.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
        });

        // 로그인 버튼
        mLoginBtn.setOnClickListener(v -> {
            String email = mEmailText.getText().toString().trim();
            String pwd = mPasswordText.getText().toString().trim();

            if(email.isEmpty() && pwd.isEmpty()){
                Toast.makeText(getApplicationContext(), "이메일과 비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show();
            }
            else if(email.isEmpty()){
                Toast.makeText(getApplicationContext(), "이메일을 입력해주세요!", Toast.LENGTH_SHORT).show();
            }
            else if(pwd.isEmpty()){
                Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show();
            }
            else{
                firebaseAuth.signInWithEmailAndPassword(email,pwd)
                        .addOnCompleteListener(SignActivity.this, task -> {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(in);
                                overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                                finish();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"로그인 오류",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
