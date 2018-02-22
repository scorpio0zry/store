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
	 * ��ת��ע��ҳ��
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String registUI(HttpServletRequest req, HttpServletResponse resp) {
		return "/jsp/register.jsp";
	}

	/**
	 * �û������첽У��
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
	 * ������첽У��
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
	 * �û�ע��
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String regist(HttpServletRequest req, HttpServletResponse resp) {
		try {
			// �ж���֤��
			String code1 = req.getParameter("code");
			String code2 = (String) req.getSession().getAttribute("code");
			if (StringUtils.isEmpty(code1) || StringUtils.isEmpty(code2)
					|| !code2.equalsIgnoreCase(code1)) {
				req.setAttribute("msg", "<font color='red'>��֤���������</font>");
				return "/jsp/register.jsp";
			}
			Map<String, String[]> map = req.getParameterMap();
			UserService us = (UserService) BeanFactory.getBean("UserService");
			User user = new User();
			//ConvertUtils.register(new MyDateConverter(), Date.class);
			BeanUtils.populate(user, map);

			us.save(user);
			// �����ʼ�
			//us.mail(user);
			req.setAttribute("msg", "ע��ɹ�����ȥ���伤��");
			//����ip��ַ
			req.setAttribute("active","<a href='http://47.100.213.177/store_v1.0/UserServlet?method=active&code="+user.getCode()+"'>��ݼ���(ֱ�ӵ�����ɼ���)</a>");
			return "/jsp/msg.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

	}

	/**
	 * ���伤��ȷ��
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
				req.setAttribute("msg", "����ʧ��!������ע��");
				return "/jsp/msg.jsp";
			}

			user.setState(2); // 2 ����״̬��
			user.setCode(null);
			us.update(user);
			//Ϊ���û��������ﳵ
			CartService cd = (CartService) BeanFactory.getBean("CartService");
			cd.createCart(user);
			req.setAttribute("msg", "����ɹ��������µ�¼");
			return "/jsp/msg.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

	}

	/**
	 * ��¼����UI
	 */
	public String loginUI(HttpServletRequest req, HttpServletResponse resp) {
		return "/jsp/login.jsp";
	}

	/**
	 * �û���¼
	 */
	public String login(HttpServletRequest req, HttpServletResponse resp) {
		try {
			// �ж���֤��
			String code1 = req.getParameter("code");
			String code2 = (String) req.getSession().getAttribute("code");
			if (StringUtils.isEmpty(code1) || StringUtils.isEmpty(code2)
					|| !code2.equalsIgnoreCase(code1)) {
				req.setAttribute("msg", "<font color='red'>��֤���������</font>");
				return "/jsp/login.jsp";
			}
			Map<String, String[]> map = req.getParameterMap();
			UserService us = (UserService) BeanFactory.getBean("UserService");
			User user = new User();
			ConvertUtils.register(new MyDateConverter(), Date.class);
			BeanUtils.populate(user, map);
			User findUser = us.login(user);
			// δ�ҵ��û���
			if (findUser == null) {
				req.setAttribute("msg",
						"<font color='red'>�û����������������û�δ���</font>");
				return "/jsp/login.jsp";
			}
			// ��½�ɹ����Զ���¼
			String auto = req.getParameter("autoLogin");
			if ("true".equals(auto)) {
				Cookie cookie = new Cookie("autoLogin", findUser.getUsername()
						+ "#" + findUser.getPassword());

				cookie.setPath(req.getContextPath());
				cookie.setMaxAge(7 * 24 * 60 * 60);
				resp.addCookie(cookie);
			}
			//��ס�û���
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
	 * �û��˳�
	 */
	public String loginOut(HttpServletRequest req, HttpServletResponse resp) {
		// ����session
		req.getSession().invalidate();
		// ���cookie
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
	 * ���ﳵ���棬�û��첽��½  asyncLogin
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
			map.put("message", "�û������������");
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
