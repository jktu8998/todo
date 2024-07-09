package com.example.todo103.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
//@Getter @Setter
@NoArgsConstructor
//@AllArgsConstructor
@Data
@Table(name = "todo")
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "todo_id")
    private long id;

    @Column(name = "creation_date", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd hh:mm:ss" )
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "status")
    private boolean status;

    @NotNull(message = "Text may not be null")
    @NotEmpty(message = "Text may not be empty")
    @Size(min = 3,max = 100, message = "A Description should have at least 3 characters")
    @Column(name = "task")
    private String text;

    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd hh:mm:ss" )
    @UpdateTimestamp
    private Date updatedAt;


    public Tasks(long id, Date createdAt, boolean status, String text, Date updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.status = status;
        this.text = text;
        this.updatedAt = updatedAt;
    }
}
