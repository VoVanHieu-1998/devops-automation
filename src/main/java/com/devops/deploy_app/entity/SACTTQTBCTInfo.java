package com.devops.deploy_app.entity;

import com.devops.deploy_app.config.miragesql.entity.AbstractTracking;
import com.miragesql.miragesql.annotation.Column;
import com.miragesql.miragesql.annotation.PrimaryKey;
import com.miragesql.miragesql.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.Date;

/**
 * project sa01y23ttqt - author hieuvv
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SAC_TTQT_BCT_INFO")
public class SACTTQTBCTInfo extends AbstractTracking {
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = PrimaryKey.GenerationType.SEQUENCE, generator = "SEQ_SAC_TTQT_BCT_INFO")
    private Long id;
    @Column(name = "CODE_BCT")
    private String codeBct;
    @Column(name = "CUSTOMER_CODE")
    private String customerCode;
    @Column(name = "CUSTOMER_NAME")
    private String customerName;
    @Column(name = "NHNNG_REF_NUMBER")
    private String nHNNgRefNumber ;
    @Column(name = "RECEIPT_DATE")
    private Date receiptDate;
    @Column(name = "CURRENCY_CODE")
    private String currencyCode;
    @Column(name = "VALUE_BCT")
    private BigDecimal valueBct;
    @Column(name = "EXPRESS_DELIVERY_COMPANY")
    private String expressDeliveryCompany;
    @Column(name = "WAYBILL_NUMBER")
    private String waybillNumber;
    @Column(name = "NOTE")
    private String note;
    @Column(name = "REF_NUMBER_SACOMBANK")
    private String refNumberSacombank;
    @Column(name = "ORDER_NUMBER_BCT")
    private String orderNumberBct;
    @Column(name = "INVOICE_NUMBER")
    private String invoiceNumber;
    @Column(name = "BCT_TYPE_CODE")
    private String bctTypeCode;
    @Column(name = "SUB_BCT_TYPE_CODE")
    private String subBctTypeCode;
    @Column(name = "SAC_ORG_CODE")
    private String sacOrgCode;
    @Column(name = "GROUND_RADIO_DELIVERY_RECEIVING")
    private String groupRadioDeliveryReceiving;
    @Column(name = "RECEIVER_ID")
    private Long receiverId;
    @Column(name = "RECEIVER_USERNAME")
    private String receiverUsername;
    @Column(name = "ORG_CREATED")
    private String orgCreated;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "IS_HO_CREATED")
    private Boolean isHoCreated;
    @Column(name = "TF_ISSUING_BANK_NO")
    private String tfIssuingBankNo;
    @Column(name = "TF_TR_REIM_CUST_NO")
    private String tfTrReimCustno;
    @Column(name = "TF_TR_REI_MB_ADDR")
    private String tfTrReimbAddr;
    @Column(name = "AO_CODE")
    private String accountOfficer;
    @Column(name = "FUNCTION_CODE")
    private String functionCode;
    @Column(name = "MAX_CHECKING_ID")
    private Long maxCheckingId;
}
