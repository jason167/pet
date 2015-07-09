/**
 * 
 */
package com.michael.controller;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.michael.po.Pet;
import com.michael.service.PetService;

/**
 * @author 610273
 *
 */
@Controller
public class PetController extends BaseController{

	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private PetService petServiceImpl;
	private static java.text.SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static String now; 
	static{
		now = sf.format(new Date());
	}
	
	@RequestMapping(value = "showPets/{id}/{petName}", method = RequestMethod.GET)
	public String showPetById(Pet pet, HttpServletRequest request){
		logger.info("now:"+now);
		request.setAttribute("petPart", pet.getId() +"_"+pet.getPetName());
		return "showPets";
	}
	
	
	@RequestMapping(value = "showPets/{id}", method = RequestMethod.GET)
	public String showPetById(@PathVariable("id") int id, HttpServletRequest request){
		request.setAttribute("pet", id);
		return "showPets";
	}
	
	@RequestMapping(value = "/showPets", method = RequestMethod.POST)
	public String showPets(int masterId, String remark, Model model, HttpServletRequest request){
		logger.info("show...masterId:"+masterId +", remark:"+ remark);
		logger.info("query string:{}", request.getQueryString());
		List<Pet> pets = petServiceImpl.searchPets(masterId);
		logger.info("pets size:" + (pets == null ? null : pets.size()));
		model.addAttribute("pets", pets);
		return "pet";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView edit(int id){
		Pet pet = petServiceImpl.findById(id);
		return new ModelAndView("edit", "pet", pet);
	}
	
	@ResponseBody
	@RequestMapping(value = "/writer", method = RequestMethod.POST)
	public String writer(int id, String remark, Writer writer, HttpServletResponse response){
		logger.info("writer...id:"+id +", remark:"+ remark);
		Pet pet = petServiceImpl.findById(id);
		try {
			response.setContentType( "text/plain;charset=UTF-8" );
//	        response.setCharacterEncoding( "UTF-8" );
	        logger.info("{}", pet);
	        response.getWriter().write(pet.toString());
//			writer.write(pet.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
