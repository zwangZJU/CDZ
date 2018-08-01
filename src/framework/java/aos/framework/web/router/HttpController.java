package aos.framework.web.router;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import aos.framework.core.utils.AOSUtils;


@Controller
@RequestMapping(value = "share")
@SuppressWarnings("all")
public class HttpController {
	private static final Logger logger = LoggerFactory.getLogger(HttpController.class);
	
	@RequestMapping(value = "do")
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response){
		String goods_id=request.getParameter("goods_id"); 
		ModelAndView mav=new ModelAndView("myproject/share/share.jsp");
		     if(AOSUtils.isNotEmpty(goods_id)){
			     mav.addObject("desc", "desc"); 
		     }else{
		    	 mav.addObject("desc", "");
		     }
		     mav.addObject("time", new Date());
		     mav.addObject("cxt", request.getContextPath());
		     mav.getModel().put("name", "caoyc");
		     return mav;
		 }
	/*@RequestMapping(value = "do")
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		HttpModel httpModel = new HttpModel(request, response);
		String router = request.getHeader("router");
		
		router = AOSUtils.isEmpty(router) ? httpModel.getInDto().getString("router") : router;
		
		return httpModel.getViewPath();
	}*/

}
