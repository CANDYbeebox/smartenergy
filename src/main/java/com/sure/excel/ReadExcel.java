package com.sure.excel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Created by SURE on ${DATA}.
 */
public class ReadExcel {
    public static void main(String[] args) throws IOException {
        ReadExcel obj = new ReadExcel();
        // 此处为我创建Excel路径：E:/zhanhj/studysrc/jxl下
        File file = new File("E:/read1.xls");
        List excelList = obj.readExcel(file);
        System.out.println("list中的数据打印出来");
        int[][] cnt = new int[29][5];
        for (int i = 0; i < excelList.size(); i++) {
            List list = (List) excelList.get(i);
            for (int j = 0; j < list.size(); j++) {
                cnt[i][Integer.valueOf((String) list.get(j)) - 1]++;
                System.out.print(list.get(j));
            }
            System.out.println();
        }
        //写
        //创建excel文件
        File NewxlsFile = new File("E:/wwww.xls");
        // 创建一个工作簿
        XSSFWorkbook Newworkbook = new XSSFWorkbook();
        // 创建一个工作表
        XSSFSheet Newsheet = Newworkbook.createSheet("sheet1");
        // 将数据填入新的表格中
        for (int row = 0; row < cnt.length; ++row) {
            //创建行
            XSSFRow Newrows = Newsheet.createRow(row);
            for (int col = cnt[0].length - 1; col >= 0; --col) {
                //按列写入
                Newrows.createCell(cnt[0].length - 1 - col).setCellValue(cnt[row][col]);
            }
            //将excel写入
            FileOutputStream fileOutputStream = new FileOutputStream(NewxlsFile);
            Newworkbook.write(fileOutputStream);
        }
        System.out.println("end");

    }
    // 去读Excel的方法readExcel，该方法的入口参数为一个File对象
    public List readExcel(File file) {
        try {
            // 创建输入流，读取Excel
            InputStream is = new FileInputStream(file.getAbsolutePath());
            // jxl提供的Workbook类
            Workbook wb = Workbook.getWorkbook(is);
            // Excel的页签数量
            int sheet_size = wb.getNumberOfSheets();
            for (int index = 0; index < sheet_size; index++) {
                List<List> outerList=new ArrayList<List>();
                // 每个页签创建一个Sheet对象
                Sheet sheet = wb.getSheet(index);
                // sheet.getRows()返回该页的总行数
                for (int i = 0; i < sheet.getColumns(); i++) {
                    List innerList=new ArrayList();
                    // sheet.getColumns()返回该页的总列数
                    for (int j = 0; j < sheet.getRows(); j++) {
                        String cellinfo = sheet.getCell(i, j).getContents();
                        if(cellinfo.isEmpty()){
                            continue;
                        }
                        innerList.add(cellinfo);
//                        System.out.print(cellinfo);
                    }
                    outerList.add(i, innerList);
//                    System.out.println();
                }
                return outerList;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
