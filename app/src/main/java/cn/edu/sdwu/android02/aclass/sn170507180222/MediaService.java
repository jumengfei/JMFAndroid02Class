package cn.edu.sdwu.android02.aclass.sn170507180222;
//布局文件：layout_ch12_2.xml
//activity:ch12Activity2

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;
import android.view.View;

public class MediaService extends Service {
    private MediaPlayer mediaPlayer;
    private MyBinder myBinder;
    public MediaService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer=MediaPlayer.create(this,R.raw.wav);
        mediaPlayer.setLooping(false);
        myBinder=new MyBinder();
        Log.i(MediaService.class.toString(),"onCreate");


    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        //实现播放器功能
        Log.i(MediaService.class.toString(),"onStartCommand");
        //从Intent中获取用户需要的操作，然后进一步处理
        String state=intent.getStringExtra("PlayerState");
        if(state!=null){
            if(state.equals("START")){//播放
                start();
            }
            if(state.equals("PAUSE")){//暂停
                pause();
            }
            if(state.equals("STOP")){//停止播放
                stop();
            }
            if(state.equals("STOPSERVICE")){//停止服务
                stopSelf();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }
    public void start(){
        mediaPlayer.start();
    }

    public void pause(){
        if(mediaPlayer.isPlaying()){
            //判断播放器是否正在播放
            mediaPlayer.pause();
        }
    }
    public void stop(){
        mediaPlayer.stop();
        //为了下一次播放，需要调用prepare方法
        try {
            mediaPlayer.prepare();
        }catch (Exception e){
            Log.e(MediaService.class.toString(),e.toString());
        }
    }

    @Override
    public void onDestroy() {
        Log.i(MediaService.class.toString(),"onDestroy");
        mediaPlayer.stop();//停止播放
        mediaPlayer.release();//释放资源
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(MediaService.class.toString(),"onBind");
        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(MediaService.class.toString(),"onUnBind");
        return super.onUnbind(intent);
    }

    public class MyBinder extends Binder{
        //内部类
        public MediaService getMediaService(){
            return MediaService.this;
        }

    }
}
