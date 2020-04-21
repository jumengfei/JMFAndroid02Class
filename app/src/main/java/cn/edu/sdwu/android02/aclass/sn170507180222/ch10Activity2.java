package cn.edu.sdwu.android02.aclass.sn170507180222;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ch10Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ch10_2);
    }

    public void send_broadcast(View view){
        //实现发送广播功能
        Intent intent=new Intent("com.inspur.broadcast");//实例化指定频道
        intent.putExtra("key1","message");

        sendBroadcast(intent);//发送广播
    }
}
