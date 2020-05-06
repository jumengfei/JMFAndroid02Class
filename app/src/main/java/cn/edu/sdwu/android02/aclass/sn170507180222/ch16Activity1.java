package cn.edu.sdwu.android02.aclass.sn170507180222;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;

import java.util.Arrays;

public class ch16Activity1 extends AppCompatActivity {
    private TextureView textureView;
    private SurfaceTexture surfaceTexture;
    private CameraDevice.StateCallback stateCallback;
    private CameraDevice cameraDevice;
    private CaptureRequest.Builder captureRequestBuilder;
    private CaptureRequest previewRequest;
    private CameraCaptureSession cameraCaptureSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //检查相机的使用权限
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int result=checkSelfPermission(Manifest.permission.CAMERA);
            if (result==PackageManager.PERMISSION_GRANTED){
                setCameraLayout();

            }else {
                requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},104);
            }
        }

        //实例化StateCallback，当打开相机时执行StateCallback(便于进行会话的创建）
        stateCallback= new CameraDevice.StateCallback() {
            @Override
            public void onOpened(@NonNull CameraDevice cameraDevice) {
                //摄像头打开后，执行本方法，可以获取 CameraDevice.对象
                ch16Activity1.this.cameraDevice=cameraDevice;
                //准备预览时使用的组件
                Surface surface=new Surface(surfaceTexture);
                //创建一个捕捉请求
                try {
                    captureRequestBuilder=cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                    captureRequestBuilder.addTarget(surface);
                    //创建一个相机捕捉会话
                    // 参数1代表后续预览或拍照使用的组件
                    //参数2代表是监听器，创建会话完成后执行的方法
                    cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
                        @Override
                        public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                            //会话创建完成后，可以在参数中得到会话对象
                            //开始显示相机的预览
                            ch16Activity1.this.cameraCaptureSession=cameraCaptureSession;
                            try {
                                previewRequest= captureRequestBuilder.build();
                                //在会话中发出重复请求，进行预览(捕获连续的图像）
                                cameraCaptureSession.setRepeatingRequest(previewRequest,null,null);
                            }catch (Exception e){
                                Log.e(ch16Activity1.this.toString(),e.toString());
                            }

                        }

                        @Override
                        public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {

                        }
                    },null);
                }catch (Exception e){

                }


            }

            @Override
            public void onDisconnected(@NonNull CameraDevice cameraDevice) {
                //摄像头关闭时，执行本方法。
                ch16Activity1.this.cameraDevice=null;

            }

            @Override
            public void onError(@NonNull CameraDevice cameraDevice, @IntDef(value = {CameraDevice.StateCallback.ERROR_CAMERA_IN_USE, CameraDevice.StateCallback.ERROR_MAX_CAMERAS_IN_USE, CameraDevice.StateCallback.ERROR_CAMERA_DISABLED, CameraDevice.StateCallback.ERROR_CAMERA_DEVICE, CameraDevice.StateCallback.ERROR_CAMERA_SERVICE}) int i) {

            }
        }
    }
    private void openCamera(int width,int height){
        //打开摄像头
        //使用getSystemService得到相机管理器
        CameraManager cameraManager=(CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            cameraManager.openCamera("0",stateCallback,null);//“0”代表后置摄像头，“1”代表前置摄像头
        }catch (Exception e){
            Log.e(ch16Activity1.this.toString(),e.toString());
        }


    }
    private void setCameraLayout(){
        //实现拍照功能

        //用户授权后，加载界面
        setContentView(R.layout.layout_ch16_1);
        textureView=(TextureView)findViewById(R.id.ch16_tv);
        //当 textureView准备好之后，自动调用.setSurfaceTextureListener的监听器
        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
                //当 textureView.可用时，打开摄像头
                ch16Activity1.this.surfaceTexture=surfaceTexture;
                openCamera(width,height);
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

            }
        });
    }

    public void call(View view){
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
            // 1.判断当前用户是否已经授权；
            int result=checkSelfPermission(Manifest.permission.CALL_PHONE);
            if (result== PackageManager.PERMISSION_GRANTED){
                callPhone();
            }else {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE},101);
            }
    }

  }
  public void chgOri(View view){
      //改变屏幕方向
      setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
      //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);竖屏
  }
  public void sms(View view){
      if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
          int result=checkSelfPermission(Manifest.permission.SEND_SMS);
          if (result==PackageManager.PERMISSION_GRANTED){
              sendSms();
          }else {
              requestPermissions(new String[]{Manifest.permission.SEND_SMS},102);
          }
      }
  }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==101){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                callPhone();
            }
        }
        if (requestCode==102){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                sendSms();
            }
        }
        if (requestCode==104){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                setCameraLayout();

            }
        }

    }

    private void sendSms(){
        //借助SmsManager工具进行发送
        SmsManager smsManager=SmsManager.getDefault();
        smsManager.sendTextMessage("19999999999","18888888888","short message test",null,null);
    }

    private void callPhone(){
          Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel://18899999999"));
        startActivity(intent);

      }
}







