package eu.ehri.helpdesk;

import java.io.IOException;
import java.net.BindException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.annolab.tt4j.TreeTaggerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! the client locale is " + locale.toString());

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	// controler for user query
	@RequestMapping(value = "/response", method = RequestMethod.GET)
	public ModelAndView addQuery(@ModelAttribute("input") GetInput input,
			BindingResult result, Model model) throws IOException,
			TreeTaggerException {

		System.out.println("Query: " + input.getInput());

		Workflow process = new Workflow();

		List<String> queryresults = process.processQuery(input);
		List<Result> list2display = new ArrayList<Result>();
		
		
		
		for (int i = 0; i < queryresults.size(); i++){
			list2display.add(new Result(queryresults.get(i)));
		}
		
		model.addAttribute("queryresults",
				java.util.Collections.synchronizedCollection(list2display));
		
		GetInput q = new GetInput();
		q.setInput("");
		return new ModelAndView("input", "command", q);
	}

	@RequestMapping("/ask")
	public ModelAndView showQuery() {
		System.out.println("HELPDESK ACTIVATED");
		return new ModelAndView("input", "command", new GetInput());
	}

}
