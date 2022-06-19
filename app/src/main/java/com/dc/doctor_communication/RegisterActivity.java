package com.dc.doctor_communication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dc.doctor_communication.DataManagement.Symptom;
import com.dc.doctor_communication.DataManagement.Symptom2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    EditText mEmailText, mPasswordText, mPasswordcheckText, mName;
    Button mregisterBtn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        firebaseAuth =  FirebaseAuth.getInstance();

        mName = (EditText)findViewById(R.id.nameEt);
        mEmailText = (EditText)findViewById(R.id.emailEt);
        mPasswordText = (EditText)findViewById(R.id.passwordEdt);
        mPasswordcheckText = (EditText)findViewById(R.id.passwordcheckEdt);
        mregisterBtn = (Button)findViewById(R.id.register2_btn);

        mregisterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //가입 정보 가져오기
                final String email = mEmailText.getText().toString().trim();
                String pwd = mPasswordText.getText().toString().trim();
                String pwdcheck = mPasswordcheckText.getText().toString().trim();
                String userName = mName.getText().toString().trim();

                if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(getApplicationContext(), "이메일과 비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show();
                }else if(userName.isEmpty()){
                    Toast.makeText(getApplicationContext(), "이름을 입력해주세요!", Toast.LENGTH_SHORT).show();
                }else if(email.isEmpty()){
                    Toast.makeText(getApplicationContext(), "이메일을 입력해주세요!", Toast.LENGTH_SHORT).show();
                }else if(pwd.isEmpty()){
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show();
                }else if(pwd.length()<6){
                    Toast.makeText(getApplicationContext(), "비밀번호는 6자 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    if (pwd.equals(pwdcheck)) {
                        Log.d(TAG, "등록 버튼 " + email + " , " + pwd);
                        final ProgressDialog mDialog = new ProgressDialog(RegisterActivity.this);
                        mDialog.setMessage("가입중입니다...");
                        mDialog.show();

                        // 파이어베이스에 신규계정 등록
                        firebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // 가입 성공시
                                if (task.isSuccessful()) {
                                    mDialog.dismiss();

                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    String email = user.getEmail();
                                    String uid = user.getUid();
                                    String name = mName.getText().toString().trim();

                                    // 해쉬맵 테이블을 파이어베이스 데이터베이스에 저장
                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("symptom", "e");
                                    hashMap.put("part", "e");
                                    hashMap.put("painLevel", "e");
                                    hashMap.put("pain_characteristics", "e");
                                    hashMap.put("pain_situation", "e");
                                    hashMap.put("accompany_pain", "e");
                                    hashMap.put("additional", "e");

                                    hashMap.put("time", "e");
                                    hashMap.put("scheduleName", "e");
                                    hashMap.put("place", "e");
                                    hashMap.put("clinic_type", "e");
                                    hashMap.put("memo", "e");

                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference().child("users");

                                    myRef.child(uid).child("email").setValue(email);
                                    myRef.child(uid).child("name").setValue(name);

                                    String date="";

                                    for(int m=4; m<=9; m++){
                                        int lastDay = getLastDateOfMonth("2022" + m + "00");
                                        for(int i=1; i<=lastDay; i++) {
                                            int length = (int) (Math.log10(i) + 1);
                                            if (length == 1) {
                                                date = "20220" + m + "0" + i;
                                            } else {
                                                date = "20220" + m + i;
                                            }
                                            for(int j=0;j<5;j++){
                                                String jj = j+"";
                                                myRef.child(uid).child("date").child(date).child(jj).setValue(hashMap);
                                            }
                                        }
                                    }

                                    // 가입이 이루어져을시 가입 화면을 빠져나감
                                    Toast.makeText(RegisterActivity.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);

                                    startActivity(intent);
                                    overridePendingTransition(R.anim.translate_none, R.anim.translate_center_to_right);
                                    finish();

                                } else {
                                    Log.e(TAG,email);
                                    Log.e(TAG,pwd);
                                    Log.e(TAG,pwdcheck);
                                    Log.e(TAG,userName);
                                    mDialog.dismiss();
                                    Toast.makeText(RegisterActivity.this, "회원가입 양식을 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                            }
                        });
                    //비밀번호 오류시
                    } else {
                        Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다. 다시 입력해 주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });
    }
    public int getLastDateOfMonth(String yyyyMMdd) {
        String year = yyyyMMdd.substring(0,4);
        String month = yyyyMMdd.substring(4,6);
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(year),Integer.parseInt(month)-1,1);
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        return lastDay;
    }


}
