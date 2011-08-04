package org.springsource.examples.controllers;


import org.springframework.stereotype.Controller;
import org.springsource.examples.services.LessonOrganizer;

import javax.inject.Inject;

/***
 *
 * Simple code to manipulate lesson information
 *
 * @author Josh Long
 */
@Controller
public class LessonOrganizerController {

    @Inject private LessonOrganizer organizer ;

    public void buildLessonPlan (){}

    public void addLessonToPlan (){}

    public void reorderLessonInPlan (){}


}
