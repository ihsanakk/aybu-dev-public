package com.example.aybudev.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import com.example.aybudev.entity.enums.ETopics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "user")
public class User {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String username;

    @OneToMany(mappedBy = "user")
    private Set<Post> posts;

    
    private String career;


    @ElementCollection(targetClass = ETopics.class)
    @JoinTable(name = "topics", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "topics")
    @Enumerated(EnumType.STRING)
    private List<ETopics> topicList;
    
}
