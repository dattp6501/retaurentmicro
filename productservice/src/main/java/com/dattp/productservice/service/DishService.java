package com.dattp.productservice.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dattp.productservice.config.GlobalConfig;
import com.dattp.productservice.entity.Dish;
import com.dattp.productservice.exception.BadRequestException;
import com.dattp.productservice.repository.DishRepository;

@Service
public class DishService {
    @Autowired
    private DishRepository dishRepository;

    public Dish save(Dish dish){
        return dishRepository.save(dish);
    }

    public List<Dish> readXlsxDish(InputStream inputStream) throws IOException{
        final int COLUMN_INDEX_NAME = 0;
        final int COLUMN_INDEX_PRICE = 1;
        final int COLUMN_INDEX_DESCRIPTION = 2;
        List<Dish> dishs = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> it = sheet.iterator();
        it.next();
        int index = 1;
        while(it.hasNext()) {
            Dish dish = new Dish();
            dish.setState(GlobalConfig.OK_STATE);
            Row row = it.next();
            for(int i=0; i<3; i++){
                if(i==COLUMN_INDEX_NAME){
                    if(row.getCell(i)==null || row.getCell(i).getStringCellValue().equals("")){
                        workbook.close();
                        throw new BadRequestException("Dòng "+index+": Tên món ăn không được để trống");
                    }
                    dish.setName(row.getCell(i).getStringCellValue());
                    continue;
                }
                if(i==COLUMN_INDEX_PRICE){
                    if(row.getCell(i)==null){
                        throw new BadRequestException("Dòng "+index+": Giá món ăn không được để trống");
                    }
                    if(row.getCell(i).getNumericCellValue()<=0){
                        workbook.close();
                        throw new BadRequestException("Dòng "+index+": Giá món ăn phải lớn hơn 0");
                    }
                    dish.setPrice((float)row.getCell(i).getNumericCellValue());
                    continue;
                }
                if(i==COLUMN_INDEX_DESCRIPTION){
                    if(row.getCell(i)!=null){
                        dish.setDiscription(row.getCell(i).getStringCellValue());
                        workbook.close();
                    }
                }
            }
            dishs.add(dish);
            index++;
        }
        workbook.close();
        return dishs;
    }
    public List<Dish> save(List<Dish> dishs){
        return dishRepository.saveAll(dishs);
    }
    public List<Dish> getAll(){
        return dishRepository.findAll();
    }
    public Dish getById(long id){
        return dishRepository.findById(id).orElse(null);
    }
}
