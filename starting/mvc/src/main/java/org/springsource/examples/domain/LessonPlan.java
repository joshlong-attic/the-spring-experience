package org.springsource.examples.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * container for the {@link Lesson} entities
 *
 * @author Josh Long
 */

@Entity
@XmlRootElement(name = "lesson")
@Table(name = "lesson_plan")
public class LessonPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;


    @OneToMany(mappedBy = "lessonPlan")
    private Set<Lesson> lessons = new HashSet<Lesson>();

    public Set<Lesson> getLessons(){
        return this.lessons;
    }

    public void setLessons(Set<Lesson> lessons){
        this.lessons = lessons;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
