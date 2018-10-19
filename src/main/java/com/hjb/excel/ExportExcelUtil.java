package com.hjb.excel;

import com.hjb.excel.model.ExcelData;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder;

import java.io.FileOutputStream;
import java.util.List;

/**
 * 简单的excel（xlsx格式）导出实现,保存到本地文件，返回文件路径
 *
 * @author: Hu Jianbo
 * @date: 2018/10/11 0011 下午 21:08
 */
public class ExportExcelUtil {

    /**
     * 文件存放目录
     */
    private static final String uploadPath = "/root/uploads/";

    public static String exportExcel(ExcelData data, String fileName) throws Exception {

        XSSFWorkbook wb = new XSSFWorkbook();
        try {
            String sheetName = data.getName() == null ? "Sheet1" : data.getName();
            XSSFSheet sheet = wb.createSheet(sheetName);
            writeExcel(wb, sheet, data);

            String filepath = uploadPath + System.currentTimeMillis() + fileName;
            FileOutputStream fout = new FileOutputStream(filepath);

            try {
                wb.write(fout);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                fout.close();
            }

            return filepath;
        } finally {
            wb.close();
        }
    }

    private static void writeExcel(XSSFWorkbook wb, Sheet sheet, ExcelData data) {
        int rowIndex = writeTitlesToExcel(wb, sheet, data.getTitles());
        writeRowsToExcel(wb, sheet, data.getRows(), rowIndex);
        autoSizeColumns(sheet, data.getTitles().size() + 1);
    }

    private static int writeTitlesToExcel(XSSFWorkbook wb, Sheet sheet, List<String> titles) {
        Font titleFont = buildTitleFont(wb);
        XSSFCellStyle titleStyle = buildXSSFCellStyle(wb, titleFont);
        setBorder(titleStyle, BorderStyle.THIN, new XSSFColor(new java.awt.Color(0, 0, 0)));
        return createTitleRow(sheet, titles, titleStyle);
    }

    private static Font buildTitleFont(XSSFWorkbook wb) {
        Font titleFont = wb.createFont();
        titleFont.setFontName("simsun");
        titleFont.setBold(true);
        titleFont.setColor(IndexedColors.BLACK.index);
        return titleFont;
    }

    private static Font buildDataFont(XSSFWorkbook wb) {
        Font titleFont = wb.createFont();
        titleFont.setFontName("simsun");
        titleFont.setColor(IndexedColors.BLACK.index);
        return titleFont;
    }

    private static XSSFCellStyle buildXSSFCellStyle(XSSFWorkbook wb, Font titleFont) {
        XSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(182, 184, 192)));
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setFont(titleFont);
        return titleStyle;
    }

    private static int createTitleRow(Sheet sheet, List<String> titles, XSSFCellStyle titleStyle) {
        int rowIndex = 0;
        int colIndex = 0;
        Row titleRow = sheet.createRow(rowIndex);

        for (String field : titles) {
            Cell cell = titleRow.createCell(colIndex);
            cell.setCellValue(field);
            cell.setCellStyle(titleStyle);
            colIndex++;
        }

        rowIndex++;
        return rowIndex;
    }

    private static int writeRowsToExcel(XSSFWorkbook wb, Sheet sheet, List<List<Object>> rows, int rowIndex) {
        Font dataFont = buildDataFont(wb);
        XSSFCellStyle dataStyle = buildXSSFCellStyle(wb, dataFont);

        for (List<Object> rowData : rows) {
            createDataRow(sheet, rowIndex, rowData, dataStyle);
            rowIndex++;
        }
        return rowIndex;
    }

    private static void createDataRow(Sheet sheet, int rowIndex, List<Object> rowData, XSSFCellStyle dataStyle) {
        Row dataRow = sheet.createRow(rowIndex);
        int colIndex = 0;
        for (Object cellData : rowData) {
            Cell cell = dataRow.createCell(colIndex);
            cell.setCellValue(cellData == null ? "" : cellData.toString());
            cell.setCellStyle(dataStyle);
            colIndex++;
        }
    }

    private static void autoSizeColumns(Sheet sheet, int columnNumber) {
        for (int i = 0; i < columnNumber; i++) {
            int orgWidth = sheet.getColumnWidth(i);
            sheet.autoSizeColumn(i, true);
            int newWidth = (int) (sheet.getColumnWidth(i) + 100);
            if (newWidth > orgWidth) {
                sheet.setColumnWidth(i, newWidth);
            } else {
                sheet.setColumnWidth(i, orgWidth);
            }
        }
    }

    private static void setBorder(XSSFCellStyle style, BorderStyle border, XSSFColor color) {
        style.setBorderTop(border);
        style.setBorderLeft(border);
        style.setBorderRight(border);
        style.setBorderBottom(border);
        style.setBorderColor(XSSFCellBorder.BorderSide.TOP, color);
        style.setBorderColor(XSSFCellBorder.BorderSide.LEFT, color);
        style.setBorderColor(XSSFCellBorder.BorderSide.RIGHT, color);
        style.setBorderColor(XSSFCellBorder.BorderSide.BOTTOM, color);
    }
}
