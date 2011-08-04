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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "lesson_title", nullable = false)
    private String lessonTitle;

    @Column(name = "number", nullable = false)
    private int number;

    @ManyToOne
    @JoinColumn(name = "lesson_plan_id", referencedColumnName = "id", nullable = false)
    private LessonPlan lessonPlan;


    public void setLessonPlan(LessonPlan lp) {
        this.lessonPlan = lp;
    }

    public LessonPlan getLessonPlan() {
        return lessonPlan;
    }

    public Lesson() {
    }

    public Lesson(int n, String t) {
        this.number = n;
        this.lessonTitle = t;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

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


