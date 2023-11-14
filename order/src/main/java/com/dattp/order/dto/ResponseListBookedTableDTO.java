package com.dattp.order.dto;

import java.util.List;

import com.dattp.order.entity.BookedTable;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseListBookedTableDTO {
    private int code;
    private String message;
    private List<BookedTable> bookedtables;
}
