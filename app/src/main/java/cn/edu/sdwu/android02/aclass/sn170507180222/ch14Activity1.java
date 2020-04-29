package cn.edu.sdwu.android02.aclass.sn170507180222;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ch14Activity1 extends AppCompatActivity {
    private MyOpenHelper myOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ch14_1);

        myOpenHelper=new MyOpenHelper(this);



    }
    public void insert(View view){
        //插入数据
        //以可写方式打开数据库(如果数据库不存在，自动创建数据库）
        SQLiteDatabase sqLiteDatabase=myOpenHelper.getWritableDatabase();
        try {
            //将插入的数据放置在  ContentValues中
            //事务的处理
            sqLiteDatabase.beginTransaction();//开启事务
            ContentValues contentValues=new ContentValues();
            contentValues.put("stuname","Tom");
            contentValues.put("stutel","18899999999");
            sqLiteDatabase.insert("student",null,contentValues);

            //所有操作结束后，调用setTransactionSuccessful方法，才会将数据保存到数据库中
            sqLiteDatabase.setTransactionSuccessful();
        }catch (Exception e){
            Log.e(ch14Activity1.class.toString(),e.toString());
        }finally {
            sqLiteDatabase.endTransaction();//结束事务
            //使用完毕，将数据库关闭
            sqLiteDatabase.close();
        }
    }

    public void query(View view){
        SQLiteDatabase sqLiteDatabase=myOpenHelper.getReadableDatabase();//只读方式打开
        try {
            //cursor 游标
            Cursor cursor=sqLiteDatabase.rawQuery("select * from student where stuname=?",new String[]{"Tom"} );
            while (cursor.moveToNext()){
               int id= cursor.getInt(cursor.getColumnIndex("id"));
                String stuname=cursor.getString(cursor.getColumnIndex("stuname"));
                String stutel=cursor.getString(cursor.getColumnIndex("stutel"));
                Log.i(ch14Activity1.class.toString(),"id:"+id+",stuname:"+stuname+",stutel:"+stutel);
            }
        }catch (Exception e){
            Log.e(ch14Activity1.class.toString(),e.toString());
        }finally {
            //关闭数据库
            sqLiteDatabase.close();
        }
    }

    public void delete(View view){
        SQLiteDatabase sqLiteDatabase=myOpenHelper.getWritableDatabase();
        try{
            sqLiteDatabase.beginTransaction();
            sqLiteDatabase.delete("student","id=?",new String[]{"1"});
            sqLiteDatabase.setTransactionSuccessful();
        }catch (Exception e){
            Log.e(ch14Activity1.class.toString(),e.toString());
        }finally {
            sqLiteDatabase.endTransaction();//结束事务
            //使用完毕，将数据库关闭
            sqLiteDatabase.close();
        }
    }

    public void modify(View view){
        SQLiteDatabase sqLiteDatabase=myOpenHelper.getWritableDatabase();
        try {
            sqLiteDatabase.beginTransaction();

            ContentValues contentValues=new ContentValues();
            contentValues.put("stutel","18899999999");

            sqLiteDatabase.update("stdent",contentValues,"id=?",new String[]{"2"});
            sqLiteDatabase.setTransactionSuccessful();
        }catch (Exception e){
            Log.e(ch14Activity1.class.toString(),e.toString());
        }finally {
            sqLiteDatabase.endTransaction();//结束事务
            //使用完毕，将数据库关闭
            sqLiteDatabase.close();
        }
    }

}
