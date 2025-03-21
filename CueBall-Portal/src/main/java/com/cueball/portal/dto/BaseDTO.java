package com.cueball.portal.dto;

import lombok.Data;


@Data
public abstract class BaseDTO {

//    private MessageDTO message;

    private int id;


    private boolean enable;

    private int createdBy;

    private int modifiedBy;


    private long createdDate;


    private long modifyDate;
}
