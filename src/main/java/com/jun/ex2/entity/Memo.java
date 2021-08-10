package com.jun.ex2.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name= "tble_memo")
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;   //PK id

    @Column(length = 200, nullable = false)
    private String memoText;    //메모
}
