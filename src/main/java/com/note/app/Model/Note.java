package com.note.app.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Note {
    @Id
    @GeneratedValue
    private Long Nid;
    private String title;
    private String description;
    private Date date;
    @OneToMany(targetEntity = Document.class,cascade = CascadeType.ALL)
    @JoinColumn(name ="cp_fk",referencedColumnName = "Nid")
    private List<Document> documents;

}
