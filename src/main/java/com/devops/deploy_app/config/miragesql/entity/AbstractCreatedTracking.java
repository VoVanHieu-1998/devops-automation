package com.devops.deploy_app.config.miragesql.entity;

import com.miragesql.miragesql.annotation.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AbstractCreatedTracking {

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "CREATED_ID")
    private Long createdId;

}
