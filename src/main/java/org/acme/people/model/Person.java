package org.acme.people.model;

import java.lang.reflect.Parameter;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.text.ParagraphView;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import org.acme.people.model.EyeColor;
import java.util.HashMap;
import java.util.Map;
import io.quarkus.panache.common.Parameters;

@Entity
public class Person extends PanacheEntity {
    // the person's name
    public String name;

    // the person's birthdate
    public LocalDate birth;

    // the person's eye color
    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    public EyeColor eyes;

    // TODO: Add more queries
        public static List<Person> findByColor(EyeColor color) {
        return list("eyes", color);
    }

    public static List<Person> getBeforeYear(int year) {
        return Person.<Person>streamAll()
        .filter(p -> p.birth.getYear() <= year)
        .collect(Collectors.toList());
    }

 
}