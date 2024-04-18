package com.simulation.LoLItemSimulation.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Simulation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "championName")
    private String championName;

    @Column(name = "item_1")
    private String item1;

    @Column(name = "item_2")
    private String item2;

    @Column(name = "item_3")
    private String item3;

    @Column(name = "item_4")
    private String item4;

    @Column(name = "item_5")
    private String item5;

    @Column(name = "item_6")
    private String item6;

    @ManyToOne
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private Post post; // 포스트 객체를 참조할 수도 있습니다.
}
