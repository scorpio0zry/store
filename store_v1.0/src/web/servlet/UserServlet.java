package web.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import com.domain.User;
import com.service.CartService;
import com.service.UserService;
import com.utils.BaseServlet;
import com.utils.BeanFactory;
import com.utils.MyDateConverter;
import com.utils.StringUtils;

public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 跳转到注册页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String registUI(HttpServletRequest req, HttpServletResponse resp) {
		return "/jsp/register.jsp";
	}

	/**
	 * 用户名的异步校验
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String checkUsername(HttpServletRequest req, HttpServletResponse resp) {
		String value = req.getParameter("value");
		UserService us = (UserService) BeanFactory.getBean("UserService");
		try {
			User user = us.findByName(value);
			if (user != null) {
				resp.getWriter().println(1);
			} else {
				resp.getWriter().println(2);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return null;

	}

	/**
	 * 邮箱的异步校验
	 */
	public String checkEmail(HttpServletRequest req, HttpServletResponse resp) {
		try {
			String value = req.getParameter("value");
			boolean flag = value
					.matches("^([a-z0-9_\\.-]+)@([0-9a-z\\.-]+)\\.([a-z\\.]{2,6})$");
			if (flag) {

				resp.getWriter().println(1);
			} else {
				resp.getWriter().println(2);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * 用户注册
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String regist(HttpServletRequest req, HttpServletResponse resp) {
		try {
			// 判断验证码
			String code1 = req.getParameter("code");
			String code2 = (String) req.getSession().getAttribute("code");
			if (StringUtils.isEmpty(code1) || StringUtils.isEmpty(code2)
					|| !code2.equalsIgnoreCase(code1)) {
				req.setAttribute("msg", "<font color='red'>验证码输入错误！</font>");
				return "/jsp/register.jsp";
			}
			Map<String, String[]> map = req.getParameterMap();
			UserService us = (UserService) BeanFactory.getBean("UserService");
			User user = new User();
			//ConvertUtils.register(new MyDateConverter(), Date.class);
			BeanUtils.populate(user, map);

			us.save(user);
			// 发送邮件
			//us.mail(user);
			req.setAttribute("msg", "注册成功，请去邮箱激活");
			//更改ip地址
			req.setAttribute("active","<a href='http://47.100.213.177/store_v1.0/UserServlet?method=active&code="+user.getCode()+"'>快捷激活(直接点击即可激活)</a>");
			return "/jsp/msg.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

	}

	/**
	 * 邮箱激活确认
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String active(HttpServletRequest req, HttpServletResponse resp) {
		String code = req.getParameter("code");
		UserService us = (UserService) BeanFactory.getBean("UserService");
		try {
			User user = us.findByCode(code);
			if (user == null) {
				req.setAttribute("msg", "激活失败!请重新注册");
				return "/jsp/msg.jsp";
			}

			user.setState(2); // 2 激活状态码
			user.setCode(null);
			us.update(user);
			//为该用户创建购物车
			CartService cd = (CartService) BeanFactory.getBean("CartService");
			cd.createCart(user);
			req.setAttribute("msg", "激活成功，请重新登录");
			return "/jsp/msg.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

	}

	/**
	 * 登录界面UI
	 */
	public String loginUI(HttpServletRequest req, HttpServletResponse resp) {
		return "/jsp/login.jsp";
	}

	/**
	 * 用户登录
	 */
	public String login(HttpServletRequest req, HttpServletResponse resp) {
		try {
			// 判断验证码
			String code1 = req.getParameter("code");
			String code2 = (String) req.getSession().getAttribute("code");
			if (StringUtils.isEmpty(code1) || StringUtils.isEmpty(code2)
					|| !code2.equalsIgnoreCase(code1)) {
				req.setAttribute("msg", "<font color='red'>验证码输入错误！</font>");
				return "/jsp/login.jsp";
			}
			Map<String, String[]> map = req.getParameterMap();
			UserService us = (UserService) BeanFactory.getBean("UserService");
			User user = new User();
			ConvertUtils.register(new MyDateConverter(), Date.class);
			BeanUtils.populate(user, map);
			User findUser = us.login(user);
			// 未找到用户名
			if (findUser == null) {
				req.setAttribute("msg",
						"<font color='red'>用户名、密码错误或者用户未激活！</font>");
				return "/jsp/login.jsp";
			}
			// 登陆成功：自动登录
			String auto = req.getParameter("autoLogin");
			if ("true".equals(auto)) {
				Cookie cookie = new Cookie("autoLogin", findUser.getUsername()
						+ "#" + findUser.getPassword());

				cookie.setPath(req.getContextPath());
				cookie.setMaxAge(7 * 24 * 60 * 60);
				resp.addCookie(cookie);
			}
			//记住用户名
			String remember = req.getParameter("remember");
			if("true".equals(remember)){
				Cookie cookie = new Cookie("remember", findUser.getUsername());
				cookie.setPath(req.getContextPath());
				cookie.setMaxAge(24*60*60);
				resp.addCookie(cookie);
			}
			req.getSession().setAttribute("user", findUser);
			resp.sendRedirect(req.getContextPath() + "/index.jsp");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

	}

	/**
	 * 用户退出
	 */
	public String loginOut(HttpServletRequest req, HttpServletResponse resp) {
		// 销毁session
		req.getSession().invalidate();
		// 清空cookie
		Cookie cookie = new Cookie("autoLogin", "");
		cookie.setPath(req.getContextPath());
		cookie.setMaxAge(0);
		resp.addCookie(cookie);
		try {
			resp.sendRedirect(req.getContextPath() + "/index.jsp");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return null;
	}
	
	/**
	 * 购物车界面，用户异步登陆  asyncLogin
	 */
	public String asyncLogin(HttpServletRequest req, HttpServletResponse resp){
		try{
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		User _user = new User();
		_user.setUsername(username);
		_user.setPassword(password);
		UserService us = (UserService) BeanFactory.getBean("UserService");
		User user = us.login(_user);
		Map<String,Object> map = new LinkedHashMap<String, Object>();
		if(user == null){
			map.put("flag",false);
			map.put("message", "用户名或密码错误");
		}else{
			map.put("flag",true);
			map.put("message", "success");
			req.getSession().setAttribute("user", user);
		}
		JSONObject jsonobj = JSONObject.fromObject(map);
		resp.getWriter().println(jsonobj.toString());
		
		return null;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
