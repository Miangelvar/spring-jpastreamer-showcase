package com.autentic.jpastreamershowcase.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "municipalities")
public class Municipality implements Serializable {
    @Id
    private Integer code;
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "department_code", nullable = false)
    private Department department;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Municipality that = (Municipality) o;

        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return 831495850;
    }
}
