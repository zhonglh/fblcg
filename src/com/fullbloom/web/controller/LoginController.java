package com.fullbloom.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.fullbloom.framework.core.ClientManager;
import com.fullbloom.framework.util.ContextHolderUtils;
import com.fullbloom.framework.util.IpUtil;
import com.fullbloom.framework.util.ResourceUtil;
import com.fullbloom.web.entity.TsUser;
import com.fullbloom.web.service.ISystemService;
import com.fullbloom.web.vo.Client;

@Controller
@RequestMapping("/loginController")
public class LoginController extends BaseController{
	
	@Autowired
	ISystemService systemService;

	/**
	 * 检查用户名称
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "checkUser")
	@ResponseBody
	public AjaxJson checkUser(TsUser user, HttpServletRequest req) {
		HttpSession session = req.getSession();
		AjaxJson j = new AjaxJson();
		try{
			user = systemService.checkUserExits(user);	
			boolean b = (user!=null);
			j.setSuccess(b);
			
			if(b){
				Client client = new Client();
		        client.setIp(IpUtil.getIpAddr(req));
		        client.setLoginTime(new Date());
		        client.setTsUser(user);
		        ClientManager.getInstance().addClinet(session.getId(), client);
			}
	        
		}catch(Exception e){
			j.setSuccess(false);
			j.setMsg("出现错误");
			e.printStackTrace();
		}
		return j;
	}
	

	@RequestMapping(params = "toRegiste")
	public String toRegiste(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) {
		return "login/registe";
	}
	
	
	/**
	 * 注册用户
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "registeUser")
	@ResponseBody
	public AjaxJson registeUser(TsUser user, HttpServletRequest req) {
		HttpSession session = req.getSession();
		AjaxJson j = new AjaxJson();
		try{
			TsUser user1 = new TsUser();
			user1.setUserName(user.getUserName());
			List<TsUser> list = systemService.getUser(user1);
			if(list != null && list.size() >0){
				j.setSuccess(false);
				j.setMsg("该用户名已经存在!");
			}
			
			user.setIp( IpUtil.getIpAddr(req) );
			
			systemService.addUser(user);
	        
		}catch(Exception e){
			j.setSuccess(false);
			j.setMsg("出现错误");
		}
		return j;
	}
	
	
	@RequestMapping(params = "toRegisteSuccess")
	public String toRegisteSuccess(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) {
		return "login/registeSuccess";
	}
	
	
	@RequestMapping(params = "login")
	public String login(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) {
		TsUser user = ResourceUtil.getSessionUser();
		if (user != null) {		
			request.setAttribute("user", user);
			return "main/hplus_main";
		} else {
			return "login/login";
		}

	}

	/**
	 * 退出系统
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "logout")
	public ModelAndView logout(HttpServletRequest request) {
		HttpSession session = ContextHolderUtils.getSession();		
		ClientManager.getInstance().removeClinet(session.getId()); 
		session.invalidate();
		ModelAndView modelAndView = new ModelAndView(new RedirectView("loginController.do?login"));
		return modelAndView;
	}
}