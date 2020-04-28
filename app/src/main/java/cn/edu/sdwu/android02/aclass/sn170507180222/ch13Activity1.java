package cn.edu.sdwu.android02.aclass.sn170507180222;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.jar.Manifest;

public class ch13Activity1 extends AppCompatActivity {
    private EditText ip;
    private EditText port;
    private InputStream inputStream;

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
    public void readRaw(View view){
        //读取Raw目录下的文件内容
        Resources resources=getResources();
        InputStream inputStream=resources.openRawResource(R.raw.readme);
        try {
            int size=inputStream.available();
            byte[] bytes=new byte[size];
            inputStream.read(bytes);
            String content=new String(bytes);
            Toast.makeText(this,content,Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Log.e(ch13Activity1.class.toString(),e.toString());
        }finally {
            try {
                inputStream.close();
            }catch (Exception e){
                Log.e(ch13Activity1.class.toString(),e.toString());
            }
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


    // 3.接收用户的授权结果；
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //4.如果用户同意，则进行下一步操作（写SD卡）
                EditText editText = (EditText) findViewById(R.id.ch13_1_et);
                String content = editText.getText().toString();
                writeExternal(content);
            }
        }
        if(requestCode==102){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                readExternal();
            }
        }

    };
    public void writeSd(View view){
            //在SD卡写入内容
            EditText editText = (EditText) findViewById(R.id.ch13_1_et);
            String content = editText.getText().toString();
            //对于6.0之后的系统，用户需要在运行时进行动态授权
            //动态授权的过程一般分为：
            // 1.判断当前用户是否已经授权；
            // 2.如果尚未授权，弹出动态授权的对话框（同意或者拒绝）；
            // 3.接收用户的授权结果；
            //4.如果用户同意，则进行下一步操作（写SD卡）

            //判断当前用户手机系统版本
            if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
                // 1.判断当前用户是否已经授权；
                int result=checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (result== PackageManager.PERMISSION_GRANTED){
                    writeExternal(content);
                }else {
                    // 2.如果尚未授权，弹出动态授权的对话框（同意或者拒绝）；
                    requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},101);
                }
            }else {
                writeExternal(content);
            }

        }
        private void writeExternal(String content){
            //写入内部存储
            //得到FileOutputStream的方法，与内部存储不同
            FileOutputStream fileOutputStream =null;
            //（1）创建File对象
            //Environment.getExternalStorageDirectory()这个方法直接得到SD的目录名
            File file=new File(Environment.getExternalStorageDirectory(),"abcd.txt");//构造方法中，提供文件所在的目录名和文件名
           
            try {
                //使用createNewFile创建文件
                file.createNewFile();
                if (file.exists()&&file.canWrite()){
                    //判断文件是否存在和可写
                    fileOutputStream=new FileOutputStream(file);
                    fileOutputStream.write(content.getBytes());
                }
            } catch (Exception e) {
                //e.printStackTrace();
                Log.e(ch13Activity1.class.toString(),e.toString());//如果有异常输出
            }
            if(fileOutputStream!=null){
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException ee) {
                    //e.printStackTrace();
                    Log.e(ch13Activity1.class.toString(),ee.toString());//如果有异常输出
                }
               
            }

        }
        public void readSd(View view){
            if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
                // 1.判断当前用户是否已经授权；
                int result= checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE);
                if (result== PackageManager.PERMISSION_GRANTED){
                   readExternal();
                }else {
                    // 2.如果尚未授权，弹出动态授权的对话框（同意或者拒绝）；
                    requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},102);
                }
            }else {
               readExternal();
            }


        }
        private void readExternal(){
            File file=new File(Environment.getExternalStorageDirectory(),"abcde.txt");
            FileInputStream fileInputStream=null;
            try {
                if (file.exists()&&file.canRead()){
                    fileInputStream=new FileInputStream(file);
                    int size=fileInputStream.available();
                    byte[] bytes=new byte[size];
                    fileInputStream.read(bytes);
                    Toast.makeText(this,new String(bytes),Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){
                try {
                    fileInputStream.close();
                } catch (IOException e1) {
                    //e1.printStackTrace();
                    Log.e(ch13Activity1.class.toString(),e.toString());
                }

            }

        }
    }


