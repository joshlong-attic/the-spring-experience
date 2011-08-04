package org.springsource.examples.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

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
@Table(name = "lesson_plan")
public class LessonPlan {
    private Long id;

    private Set<Lesson> lessons = new HashSet<Lesson>();

    @OneToMany(mappedBy = "lessonPlan")
    public Set<Lesson> getLessons(){
        return this.lessons;
    }

    public void setLessons(Set<Lesson> lessons){
        this.lessons = lessons;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
