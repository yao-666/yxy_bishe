package com.ruoyi.neuqer.convert;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.math.BigDecimal;

/**
 * @author yxy
 * @version 1.0
 * @date 2022/4/28 15:13
 * @apiNote
 */
public class ExcelBigDecimalConverter implements Converter<BigDecimal> {

    @Override
    public Class<BigDecimal> supportJavaTypeKey() {
        return BigDecimal.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * 这里读的时候会调用
     *
     * @param
     * @return
     */
    @Override
    public BigDecimal convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (cellData.getType().name().equals("NUMBER")) {
            return cellData.getNumberValue();
        } else if (cellData.getType().name().equals("STRING")) {
            if (cellData.getStringValue().isEmpty()) {
                return null;
            } else {
                throw new ExcelDataConvertException(cellData.getRowIndex(), cellData.getColumnIndex(),
                    cellData, contentProperty, "Excel数组解析错误");
            }
        } else {
            throw new ExcelDataConvertException(cellData.getRowIndex(), cellData.getColumnIndex(),
                cellData, contentProperty, "Excel数组解析错误");
        }


    }

    /**
     * 这里是写的时候会调用
     *
     * @return
     */
    @Override
    public WriteCellData<String> convertToExcelData(BigDecimal object, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (ObjectUtil.isNull(object)) {
            return new WriteCellData<>("");
        }
        WriteCellData<String> cellData = new WriteCellData<>(object.toString());
        cellData.setType(CellDataTypeEnum.NUMBER);
        return cellData;
    }
}
