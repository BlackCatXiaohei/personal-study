package org.personal.blackcat.util;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

/**
 * @author lifan.li
 * @since 2018/5/21
 **/
@Slf4j
public class ExcelFileProcessor {

    private final static String XLS = ".xls";

    private final static String XLSX = ".xlsx";

    public static final String SPLIT = "|";

    /**
     * a static method that read excel file
     * @param filePath      file path of excel
     * @return              value of excel as Two-dimensional Array
     */
    public static List<String> read(String filePath) {
        if (filePath.endsWith(XLSX)) {
            return readXSS(filePath);
        } else if (filePath.endsWith(XLS)) {
            return null;
        } else {
            throw new RuntimeException("this profile is not an excel file!");
        }
    }

    private static List<String> readXSS(String filePath) {
        List<String> result = Lists.newArrayList();

        XSSFWorkbook workbook;
        try {
            workbook = new XSSFWorkbook(filePath);
        } catch (IOException e) {
            log.error("can not found file, check path-", e);
            throw new RuntimeException(e);
        }

        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFRow row;
        XSSFCell cell;


        int rows = sheet.getPhysicalNumberOfRows();
        log.info("this excel file had {} rows", rows);

        int cols = 0;
        int tmp;

        for (int i=0; i<rows; i++) {
            row = sheet.getRow(i);
            if (row != null) {
                tmp = sheet.getRow(i).getPhysicalNumberOfCells();
                if (tmp > cols) {
                    cols = tmp;
                }
            }
        }

        for (int j=0; j<rows; j++) {
            row = sheet.getRow(j);
            if (row != null) {
                StringBuilder thisRow = new StringBuilder();
                for (int k=0;k<cols;k++) {
                    cell = row.getCell(k);
                    if (null == cell) {
                        break;
                    }
                    thisRow.append(String.valueOf(cell));
                    thisRow.append(SPLIT);
                }
                result.add(thisRow.toString());
            }
        }

        return result;
    }
}
