package com.dattp.productservice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dattp.productservice.dto.CommentTableRequestDTO;
import com.dattp.productservice.dto.CommentTableResponseDTO;
import com.dattp.productservice.dto.ResponseDTO;
import com.dattp.productservice.dto.TableResponseDTO;
import com.dattp.productservice.entity.CommentTable;
import com.dattp.productservice.entity.User;
import com.dattp.productservice.service.TableService;

@RestController
@RequestMapping("/api/product/user/table")
public class TableControllerUser {
    @Autowired
    private TableService tableService;

    @GetMapping(value = "/get_table")
    public ResponseEntity<ResponseDTO> getAllTable(Pageable pageable){
        List<TableResponseDTO> list = new ArrayList<>();
        tableService.getAll(pageable).getContent().forEach((t)->{
            TableResponseDTO tableResp = new TableResponseDTO();
            BeanUtils.copyProperties(t, tableResp);
            // comment
            if(t.getCommentTables()!=null){
                tableResp.setComments(new ArrayList<>());
                t.getCommentTables().stream().forEach((c)->{
                    CommentTableResponseDTO cr = new CommentTableResponseDTO();
                    BeanUtils.copyProperties(c, cr);
                    cr.setUserId(c.getUser().getId());
                    cr.setUsername(c.getUser().getUsername());
                    tableResp.getComments().add(cr);
                });
            }
            list.add(tableResp);
        });
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(), 
                "Thành công", 
                list
            )
        );
    }

    @PostMapping
    @RequestMapping("add_comment")
    @RolesAllowed({"ROLE_PRODUCT_ACCESS"})
    public ResponseEntity<ResponseDTO> addComment(@RequestBody @Valid CommentTableRequestDTO CTR) throws Exception{
        CommentTable CT = new CommentTable();
        BeanUtils.copyProperties(CTR, CT);
        CT.setUser(new User(Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName()), null));
        if(!tableService.addComment(CTR.getTableId(), CT)) throw new Exception("Không đánh giá được sản phẩm");
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(), 
                "Thành công", 
                null
            )
        );
    }
}