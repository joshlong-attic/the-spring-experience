package org.springsource.examples.services;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springsource.examples.config.ServicesConfig;
import org.springsource.examples.domain.Lesson;
import org.springsource.examples.domain.LessonPlan;

import javax.inject.Inject;

/**
 * @author Josh Long
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {ServicesConfig.class})
public class LessonOrganizerIntegrationTest {

    @Inject private LessonOrganizer lessonOrganizer;

    @Test
    public void testLessonOrganizer() throws Throwable {

        Lesson lesson = lessonOrganizer.createLesson(
                lessonOrganizer.createLessonPlan().getId(), 2, "Getting Started with Spring");
        LessonPlan lessonPlan = lessonOrganizer.getLessonPlanById(lesson.getLessonPlan().getId());

        Assert.assertNotNull(lesson.getLessonPlan());
        Assert.assertTrue(lessonPlan.getLessons().size() > 0);
    }


}
