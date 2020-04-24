package cn.edu.sdwu.android02.aclass.sn170507180222;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ch13Activity1 extends AppCompatActivity {
    private EditText ip;
    private EditText port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ch13_1);

        ip=(EditText)findViewById(R.id.ch13_1_ip);
        port=(EditText)findViewById(R.id.ch13_1_port);

        //简单存储的读取功能（直接显示在桌面上）
        SharedPreferences sharedPreferences=getSharedPreferences("prefs",MODE_PRIVATE);
        ip.setText(sharedPreferences.getString("ip",""));
        port.setText(sharedPreferences.getString("port",""));
    }

    public void write(View view) {
        //把文本框的内容以文件形式存储
        EditText editText = (EditText) findViewById(R.id.ch13_1_et);
        String content = editText.getText().toString();//得到文本框输入的内容
        try {
            FileOutputStream fileOutputStream = openFileOutput("android02.text",MODE_APPEND); //有两个参数分别：文件名 访问模式

            fileOutputStream.write(content.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            Log.e(ch13Activity1.class.toString(),e.toString());
        }

    }
        public void read(View view){
            //将写入文本框的内容读取出来
            try{
                FileInputStream fileInputStream=openFileInput("android02.txt");

                int size=fileInputStream.available();
                byte[] bytes=new byte[size];
                fileInputStream.read(bytes);
                String content=new String(bytes);

                fileInputStream.close();
                Toast.makeText(this,content,Toast.LENGTH_LONG).show();
            }catch (Exception e){
                Log.e(ch13Activity1.class.toString(),e.toString());
            }
        }

        public void saveSharePref(View view){
            //简单存储
            SharedPreferences sharedPreferences=getSharedPreferences("prefs",MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("ip",ip.getText().toString());
            editor.putString("port",port.getText().toString());
            editor.commit();
        }
    }


