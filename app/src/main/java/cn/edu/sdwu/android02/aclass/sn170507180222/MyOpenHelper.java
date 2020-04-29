package cn.edu.sdwu.android02.aclass.sn170507180222;
//创建数据库
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by a on 2020/4/29.
 */

public class MyOpenHelper extends SQLiteOpenHelper {
    private static final String STUDENT_TB_SQL="create table student(id integer primary key autoincrement,stuname text,stutel text)";
   public MyOpenHelper(Context context){
       //super的构造方法：
       // Context context上下文 String name 数据库名称 CursorFactory factory传入NULL,int version 数据库的版本
        super(context,"stud.db",null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //当打开数据库时，发现数据库并不存在，此时会自动创建数据库，然后调用Oncreate方法
        //在onCreate方法，完成数据库对象（表、视图、索引等）的创建
        sqLiteDatabase.execSQL(STUDENT_TB_SQL);
        Log.i(MyOpenHelper.class.toString(),"onCreate");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
