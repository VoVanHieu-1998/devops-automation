package com.devops.deploy_app.config.miragesql.entity;

import com.miragesql.miragesql.annotation.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AbstractAuditTracking extends AbstractCreatedTracking {

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_ID")
	private Long updatedId;
}
