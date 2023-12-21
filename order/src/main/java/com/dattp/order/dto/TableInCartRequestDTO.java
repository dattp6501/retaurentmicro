package com.dattp.order.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableInCartRequestDTO {
    @JsonProperty("tableId")
    @NotNull(message="tableId không được để trống")
    private Long tableId;

    @NotNull(message="Tên bàn(name) không được để trống")
    private String name;

    @Min(value = 1, message = "GIá thuê bàn phải > 0")
    private float price;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tableId == null) ? 0 : tableId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof TableInCartRequestDTO))
            return false;
        TableInCartRequestDTO other = (TableInCartRequestDTO) obj;
        if (tableId == null) {
            if (other.tableId != null)
                return false;
        } else if (!tableId.equals(other.tableId))
            return false;
        return true;
    }
}
