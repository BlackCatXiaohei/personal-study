package org.personal.blackcat.service;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.personal.blackcat.model.TDDepartment;

import java.util.List;

import static org.personal.blackcat.util.ExcelFileProcessor.SPLIT;
import static org.personal.blackcat.util.ExcelFileProcessor.read;

/**
 * @author lifan.li
 * @since 2018/5/22
 **/
@Slf4j
public class CalculatorService {

    /**
     * a method that calculate least squares
     * @param filePath file Path
     * @return a model for 2D department
     */
    public TDDepartment leastSquares(String filePath) {
        List<String> dataFrom = read(filePath);
        if (null == dataFrom) {
            log.error("data is null , Please check file!");
            return new TDDepartment(0, 0);
        }

        List<TDDepartment> dataList = Lists.newArrayList();
        transDataToTDD(dataFrom, dataList);

        return calculate(dataList);
    }

    private TDDepartment calculate(List<TDDepartment> dataList) {
        int length = dataList.size();

        double a = calculateA(dataList, length);
        double b = calculateB(dataList, a, length);
        return new TDDepartment(a, b);
    }

    private double calculateA(List<TDDepartment> dataList, int length) {
        return (length * xySum(dataList) - sum(dataList).getX()*sum(dataList).getY())
                /
                (length * squareSum(dataList).getX() - Math.pow(sum(dataList).getX(), 2));
    }

    private double calculateB(List<TDDepartment> dataList, double a, int length) {
        return sum(dataList).getY()/length - a*sum(dataList).getX()/length;
    }

    private TDDepartment sum(List<TDDepartment> list) {
        TDDepartment result = new TDDepartment();

        double x = 0;
        double y = 0;
        for (TDDepartment tdDepartment : list) {
            x += tdDepartment.getX();
            y += tdDepartment.getY();
        }
        result.setX(x);
        result.setY(y);
        return result;
    }

    private TDDepartment squareSum(List<TDDepartment> list) {
        TDDepartment result = new TDDepartment();

        double x2 = 0;
        double y2 = 0;
        for (TDDepartment tdDepartment : list) {
            x2 += Math.pow(tdDepartment.getX(), 2);
            y2 += Math.pow(tdDepartment.getY(), 2);
        }
        result.setX(x2);
        result.setY(y2);
        return result;
    }

    private double xySum(List<TDDepartment> list) {
        double sum = 0;
        for (TDDepartment tdd : list) {
            sum += tdd.getX() * tdd.getY();
        }
        return sum;
    }

    private void transDataToTDD(List<String> dataFrom, List<TDDepartment> dataList) {
        int xIndex = 0;
        int yIndex = 0;

        String header = dataFrom.get(0);
        List<String> headerCells = Splitter.on(SPLIT).splitToList(header);
        for (String cell : headerCells) {
            if (cell.contains(X)) {
                xIndex = headerCells.indexOf(cell);
            }
            if (cell.contains(Y)) {
                yIndex = headerCells.indexOf(cell);
            }
        }
        dataFrom.remove(0);

        for (String row : dataFrom) {
            List<String> cells = Splitter.on(SPLIT).splitToList(row);
            double x = Double.parseDouble(cells.get(xIndex));
            double y = Double.parseDouble(cells.get(yIndex));

            TDDepartment department = new TDDepartment();
            department.setX(x);
            department.setY(y);

            dataList.add(department);
        }

    }

    private static final String X = "x";

    private static final String Y = "y";

}
