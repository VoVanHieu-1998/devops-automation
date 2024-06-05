package com.devops.deploy_app.config.miragesql.entity;

import com.miragesql.miragesql.annotation.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AbstractTracking extends AbstractAuditTracking {

    @Column(name = "DELETED_DATE")
    private Date deletedDate;

    @Column(name = "DELETED_ID")
    private Long deletedId = 0L;
}
