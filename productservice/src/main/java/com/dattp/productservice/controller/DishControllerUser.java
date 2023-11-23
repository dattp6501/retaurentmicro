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

import com.dattp.productservice.dto.CommentDishRequestDTO;
import com.dattp.productservice.dto.CommentDishResponseDTO;
import com.dattp.productservice.dto.DishResponseDTO;
import com.dattp.productservice.dto.ResponseDTO;
import com.dattp.productservice.entity.CommentDish;
import com.dattp.productservice.entity.User;
import com.dattp.productservice.service.DishService;

@RestController
@RequestMapping("/api/product/user/dish")
public class DishControllerUser {
    @Autowired
    private DishService dishService;

    @GetMapping("/get_dish")
    public ResponseEntity<ResponseDTO> getDishs(Pageable pageable){//page=?&size=?
        List<DishResponseDTO> list = new ArrayList<>();
        dishService.getDishs(pageable).getContent().forEach((d)->{
            DishResponseDTO dishResp = new DishResponseDTO();
            BeanUtils.copyProperties(d, dishResp);
            // comment
            if(d.getCommentDishs()!=null){
                dishResp.setComments(new ArrayList<>());
                d.getCommentDishs().stream().forEach((c)->{
                    CommentDishResponseDTO cr = new CommentDishResponseDTO();
                    BeanUtils.copyProperties(c, cr);
                    cr.setUserId(c.getUser().getId());
                    cr.setUsername(c.getUser().getUsername());
                    dishResp.getComments().add(cr);
                });

            }
            list.add(dishResp);
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
    public ResponseEntity<ResponseDTO> addComment(@RequestBody @Valid CommentDishRequestDTO CDR) throws Exception{
        CommentDish CD = new CommentDish();
        BeanUtils.copyProperties(CDR, CD);
        CD.setUser(new User(Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName()), null));
        if(!dishService.addComment(CDR.getDishId(), CD)) throw new Exception("Không đánh giá được sản phẩm");
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(), 
                "Thành công", 
                null
            )
        );
    }
}