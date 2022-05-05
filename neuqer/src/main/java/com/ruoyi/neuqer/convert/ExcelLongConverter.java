package com.ruoyi.neuqer.convert;

import cn.hutool.core.convert.Convert;
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
public class ExcelLongConverter implements Converter<Long> {

    @Override
    public Class<Long> supportJavaTypeKey() {
        return Long.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    /**
     * 这里读的时候会调用
     *
     * @param
     * @return
     */
    @Override
    public Long convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (cellData.getType().name().equals("NUMBER")) {
           if(cellData.getNumberValue() != null){
               if(new BigDecimal(cellData.getNumberValue().intValue()).compareTo(cellData.getNumberValue())==0){
                    return cellData.getNumberValue().longValue();
                }else{
                   throw new ExcelDataConvertException(cellData.getRowIndex(), cellData.getColumnIndex(),
                       cellData, contentProperty, "Excel数组解析错误");
                }
            }else{
               return null;
           }
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
    public WriteCellData<Long> convertToExcelData(Long object, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (ObjectUtil.isNotNull(object)) {
            String str = Convert.toStr(object);
            if (str.length() > 15) {
                return new WriteCellData<>(str);
            }
        }
        WriteCellData<Long> cellData = new WriteCellData<>(new BigDecimal(object));
        cellData.setType(CellDataTypeEnum.NUMBER);
        return cellData;
    }
}
