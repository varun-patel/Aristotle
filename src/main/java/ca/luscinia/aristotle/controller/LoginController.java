/*
 * Aristotle Learning Platform: Luscinia Enterprises Assn.
 * Copyright (C) 2019  Varun Patel <varun@varunpatel.ca>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package ca.luscinia.aristotle.controller;

import ca.luscinia.aristotle.model.Login;
import ca.luscinia.aristotle.model.Student;
import ca.luscinia.aristotle.model.Teacher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController extends AristotleControllerTmpl {

	@RequestMapping(value = "/login/", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("login");
		modelAndView.addObject("email", "user@example.com");
		return modelAndView;
	}

	@RequestMapping(value = "/login/process/", method = RequestMethod.POST)
	public ModelAndView loginProcess(HttpServletRequest request, HttpServletResponse response) {
		Login login = new Login(request.getAttribute("email").toString(), request.getAttribute("password").toString());
		Student student = new Student();
		Teacher teacher = new Teacher();
		ModelAndView modelAndView = new ModelAndView();
		if (login.isStudent(student)) {
			modelAndView.setViewName("student.dash");
			request.getSession().setAttribute("user", student);
		} else if (login.isTeacher(teacher)) {
			modelAndView.setViewName("teacher.dash");
			request.getSession().setAttribute("user", teacher);
		} else {
			modelAndView.setViewName("login");
			modelAndView.addObject("email", login.getUsername());
			modelAndView.addObject("error", login.processError());
			request.getSession().invalidate();
		}
		return modelAndView;
	}
}
