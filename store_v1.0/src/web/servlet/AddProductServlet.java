package web.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.domain.Category;
import com.domain.Product;
import com.service.CategoryService;
import com.service.ProductService;
import com.utils.BeanFactory;
import com.utils.UUIDUtils;

/**
 * Servlet implementation class AddProductServlet
 */
public class AddProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			//��ô����ļ���������
			DiskFileItemFactory dfif = new DiskFileItemFactory();
			
			//���û������Ĵ�С
			dfif.setSizeThreshold(3 * 1024 * 1024); //������3M�����������ʱ�ļ�
			
			//ͨ��������ú��Ľ�����
			ServletFileUpload fileUpload = new ServletFileUpload(dfif);
			fileUpload.setHeaderEncoding("UTF-8"); //��������ļ���������
			
			//����request���󣬷���List���ϣ������е������Ƿָ��߷ֳɵ�ÿ������
			List<FileItem> list = fileUpload.parseRequest(request);
			Map<String,String> map = new LinkedHashMap<String, String>();
			//����ÿ������
			String path = this.getServletContext().getRealPath("/products/1");
			String filename = null;
			
			for (FileItem fileItem : list) {
				if(fileItem.isFormField()){
					//��ͨ��
					String name = fileItem.getFieldName();
					String value = fileItem.getString("utf-8"); //�����ͨ����������
					map.put(name, value);
				}else{
					//�ļ��ϴ���
					filename = fileItem.getName(); //��ȡ�ϴ��ļ���
					InputStream is = fileItem.getInputStream(); //��ȡ������
					
					
					OutputStream os = new FileOutputStream(path + "/" + filename);
					
					int len = 0;
					byte[] b = new byte[1024];
					while((len = is.read(b))!=-1){
						os.write(b,0,len);
					}
					
					is.close();
					os.close();
				}
			}
			Product product = new Product();
			BeanUtils.populate(product, map);
			product.setPid(UUIDUtils.getUUID());
			product.setPdate(new Date());
			product.setPflag(0);
			product.setPimage("products/1/"+filename );
			CategoryService cs =(CategoryService) BeanFactory.getBean("CategoryService");
			Category category = cs.findByCid(map.get("cid"));
			product.setCategory(category);
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			ps.save(product);
			response.sendRedirect(request.getContextPath() + "/AdminProductServlet?method=findByPage&currPage=1");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
