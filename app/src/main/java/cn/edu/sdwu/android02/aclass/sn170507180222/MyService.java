package cn.edu.sdwu.android02.aclass.sn170507180222;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

public class MyService extends Service {
    private MediaPlayer mediaPlayer;
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化播放器
        mediaPlayer=MediaPlayer.create(this,R.raw.wav);
        mediaPlayer.setLooping(true);//是否循环播放
        Log.i(MyService.class.toString(),"oncreate");

    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        //主要业务写在本方法中
        mediaPlayer.start();//开始播放
        Log.i(MyService.class.toString(),"onStartCommand");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();//停止播放
        mediaPlayer.release();//释放资源
        Log.i(MyService.class.toString(),"onDestroy");
        super.onDestroy();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
