package com.devops.deploy_app.entity;

import com.miragesql.miragesql.annotation.Column;
import com.miragesql.miragesql.annotation.PrimaryKey;
import com.miragesql.miragesql.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

/**
 * Book
 *
 * @author hieuvv
 * @version 01-00
 * @since 6/5/2024
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BOOK")
public class Book {
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = PrimaryKey.GenerationType.SEQUENCE, generator = "SEQ_BOOK")
    private Long id;
    @Column(name = "TITLE")
    private String title;
}
