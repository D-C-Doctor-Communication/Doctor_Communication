package com.dc.doctor_communication.Emergency;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dc.doctor_communication.R;
import com.dc.doctor_communication.SymptomRegistration.SearchAdapter;
import com.dc.doctor_communication.SymptomRegistration.SearchList;
import com.dc.doctor_communication.SymptomRegistration.SelectBody_arm;
import com.dc.doctor_communication.SymptomRegistration.SelectBody_back;
import com.dc.doctor_communication.SymptomRegistration.SelectBody_body;
import com.dc.doctor_communication.SymptomRegistration.SelectBody_buttock;
import com.dc.doctor_communication.SymptomRegistration.SelectBody_chest;
import com.dc.doctor_communication.SymptomRegistration.SelectBody_face;
import com.dc.doctor_communication.SymptomRegistration.SelectBody_foot;
import com.dc.doctor_communication.SymptomRegistration.SelectBody_hand;
import com.dc.doctor_communication.SymptomRegistration.SelectBody_head;
import com.dc.doctor_communication.SymptomRegistration.SelectBody_leg;
import com.dc.doctor_communication.SymptomRegistration.SelectBody_stomach;
import com.dc.doctor_communication.SymptomRegistration.SelectBody_waist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class EmergencySearchList extends AppCompatActivity {


    private ListView listView;
    private List<String> list;
    private ArrayList<String> nameArr;
    private SearchAdapter adapter;
    private EditText search_text; //?????? ?????????
    private ImageView back_btn;

    String[] symptom_Nm;
    String[] part_Nm;
    int[] part_num;
    JSONObject jo;
    String registration;
    private Context mContext;
    private TextView txt_preferences;
    int repeat;
    String[] str = new String[5];

    String shared = "date"; //????????????
    String todayDate; //key???
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_search_list);

        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(v -> {
            finish();
        });

        mContext=this;
        search_text = (EditText)findViewById(R.id.search_text);
        listView = (ListView)findViewById(R.id.search_list);
        Log.e("hihi", "onCreate: ");

        Intent intent = getIntent();
        repeat = intent.getExtras().getInt("repeat");
        Log.d("repeat", repeat+"");
        list = new ArrayList<String>();
        settingList();
        str[0] = null;
        str[1] = null;
        str[2] = null;
        str[3] = null;
        str[4] = null;
        nameArr = new ArrayList<>();
        nameArr.addAll(list);

        adapter = new SearchAdapter(list,this);
        listView.setAdapter(adapter);


        //????????? ?????? ?????? ??????
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        todayDate = sdf.format(calendar.getTime());

        SharedPreferences sharedPreferences = getSharedPreferences(shared,0);
        repeat = sharedPreferences.getInt(todayDate,0);
        Log.d("myapp",repeat+"");

        //?????? ?????? edittext ?????? ?????? ???
        search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = search_text.getText().toString(); //?????? ????????? ????????????
                search(content);
            }
        });
        listView.setOnItemClickListener(listener);
    }

    //???????????? ?????????
    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent;

            Log.e("her!", String.valueOf(part_num[position]));
            switch (part_num[position])  //????????? ?????? ?????? ??????
            {
                case 1 : {  //??????
                    Log.e("her!", "1???");
                    intent = new Intent(getApplicationContext(), SelectBody_head.class);
                    intent.putExtra("emergency",true);
                    intent.putExtra("symptom", symptom_Nm[position]); //????????? ??????
                    intent.putExtra("part", part_num[position]);
                    intent.putExtra("repeat",repeat);
                    startActivity(intent);
                    overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);

                    break;
                }
                case 2 : {  //??????
                    Log.e("her!", "2???");
                    intent = new Intent(getApplicationContext(),SelectBody_face.class);
                    intent.putExtra("emergency",true);
                    intent.putExtra("symptom", symptom_Nm[position]); //????????? ??????
                    intent.putExtra("part", part_num[position]);
                    intent.putExtra("repeat",repeat);
                    startActivity(intent);
                    overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                    break;
                }
                case 3 : {  //???
                    Log.e("her!", "3???");
                    intent = new Intent(getApplicationContext(), SelectBody_arm.class);
                    intent.putExtra("emergency",true);
                    intent.putExtra("symptom", symptom_Nm[position]); //????????? ??????
                    intent.putExtra("part", part_num[position]);
                    intent.putExtra("repeat",repeat);
                    startActivity(intent);
                    overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                    break;
                }
                case 4 : {  //??????
                    Log.e("her!", "4???");
                    intent = new Intent(getApplicationContext(), SelectBody_leg.class);
                    intent.putExtra("emergency",true);
                    intent.putExtra("symptom", symptom_Nm[position]); //????????? ??????
                    intent.putExtra("part", part_num[position]);
                    intent.putExtra("repeat",repeat);
                    startActivity(intent);
                    overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                    break;
                }
                case 5 : {  //???
                    Log.e("her!", "5???");
                    intent = new Intent(getApplicationContext(), SelectBody_back.class);
                    intent.putExtra("emergency",true);
                    intent.putExtra("symptom", symptom_Nm[position]); //????????? ??????
                    intent.putExtra("part", part_num[position]);
                    intent.putExtra("repeat",repeat);
                    startActivity(intent);
                    overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                    break;
                }
                case 6 : {  //??????
                    Log.e("her!", "6???");
                    intent = new Intent(getApplicationContext(), SelectBody_waist.class);
                    intent.putExtra("emergency",true);
                    intent.putExtra("symptom", symptom_Nm[position]);
                    intent.putExtra("part", part_num[position]);
                    intent.putExtra("repeat",repeat);
                    startActivity(intent);
                    overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                    break;
                }
                case 7 : {  //??????
                    Log.e("her!", "7???");
                    intent = new Intent(getApplicationContext(), SelectBody_chest.class);
                    intent.putExtra("emergency",true);
                    intent.putExtra("symptom", symptom_Nm[position]); //????????? ??????
                    intent.putExtra("part", part_num[position]);
                    intent.putExtra("repeat",repeat);
                    startActivity(intent);
                    overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                    break;
                }
                case 8 : {//??????
                    Log.e("her!", "8???");
                    intent = new Intent(getApplicationContext(), SelectBody_stomach.class);
                    intent.putExtra("emergency",true);
                    intent.putExtra("symptom", symptom_Nm[position]);
                    intent.putExtra("part", part_num[position]);
                    intent.putExtra("repeat",repeat);
                    startActivity(intent);
                    overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                    break;
                }
                case 9 : {  //?????????
                    Log.e("her!", "9???");
                    intent = new Intent(getApplicationContext(), SelectBody_buttock.class);
                    intent.putExtra("emergency",true);
                    intent.putExtra("symptom", symptom_Nm[position]); //????????? ??????
                    intent.putExtra("part", part_num[position]);
                    intent.putExtra("repeat",repeat);
                    startActivity(intent);
                    overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                    break;
                }
                case 11 : {  //??????
                    Log.e("her!", "11???");
                    intent = new Intent(getApplicationContext(), SelectBody_body.class);
                    intent.putExtra("emergency",true);
                    intent.putExtra("symptom", symptom_Nm[position]); //????????? ??????
                    intent.putExtra("part",part_num[position]);
                    intent.putExtra("repeat",repeat);
                    startActivity(intent);
                    overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                    break;
                }
                case 12 : {  //???
                    Log.e("her!", "12???");
                    intent = new Intent(getApplicationContext(), SelectBody_hand.class);
                    intent.putExtra("emergency",true);
                    intent.putExtra("symptom", symptom_Nm[position]); //????????? ??????
                    intent.putExtra("part", part_num[position]);
                    intent.putExtra("repeat",repeat);
                    startActivity(intent);
                    overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                    break;
                }
                case 13 : {  //???
                    Log.e("her!", "13???");
                    intent = new Intent(getApplicationContext(), SelectBody_foot.class);
                    intent.putExtra("emergency",true);
                    intent.putExtra("symptom", symptom_Nm[position]); //????????? ??????
                    intent.putExtra("part", part_num[position]);
                    intent.putExtra("repeat",repeat);
                    startActivity(intent);
                    overridePendingTransition(R.anim.translate_none,R.anim.translate_center_to_right);
                    break;
                }
            }

            finish();
        }
    };

    public void Click_search_btn(View v) {
        String search_content = search_text.getText().toString();

    }
    public String[] getIndex(int index, String dd){
        str[index] = dd;
        Log.d("sick", "symptomd: " + String.valueOf(str[index]));
        return str;
    }
    //?????? ??????(?????? edittext??? ????????? ????????? ???????????? ????????? ??????????????? ????????????)
    public void search(String content){
        list.clear();

        if(content.length()== 0){
            list.addAll(nameArr);
        }else{
            for(int i=0; i<nameArr.size(); i++){
                if(nameArr.get(i).toLowerCase().contains(content)||part_Nm[i].contains(content))
                {
                    list.add(nameArr.get(i));
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    //??? ?????? ????????? ?????? ????????? ??????
    public void settingList(){
        AssetManager assetManager = getAssets();
        try {
            InputStream is =assetManager.open("symptom.json"); //symptom.json ???????????? ????????????
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);

            StringBuffer buffer = new StringBuffer();

            String line = reader.readLine();

            while(line!=null){
                buffer.append(line+"\n");
                line=reader.readLine();
            }

            String jsonData = buffer.toString();

            JSONArray jsonArray= new JSONArray(jsonData);
            symptom_Nm = new String[jsonArray.length()];
            part_Nm= new String[jsonArray.length()];
            part_num= new int[jsonArray.length()];

            //listview ????????????
            for(int i=0; i<jsonArray.length();i++){
                jo=jsonArray.getJSONObject(i);

                //json ????????? ????????? ??????, ?????? ????????????
                String name= jo.getString("symptom");
                String spart= jo.getString("spart");
                int part = jo.getInt("part");

                symptom_Nm[i]=name;
                part_Nm[i]=spart;
                part_num[i]=part;

                list.add(name);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences sharedPreferences = getSharedPreferences(shared,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        repeat++;
        editor.putInt(todayDate,repeat);
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.translate_none,R.anim.translate_none);
    }
}