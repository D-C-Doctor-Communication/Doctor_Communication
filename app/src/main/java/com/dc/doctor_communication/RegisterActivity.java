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
import com.dc.doctor_communication.GuideScreen.GuideActivity1;
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
import java.util.HashMap;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    EditText mEmailText, mPasswordText, mPasswordcheckText, mName;
    Button mregisterBtn;
    private FirebaseAuth firebaseAuth;
    //private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        //액션 바 등록하기
        //ActionBar actionBar = getSupportActionBar();
        // actionBar.setTitle("뒤로가기");
        //actionBar.setDisplayHomeAsUpEnabled(true); //뒤로가기버튼

        //파이어베이스 접근 설정
        // user = firebaseAuth.getCurrentUser();
        firebaseAuth =  FirebaseAuth.getInstance();
        // firebaseDatabase = FirebaseDatabase.getInstance().getReference();

        mName = (EditText)findViewById(R.id.nameEt);
        mEmailText = (EditText)findViewById(R.id.emailEt);
        mPasswordText = (EditText)findViewById(R.id.passwordEdt);
        mPasswordcheckText = (EditText)findViewById(R.id.passwordcheckEdt);
        mregisterBtn = (Button)findViewById(R.id.register2_btn);

        // 데이터 읽고 쓰기 // firebase 정의
       // mDatabase = FirebaseDatabase.getInstance().getReference();

        //readUser();

        // 파이어베이스 user로 접근
        // 가입버튼 ->  firebase에 데이터 저장
        mregisterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //가입 정보 가져오기
                final String email = mEmailText.getText().toString().trim();
                String pwd = mPasswordText.getText().toString().trim();
                String pwdcheck = mPasswordcheckText.getText().toString().trim();

                if(pwd.equals(pwdcheck)) {
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
                                HashMap<String,String> hashMap = new HashMap<>();
                                hashMap.put("symptom","e");
                                hashMap.put("part","e");
                                hashMap.put("painLevel","e");
                                hashMap.put("pain_characteristics","e");
                                hashMap.put("pain_situation","e");
                                hashMap.put("accompany_pain","e");
                                hashMap.put("additional","e");

                                hashMap.put("time","e");
                                hashMap.put("scheduleName","e");
                                hashMap.put("place","e");
                                hashMap.put("clinic_type", "e");
                                hashMap.put("memo", "e");

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference().child("users");

                                myRef.child(uid).child("email").setValue(email);
                                myRef.child(uid).child("name").setValue(name);

                                String date="";
                                for(int i=1; i<=30; i++) {
                                    int length = (int) (Math.log10(i) + 1);
                                    if (length == 1) {
                                        date = "2021090" + i;
                                    } else {
                                        date = "202109" + i;
                                    }
                                    for(int j=0;j<5;j++){
                                        String jj = j+"";
                                        myRef.child(uid).child("date").child(date).child(jj).setValue(hashMap);
                                    }
                                }
                                for(int i=1; i<=31; i++) {
                                    int length = (int) (Math.log10(i) + 1);
                                    if (length == 1) {
                                        date = "2021100" + i;
                                    } else date = "202110" + i;
                                    for(int j=0;j<5;j++){
                                        String jj = j+"";
                                        myRef.child(uid).child("date").child(date).child(jj).setValue(hashMap);
                                    }
                                }

                                // 가입이 이루어져을시 가입 화면을 빠져나감
                                Toast.makeText(RegisterActivity.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, GuideActivity1.class);

                                startActivity(intent);
                                overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                                finish();

                            } else {
                                mDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "비밀번호는 6자 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                                return;  // 해당 메소드 진행을 멈추고 빠져나감.

                            }

                        }
                    });
                    //비밀번호 오류시
                }else{
                    Toast.makeText(RegisterActivity.this, "비밀번호가 틀렸습니다. 다시 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    /*private void writeNewUser(String userId){
        mDatabase.child("Users").child(userId).setValue("date")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        System.out.println("회원정보 저장 완료");
                        String date="";
                        //HashMap<String> hashMap = new HashMap<>();
                        String[] arr = null;

                        for(int i=1; i<=30; i++){
                            int length = (int)(Math.log10(i)+1);
                            if(length == 1){
                                date = "2021.09.0"+i;
                            }
                            else{
                                date = "2021.09."+i;
                            }
                            arr[i-1] = date;
                            //mDatabase.child("date").setValue(date);

                        }
                        List nameList = new ArrayList<String>(Arrays.asList(arr));

                        mDatabase.child("Users").child(userId).child("date").setValue(nameList);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("회원정보 저장 실패");
                    }
                });

    }*/

    /*private void readUser(){
        mDatabase.child("users").child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue(User.class) != null){
                    User post = snapshot.getValue(User.class);
                    Log.w("FireBaseData", "getData"+post.toString());
                }
                else{
                    Toast.makeText(RegisterActivity.this, "데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("FireBaseData", "loadPost:onCancelled", error.toException());
            }
        });
    }*/
    /*public boolean onSupportNavigateUp(){
        onBackPressed();; // 뒤로가기 버튼이 눌렸을시
        return super.onSupportNavigateUp(); // 뒤로가기 버튼
    }*/
}
