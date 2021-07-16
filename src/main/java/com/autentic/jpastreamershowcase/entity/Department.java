package com.autentic.jpastreamershowcase.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "departments")
public class Department implements Serializable {
    @Id
    private Short code;
    @Column(nullable = false, unique = true)
    private String name;
    @OneToMany(mappedBy = "department")
    @ToString.Exclude
    @JsonManagedReference
    private Set<Municipality> municipalities;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Department that = (Department) o;

        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return 586730605;
    }
}
