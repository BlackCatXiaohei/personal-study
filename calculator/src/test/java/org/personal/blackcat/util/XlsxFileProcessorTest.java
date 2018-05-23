package org.personal.blackcat.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.personal.blackcat.model.TDDepartment;
import org.personal.blackcat.service.CalculatorService;

import java.util.List;

@Slf4j
public class XlsxFileProcessorTest {

    @Test
    public void read() {
        String filePath = "/C:/Users/lifan.li/Desktop/work/dataForRegression.xlsx";

        List<String> read = ExcelFileProcessor.read(filePath);
        if (null != read) {
            read.forEach(row -> log.info("第{}行：{}", read.indexOf(row), row));
        }

        CalculatorService service = new CalculatorService();
        TDDepartment department = service.leastSquares(filePath);
        log.info("x: {}, y: {}", department.getX(), department.getY());
    }
}