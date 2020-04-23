package cn.edu.sdwu.android02.aclass.sn170507180222;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ch12Activity1 extends AppCompatActivity {
    private ServiceConnection serviceConnection;//服务链接(属于接口interface)
    private MyService2 myService2;
    private boolean bindSucc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ch12_1);

        serviceConnection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                //当调用者与服务建立起连接后会自动调用本方法
                //第二个参数是service中onBind方法的返回值
                MyService2.MyBinder myBinder=(MyService2.MyBinder)iBinder;
                myService2=myBinder.getRandomService();
                bindSucc=true;

            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                //当调用者与服务断开连接后会自动调用本方法
                bindSucc=false;

            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent=new Intent(this,MyService2.class);
        //绑定
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);//第二个参数为服务链接
    }

    @Override
    protected void onStop() {
        super.onStop();

        //解绑
        unbindService(serviceConnection);
    }

    public void start_service(View view){
        //使用intent启动服务
        Intent intent=new Intent(this,MyService.class);
        //使用startSercice以启动方式使用服务
        startService(intent);
    }
    public void stop_service(View view){
        //停止服务
        Intent intent=new Intent(this,MyService.class);
        stopService(intent);
    }
   public void getRandom (View view){
       if(bindSucc){
          int ran= myService2.genRandom();
           Toast.makeText(this,ran+"",Toast.LENGTH_LONG).show();
       }
   }
}
