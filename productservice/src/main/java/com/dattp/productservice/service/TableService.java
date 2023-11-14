package com.dattp.productservice.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dattp.productservice.entity.TableE;
import com.dattp.productservice.exception.BadRequestException;
import com.dattp.productservice.repository.TableRepository;

@Service
public class TableService {
    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private RestTemplate restTemplate;

    public TableE saveTable(TableE table) {
        return tableRepository.save(table);
    }

    public List<TableE> getAll(){
        return tableRepository.findAll();
    }

    public ArrayList<TableE> getFreeTime(Date from, Date to) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity <String> entity = new HttpEntity<String>(headers);
      
        restTemplate.exchange("http://localhost:9093", HttpMethod.GET, entity, String.class).getBody();
        return null;
    }
    

    public List<TableE> readXlsxTable(InputStream inputStream) throws IOException{
        List<TableE> tables = new ArrayList<>();
        final int COLUMN_INDEX_NAME = 0;
        final int COLUMN_INDEX_AMOUNT_OF_PEOPLE = 1;
        final int COLUMN_INDEX_PRICE = 2;
        final int COLUMN_INDEX_DESCRIPTION = 3;
        // xlsx: XSSFWorkbook, xls: HSSFWorkbook, 
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> it = sheet.iterator();
        it.next();//bo qua dong dong tien(dong tieu de)
        int index = 1;
        while(it.hasNext()) {
            Row row = it.next();
            TableE table = new TableE();
            for(int i=0; i<4; i++){
                if(i==COLUMN_INDEX_NAME){
                    if(row.getCell(i)==null || row.getCell(i).getStringCellValue().equals("")) {
                        workbook.close();
                        throw new BadRequestException("Dòng "+index+": Tên bàn không được để trống");
                    }
                    table.setName(row.getCell(i).getStringCellValue());
                    continue;
                }
                if(i==COLUMN_INDEX_AMOUNT_OF_PEOPLE){
                    if(row.getCell(i)==null) {
                        workbook.close();
                        throw new BadRequestException("Dòng "+index+": Số người ngồi không được để trống");
                    }
                    if(row.getCell(i).getNumericCellValue()<=0) {
                        workbook.close();
                        throw new BadRequestException("Dòng "+index+": Số người ngồi phải lớn hơn 0");
                    }
                    table.setAmountOfPeople((int)row.getCell(i).getNumericCellValue());
                    continue;
                }
                if(i==COLUMN_INDEX_PRICE){
                    if(row.getCell(i)==null){
                        workbook.close();
                        throw new BadRequestException("Dòng "+index+": Giá thuê bàn không được để trống");
                    }
                    if(row.getCell(i).getNumericCellValue()<=0) {
                        workbook.close();
                        throw new BadRequestException("Dòng "+index+": Giá thuê bàn phải lớn hơn 0");
                    }
                    table.setPrice((float)row.getCell(i).getNumericCellValue());
                    continue;
                }
                if(i==COLUMN_INDEX_DESCRIPTION){
                    if(row.getCell(i)!=null){
                        table.setDesciption(row.getCell(i).getStringCellValue());
                    }
                    continue;
                }
            }
            tables.add(table);
            index++;
        }
        workbook.close();
        return tables;
    }
    public List<TableE> save(List<TableE> tables){
        return tableRepository.saveAll(tables);
    }
}
