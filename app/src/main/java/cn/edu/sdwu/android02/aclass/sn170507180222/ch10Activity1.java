package cn.edu.sdwu.android02.aclass.sn170507180222;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ch10Activity1 extends AppCompatActivity {
    private Integer count;//点击按键的计数器

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.i(ch10Activity1.class.toString(),"onCreate");
        setContentView(R.layout.layout_ch10_1);
        count=0;//按键计数器的初始值为0

    }

    public void finishClick(View view){
        //关闭界面的相关操作
        finish();
    }

    //对于后退 回到主界面等操作会经历哪几个过程
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ch10Activity1.class.toString(),"onStart");

    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ch10Activity1.class.toString(),"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ch10Activity1.class.toString(),"onDestroy");
    }

    @Override
    protected void onPause() {
        //一般用于保存持久性的数据，并将数据保存在存储设备上的文件系统或数据库系统中
        super.onPause();
        Log.i(ch10Activity1.class.toString(),"onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ch10Activity1.class.toString(),"onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(ch10Activity1.class.toString(),"onRestart");
    }

    //关于按键计数的方法
    protected void counter(View view){
        count++;
        Log.i(ch10Activity1.class.toString(),"counter:"+count);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //使用本方法保存一些界面的状态信息
        //用于保存动态的状态信息，信息一般保存在Bundle中
        outState.putInt("counter",count);
        super.onSaveInstanceState(outState);
        //在onPause和onStop之间实行
        Log.i(ch10Activity1.class.toString(),"onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //恢复之前保存的状态信息
        //在onStart和onResume之间实行的
        count=savedInstanceState.getInt("counter");
        Log.i(ch10Activity1.class.toString(),"onRestoreInstanceState");
    }
}
