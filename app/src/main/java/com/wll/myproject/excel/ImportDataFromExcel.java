package com.wll.myproject.excel;
/*
    Create by WLL on 2020/3/28 DATA: 12:26
*/

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ImportDataFromExcel {
    //将excel文件导入到内存中
    private List<BeanExportData> datas;
    public String ImportExcelData(String excelPath){
        datas = new ArrayList<>();
        Workbook workbook = null;
//        String fileName ="考情统计.xls";
        try {

            workbook = Workbook.getWorkbook( new File( excelPath ) );
            Sheet sheet = workbook.getSheet(0);
            int rows = sheet.getRows();
            Log.i( "wll1", "row:" + rows );
            int columns = sheet.getColumns();
            Log.i( "wll1", "colums:" + columns );
            //遍历excel文件的每行每列
            for (int i=0; i < rows ;i++){
                //遍历行
                Log.i( "wll1", "卧槽走了吗？？？" );
                List<String> li = new ArrayList<>();
                for (int j = 0 ; j < columns ; j++ ){
                    Cell cell = sheet.getCell(j,i);
                    String result = cell.getContents();
                    if (i!=0){
                        li.add(result);
                    }
                }
                if (li.size()>0){
                    datas.add(new BeanExportData(li.get(0),li.get(1),li.get(2),li.get(3)));
                }
                li = null;
            }
            Gson gson = new Gson();
            return gson.toJson(datas);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        return "error";
    }
}
