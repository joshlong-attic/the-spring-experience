package org.springsource.examples.controllers;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springsource.examples.domain.Lesson;
import org.springsource.examples.domain.LessonPlan;
import org.springsource.examples.services.LessonOrganizer;

import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * Simple code to manipulate lesson information
 *
 * @author Josh Long
 */
@Controller
@RequestMapping(headers = "Accept=application/json, application/xml")
public class LessonOrganizerController {

    private Log logger = LogFactory.getLog(getClass());

    @Inject  private LessonOrganizer organizer;

    @RequestMapping( value = "/plans/{planId}/lessons/{lessonId}", method = RequestMethod.GET)
    @ResponseBody
    public Lesson getLesson(
            @PathVariable("planId") long planId,
            @PathVariable("lessonId") long lessonId) {

        logger.info("planId: "+ planId);
        logger.info("lessonId: "+ lessonId);

        return organizer.getLessonById(lessonId);
    }

    /**
     *    @RequestMapping(value = "/plans/{planId}/lessons", method = RequestMethod.POST)
    @ResponseBody
    public Lesson addLessonToPlan(
            @PathVariable("planId") long planId,
            @RequestBody Lesson lesson) {

        int number = lesson.getNumber();
        String name = lesson.getLessonTitle();
        return organizer.createLesson(planId, number, name);
    }
     */


}
