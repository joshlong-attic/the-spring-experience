package org.springsource.examples.config;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springsource.examples.domain.Lesson;
import org.springsource.examples.domain.LessonPlan;
import org.springsource.examples.services.LessonOrganizer;

public class Main {
    public static void main (String args [])  throws Throwable {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(  ServicesConfig.class);

        LessonOrganizer lessonOrganizer = annotationConfigApplicationContext.getBean(LessonOrganizer.class);

        LessonPlan lessonPlan ;
        Lesson lesson;

        lessonPlan = lessonOrganizer.createLessonPlan() ;
        lesson = lessonOrganizer.createLesson ( lessonPlan.getId(),  2 , "Getting Started with Spring") ;

        lessonPlan = lessonOrganizer.getLessonPlanById( lessonPlan.getId());

        System.out.println(ToStringBuilder.reflectionToString(lessonPlan));
        System.out.println(ToStringBuilder.reflectionToString(lesson));

    }
}
