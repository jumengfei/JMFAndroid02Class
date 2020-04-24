package cn.edu.sdwu.android02.aclass.sn170507180222;
//布局文件：layout_ch12_1

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

public class MyService2 extends Service {
    //实现生成随机数的功能

    private Random random;
    private MyBinder myBinder;

    @Override
    public void onCreate() {
        super.onCreate();
        random=new Random();//实例化
        myBinder=new MyBinder();
    }

    public MyService2() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        //返回值会返回给调用者。以后调用者与服务进行交互，都会使用此返回值
        // TODO: Return the communication channel to the service.
        Log.i(MyService2.class.toString(),"onBind");
       return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(MyService2.class.toString(),"onUBind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        //当所有调用者都解绑后销毁
        Log.i(MyService2.class.toString(),"onDestroy");
        super.onDestroy();
    }

    public class MyBinder extends Binder{
        public MyService2 getRandomService(){
            return MyService2.this;
        }
    }

    //实际与调用者进行交互的方法
    public int genRandom(){
        return random.nextInt();
    }
}
