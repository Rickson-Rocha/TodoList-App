package com.br.todolist.model.task;

import java.time.LocalDate;

import com.br.todolist.model.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.ManyToOne;


@Entity
@Table(name = Task.TABLE_NAME)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Task {

    public static final String TABLE_NAME = "task";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(min = 4, max = 200)
    private String name;

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 4, max = 200)
    private String description;

    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate startAt;

    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate endAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


   

}
