package cn.edu.sdwu.android02.aclass.sn170507180222;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
    public void ch10Activity(View view){
        //实现页面跳转
        Intent intent=new Intent(this,ch10Activity1.class);
        EditText editText=(EditText)findViewById(R.id.ch10_2_et);
        intent.putExtra("text",editText.getText().toString());//设置传递的数据
        startActivity(intent);
    }

    public void StartSubActivity(View view){
        //（1）以Sub-Activity的方式启动子Activity
        //显示启动
        Intent intent=new Intent(this,ch10Activity3.class);
        startActivityForResult(intent,101);
        //（2）设置子Activity的返回值 代码在ch10Activity3
        //（3）在父Activity中获取返回值


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //第一个参数为请求码，与 请求的时候保持一致
        //（3）在父Activity中获取返回值
        //requestCode用来区分哪一个子Activity返回的结果
        if(requestCode==101){
            if(resultCode==RESULT_OK){
                //用户点击确认按钮，进一步获取数据
                String name=data.getStringExtra("name");
                Toast.makeText(this,name,Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this,"cancel",Toast.LENGTH_LONG).show();
            }

        }else if (requestCode==102){
            //从联系人列表返回的结果
            if(resultCode==RESULT_OK){
                //得到联系人的信息(仅有联系人的编号，lookup uri)
                String content=data.getDataString();
                Toast.makeText(this,content,Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"cancel",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void web(View view){
        //使用隐式启动方式打开网页
        //两个参数分别是需要执行的动作 和传递的参数
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://baidu.com"));
        startActivity(intent);
    }

    public void contactsList(View view){
        //使用隐式启动打开通讯录
        Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("content://contacts/people/"));
        startActivity(intent);
    }

    public void contactsDetail(View view){
        //使用隐式启动查看联系人的详细信息
        Intent intent=new Intent(Intent.ACTION_EDIT);
        intent.setData(Uri.parse("content://contacts/people/1"));
        startActivity(intent);
    }

    public void showMap(View view){
        //使用隐式启动打开地图
        Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("geo:50.123,7.1434"));//两个浮点数分别表示经度和维度
    }

    public void showPhoto(View view){
        //使用隐式启动打开相册
        Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("content://media/external/images/media/"));
        startActivity(intent);
    }

    public void pickContact(View view){
        //使用隐式启动的方法。
        // 以子Activity的形式，打开联系人列表，让用户选择一个联系人再返回结果。
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setData(ContactsContract.Contacts.CONTENT_URI);//传入数据
        startActivityForResult(intent,102);//第二个参数为请求码
    }
    public void implicitStart(View view){
        //用过滤器方法实现跳转到上一个界面
        Intent intent=new Intent("com.inspur.action2");
        intent.setData(Uri.parse("abc://inspur.com"));
        startActivity(intent);
    }
}
