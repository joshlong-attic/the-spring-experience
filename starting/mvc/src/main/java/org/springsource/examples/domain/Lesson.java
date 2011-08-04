package org.springsource.examples.domain;

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;


/**
 * entity to hold information about a lesson
 *
 * @author Josh Long
 */
@Entity
@Table(name = "lesson")
public class Lesson implements java.io.Serializable {


    private Long id;


    private String lessonTitle;


    private int number;


    private LessonPlan lessonPlan;

    @ManyToOne
    @JoinColumn(name = "lesson_plan_id", referencedColumnName = "id", nullable = false)
    public LessonPlan getLessonPlan() {
        return lessonPlan;
    }

    public void setLessonPlan(LessonPlan lessonPlan) {
        this.lessonPlan = lessonPlan;
    }

    public Lesson() {
    }

    public Lesson(int n, String t) {
        this.number = n;
        this.lessonTitle = t;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "number", nullable = false)
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Column(name = "lesson_title", nullable = false)
    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}


