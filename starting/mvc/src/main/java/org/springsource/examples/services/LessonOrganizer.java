package org.springsource.examples.services;


import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springsource.examples.domain.Lesson;
import org.springsource.examples.domain.LessonPlan;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Simple serivce that manipulates entities related to lessons.
 *
 * @author Josh Long
 */
@Service
public class LessonOrganizer {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Lesson createLesson(long lessonPlanId, int number, String title) {

        LessonPlan lessonPlan = getLessonPlanById(lessonPlanId);

        Lesson lesson = new Lesson(number, title);
        lesson.setLessonPlan(lessonPlan);

        this.entityManager.persist(lesson);

        lessonPlan.getLessons().add(lesson);
        this.entityManager.merge(lessonPlan);

        return lesson;
    }

    @Transactional(readOnly = true)
    public LessonPlan getLessonPlanById(long id) {
        LessonPlan lp = this.entityManager.find(LessonPlan.class, id);
        Hibernate.initialize(lp.getLessons());
        return lp;
    }

    @Transactional(readOnly = true)
    public Lesson getLessonById(long id) {
        return this.entityManager.find(Lesson.class, id);
    }

    @Transactional
    public LessonPlan createLessonPlan() {
        LessonPlan lessonPlan = new LessonPlan();
        this.entityManager.persist(lessonPlan);
        return lessonPlan;
    }

}
