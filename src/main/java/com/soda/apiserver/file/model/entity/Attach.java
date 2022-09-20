package com.soda.apiserver.file.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "TBL_ATTACH")
@SequenceGenerator(
        name = "SEQ_ATTACH_ID_GENERATOR",
        sequenceName = "SEQ_ATTACH_ID",
        initialValue = 1,
        allocationSize = 1
)
public class Attach {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_ATTACH_ID_GENERATOR"
    )
    @Column(name="ATTACH_ID")
    private int id;
    @Column(name="ORIGINAL_NAME")
    private String originalName;
    @Column(name = "SAVED_NAME")
    private String savedName;
    @Column(name = "SAVED_PATH")
    private String savedPath;
    @Column(name = "FILE_TYPE")
    private String fileType;
    @Column(name = "UPLOAD_DATE")
    private String uploadDate;
    @Column(name = "ORDER_NO")
    private int orderNo;
    @Column(name = "IS_USED")
    private String isUsed;
}
