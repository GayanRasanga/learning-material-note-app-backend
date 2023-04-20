package com.note.app.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Document {
    @Id
    @GeneratedValue
    private Long did;
    @Column(length = 512, nullable = false, unique = true)
    private String dName;
    private String dType;
    private  long dSize;
    @Column(name="upload_Time")
    private Date uploadTime;
    @Lob
    private byte[] file;

}
