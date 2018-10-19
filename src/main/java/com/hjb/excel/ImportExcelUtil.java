package com.hjb.excel;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 简单的excel（xlsx）导入，返回{field：value}的map集合
 *
 * @author: Hu Jianbo
 * @date: 2018/10/16 0016 下午 21:06
 */
public class ImportExcelUtil {

    public static List<Map<String, Object>> importExcel(InputStream in, Object obj) throws Exception {

        Workbook hw = new XSSFWorkbook(in);
        Sheet sheet = hw.getSheetAt(0);

        List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                ret.add(dataObj(obj, row));
            }
        }
        return ret;
    }

    private static Map<String, Object> dataObj(Object obj, Row row) throws Exception {
        Class<?> rowClazz = obj.getClass();
        Field[] fields = FieldUtils.getAllFields(rowClazz);
        if (fields == null || fields.length < 1) {
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();

        // 接数据的对象属性要跟excel的列顺序一致
        for (int j = 0; j < fields.length; j++) {
            map.put(fields[j].getName(), getVal(row.getCell(j)));
        }
        return map;
    }

    public static String getVal(Cell cell) {
        if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
            return cell.getStringCellValue();
        } else {
            return String.valueOf(cell.getNumericCellValue());
        }
    }
}