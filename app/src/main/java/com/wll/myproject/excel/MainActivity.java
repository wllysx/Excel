package com.wll.myproject.excel;

import androidx.annotation.Nullable;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;


public class MainActivity extends BaseActivity {
    ExpportDataBeExcel expportDataBeExcel;
    ImportDataFromExcel importDataFromExcel;
    String mgson;
    List<BeanExportData> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        expportDataBeExcel = new ExpportDataBeExcel();
        importDataFromExcel = new ImportDataFromExcel();
    }

    public void putIn(View view) {
        //导入格式为 .xls .xlsx
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/*");//设置类型
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult( intent, 1 );
    }

    public void putOut(View view) {
        expportDataBeExcel.exportData( mList );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (resultCode == RESULT_OK && data != null) {
            Log.i( "wll11", "选择的文件Uri = " + data.toString() );
            //通过Uri获取真实路径
            final String excelPath = getRealFilePath( this, data.getData() );
            Log.i( "wll11", "excelPath = " + excelPath );//    /storage/emulated/0/test.xls
            if (excelPath.contains( ".xls" ) || excelPath.contains( ".xlsx" )) {
                Toast.makeText( this, "正在加载", Toast.LENGTH_SHORT ).show();
                //载入excel
                mgson= readExcel( excelPath );
                Gson gson = new Gson();
                Type type=new TypeToken<List<BeanExportData>>(){}.getType();
                mList =gson.fromJson( mgson, type);
            } else {
                Toast.makeText( this, "此文件不是excel", Toast.LENGTH_SHORT ).show();
            }
        }

    }

    //读取Excel表
    private String readExcel(String excelPath) {

        //json格式的字符串。
        String mjson= importDataFromExcel.ImportExcelData(excelPath);
        Toast.makeText( this, "可能你不太相信，但是他真的导入成功了。。", Toast.LENGTH_SHORT ).show();
        Log.i( "wll1","卧槽："+ mjson );
        return mjson;
    }


    /**
     * 根据Uri获取真实图片路径
     * <p/>
     * 一个android文件的Uri地址一般如下：
     * content://media/external/images/media/62026
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }



}
