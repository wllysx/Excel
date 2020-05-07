package com.wll.myproject.excel;

import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ResponseCache;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * Created by Administrator on 2017/3/7.\
 * 将数据导出成文excel文件
 * 使用sqlite创建一个本地的结果表  将这个结果表映射成为一个实体类 将整个实体类转化成excel表格
 */

//将内存中创建的实体类，保存为excel文件

public class ExpportDataBeExcel {
    public ExpportDataBeExcel() {

    }

    public void exportData(List<BeanExportData> datas){
        //需要导出的excel文件的文件名
        String fileName ="项目信息.xls";
        //操作excel的对象
        WritableWorkbook wwb = null;
        try {
            //根据当前的文件路径创建统计的文件并且实例化出一个操作excel的对象
            wwb = Workbook.createWorkbook(new File( Environment.getExternalStorageDirectory()+"/"+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (wwb != null ){
            //创建底部的选项卡  传参是选项卡的名称  和  选型卡的索引
            WritableSheet writableSheet = wwb.createSheet("2017年3月7日考勤",0);
            //创建excel的表头的信息
            String [] topic ={"姓名","年龄","日期","数据"};
            for (int i = 0 ; i<topic.length  ; i++ ){
                //横向的在单元格中填写数据
                Label labelC = new Label(i,0,topic[i]);
                try {
                    writableSheet.addCell(labelC);
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
            //从实体中遍历数据并将数据写入excel文件中
            BeanExportData account;
            ArrayList<String> li;
            if (datas != null) { for ( int j = 0 ; j < datas.size() ; j++ ){
                //将数据源列表中的数据整合成 一个个的字符串列表
                account = datas.get(j);
                li = new ArrayList<>();
                li.add(account.getNumber());
                li.add(account.getName());
                li.add(account.getAge());
                li.add(account.getData());
                int k = 0;
                for (String l:li){
                    //将单个的字符串列表横向的填入到excel表中
                    Label labelC = new Label(k,j+1,l);
                    k++;
                    try {
                        writableSheet.addCell(labelC);
                    } catch (WriteException e) {
                        e.printStackTrace();
                    }
                }
                li = null;
            }


            }

        }
        //将文件从内存写入到文件当中
        try {
            wwb.write();
            wwb.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }

    }
    }
