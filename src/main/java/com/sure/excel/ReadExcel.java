package com.sure.excel;

import com.sun.org.apache.xpath.internal.SourceTree;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by try on 2019/11/27.
 */
public class ReadExcel {
    public static void main(String[] args) throws Exception {
        ReadExcel obj = new ReadExcel();


        // 此处为我创建Excel路径：E:/zhanhj/studysrc/jxl下
        File file = new File("H:/3.xls");
        List excelList = obj.readExcel(file);

        Map<Key, List> map = new HashMap<>();

        for (int i = 1; i < excelList.size(); i++) {
            List list = (List) excelList.get(i);
            Key key = new Key((String)list.get(0), (String) list.get(1));
            if (map.containsKey(key)) {
                List first = map.get(key);
                for (int j = 2; j < first.size(); j++) {
                    if (((String)list.get(j)).isEmpty()) {
                        continue;
                    }
                    if (((String)first.get(j)).isEmpty()) {
                        first.set(j, list.get(j));
                    }

                    if (Double.valueOf((String) first.get(j)) < Double.valueOf((String) list.get(j))) {
                        first.set(j, list.get(j));
                    }
                }
                System.out.println(key.getHouseholdID());
            } else {
                map.put(new Key((String)list.get(0), (String) list.get(1)), list);
            }
        }

        obj.updateExcel(map);

        System.out.println("list中的数据打印出来");



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
                for (int i = 0; i < sheet.getRows(); i++) {
                    List innerList=new ArrayList();
                    //排除非失能
//                    if (!sheet.getCell(19, i).getContents().equals("1")) {
//                        continue;
//                    }
                    if (sheet.getCell(20, i).getContents().isEmpty() &&
                            sheet.getCell(21, i).getContents().isEmpty() &&
                            sheet.getCell(22, i).getContents().isEmpty() &&
                            sheet.getCell(23, i).getContents().isEmpty() &&
                            sheet.getCell(24, i).getContents().isEmpty() &&
                            sheet.getCell(25, i).getContents().isEmpty() &&
                            sheet.getCell(26, i).getContents().isEmpty() &&
                            sheet.getCell(27, i).getContents().isEmpty() &&
                            sheet.getCell(28, i).getContents().isEmpty() &&
                            sheet.getCell(29, i).getContents().isEmpty()) {
                        continue;
                    }
                    // sheet.getColumns()返回该页的总列数
                    String cellinfo = sheet.getCell(1, i).getContents();
                    innerList.add(cellinfo);
                    cellinfo = sheet.getCell(2, i).getContents();
                    innerList.add(cellinfo);
                    for (int j = 20; j <= 29; j++) {
                        cellinfo = sheet.getCell(j, i).getContents();
                        innerList.add(cellinfo);
                    }
                    outerList.add(innerList);
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



    public static void ModifyAndExport() {
        InputStream io;
        try {
            io = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\excels\\output0.xls"));
            HSSFWorkbook workbook = new HSSFWorkbook(io);
            HSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 2; i < 5; i++) {
                HSSFRow row = sheet.getRow(4);
                HSSFCell cell = row.getCell(0);
                cell.setCellValue("联系人姓名：王" + i + "麻子");
                HSSFCell cell1 = row.getCell(3);
                cell1.setCellValue("手机：" + i + "10110");
                String outputPath = "C:\\Users\\Administrator\\Desktop\\excels\\output" + i + ".xls";
                FileOutputStream fo = new FileOutputStream(new File(outputPath));
                workbook.write(fo);
            }
            workbook.close();
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * 只是一个demo，这里假设修改的值是String类型
     * @throws Exception
     */
    public void updateExcel(Map<Key, List> map)throws Exception{
        int cnt = 0;
        FileInputStream fis=new FileInputStream("H:/4.xls");
        HSSFWorkbook workbook=new HSSFWorkbook(fis);
//        workbook.
        HSSFSheet sheet=workbook.getSheetAt(0);

        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            String householdID = sheet.getRow(i).getCell(1).getStringCellValue();
            String communityID = sheet.getRow(i).getCell(2).getStringCellValue();
            Key key = new Key(householdID, communityID);
            if (!map.containsKey(key)) {
                    cnt++;
                    continue;
            }
            List list = map.get(key);
            for (int j = 2; j < list.size(); j++) {
                HSSFCell cell = sheet.getRow(i).getCell(j + 18);
                if (((String) list.get(j)).isEmpty()) {
                    continue;
                }
                cell.setCellValue(Integer.valueOf((String) list.get(j)));
            }

        }
        System.out.println("没有数据个数" + cnt);
        fis.close();//关闭文件输入流

        FileOutputStream fos=new FileOutputStream("H:/4.xls");
        workbook.write(fos);
        fos.close();//关闭文件输出流

    }


    private String getCellValue(HSSFCell cell) {
        String cellValue = "";
        DecimalFormat df = new DecimalFormat("#");
        switch (cell.getCellType()) {
            case XSSFCell.CELL_TYPE_STRING:
                cellValue = cell.getRichStringCellValue().getString().trim();
                break;
            case XSSFCell.CELL_TYPE_NUMERIC:
                cellValue = df.format(cell.getNumericCellValue()).toString();
                break;
            case XSSFCell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
                break;
            case XSSFCell.CELL_TYPE_FORMULA:
                cellValue = cell.getCellFormula();
                break;
            default:
                cellValue = "";
        }
        return cellValue;
    }

}
