package com.dattp.productservice.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dattp.productservice.config.GlobalConfig;
import com.dattp.productservice.entity.CommentTable;
import com.dattp.productservice.entity.TableE;
import com.dattp.productservice.exception.BadRequestException;
import com.dattp.productservice.repository.CommentTableRepository;
import com.dattp.productservice.repository.TableRepository;

@Service
public class TableService {
    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private CommentTableRepository commentTableRepository;

    @Transactional
    public TableE saveTable(TableE table) {
        return tableRepository.save(table);
    }

    public Page<TableE> getAll(Pageable pageable){
        return tableRepository.findAll(pageable);
    }
    
    public List<TableE> readXlsxTable(InputStream inputStream) throws IOException{
        List<TableE> tables = new ArrayList<>();
        final int COLUMN_INDEX_NAME = 0;
        final int COLUMN_INDEX_AMOUNT_OF_PEOPLE = 1;
        final int COLUMN_INDEX_PRICE = 2;
        final int COLUMN_INDEX_FROM = 3;
        final int COLUMN_INDEX_TO = 4;
        final int COLUMN_INDEX_DESCRIPTION = 5;
        Calendar calendar = Calendar.getInstance();
        // xlsx: XSSFWorkbook, xls: HSSFWorkbook, 
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> it = sheet.iterator();
        it.next();//bo qua dong dong tien(dong tieu de)
        int index = 0;
        while(it.hasNext()) {
            index++;
            boolean isRowEmpty = true;
            Row row = it.next();
            TableE table = new TableE();
            table.setState(GlobalConfig.OK_STATE);
            for(int i=0; i<6; i++){
                if(i==COLUMN_INDEX_NAME){
                    if(row.getCell(i)!=null && !row.getCell(i).getStringCellValue().equals("")) {
                        table.setName(row.getCell(i).getStringCellValue());
                        isRowEmpty = false;
                    }
                    continue;
                }
                if(i==COLUMN_INDEX_AMOUNT_OF_PEOPLE){
                    if(row.getCell(i)!=null){
                        table.setAmountOfPeople((int)row.getCell(i).getNumericCellValue());
                        isRowEmpty = false;
                    }
                    continue;
                }
                if(i==COLUMN_INDEX_PRICE){
                    if(row.getCell(i)!=null){
                        table.setPrice((float)row.getCell(i).getNumericCellValue());
                        isRowEmpty = false;
                    }
                    continue;
                }
                if(i==COLUMN_INDEX_FROM){
                    if(row.getCell(i)!=null){
                        calendar.setTime(row.getCell(i).getDateCellValue());
                        calendar.add(Calendar.HOUR_OF_DAY, 7);//dua ve UTC +7
                        table.setFrom(calendar.getTime());
                        isRowEmpty = false;
                    }
                    continue;
                }
                if(i==COLUMN_INDEX_TO){
                    if(row.getCell(i)!=null){
                        calendar.setTime(row.getCell(i).getDateCellValue());
                        calendar.add(Calendar.HOUR_OF_DAY, 7);//dua ve UTC +7
                        table.setTo(calendar.getTime());
                        isRowEmpty = false;
                    }
                    continue;
                }
                if(i==COLUMN_INDEX_DESCRIPTION){
                    if(row.getCell(i)!=null){
                        table.setDesciption(row.getCell(i).getStringCellValue());
                        isRowEmpty = false;
                    }
                    continue;
                }
            }
            if(isRowEmpty) continue;//if row empty
            // row not empty
            if(table.getName()==null || table.getName().equals("")){
                workbook.close();
                throw new BadRequestException("Dòng "+index+": Tên bàn không được để trống");
            } 
            if(table.getAmountOfPeople()<=0){
                workbook.close();
                throw new BadRequestException("Dòng "+index+": Số người ngồi phải lớn hơn 0");
            } 
            if(table.getPrice()<=0){
                workbook.close();
                throw new BadRequestException("Dòng "+index+": Giá thuê bàn phải lớn hơn 0");
            } 
            tables.add(table);
        }
        workbook.close();
        return tables;
    }

    @Transactional
    public List<TableE> save(List<TableE> tables){
        return tableRepository.saveAll(tables);
    }

    public TableE getById(long id){
        return tableRepository.findById(id).orElse(null);
    }

    @Transactional
    public boolean addComment(Long tableId, CommentTable comment){
        // if user commented
        if(commentTableRepository.findByTableIdAndUserId(tableId, comment.getUser().getId())!=null)
            return commentTableRepository.update(comment.getStar(), comment.getComment(), tableId, comment.getUser().getId())>0;
        return commentTableRepository.save(comment.getStar(), comment.getComment(), tableId, comment.getUser().getId(), comment.getUser().getUsername())>=1;
    }
}