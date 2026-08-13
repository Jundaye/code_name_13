package ab;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import org.eclipse.tags.shaded.org.apache.xml.res.XMLErrorResources_pl;

// jsp나 html이 .do로 다끝나면 이 서블릿 다 처리해줍니다...
@WebServlet("*.do") 
public class AddrBookController extends HttpServlet {
   
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String page ="index.jsp";
		String uri = req.getRequestURI(); // http://localhost8080/empapp/list.do
		String requestUri = uri.substring(uri.lastIndexOf("/"), uri.length()); // 마지막/ 
		System.out.println("requestUri=" + requestUri); 
		switch (requestUri) {
		case "/form.do" : {
			page = "addrbook_form.jsp";
			break;
		}
		case "/list.do" : {
			//model 의 메서드를 호출하고 결과를 request에 넣는다.
			List<AddrBookTO> list = AddrBookDAO.getList();
			req.setAttribute("data", list);
			page = "addrbook_list.jsp";
			break;
		}
		case "/insert.do" : {
			AddrBookTO ab = makeAddrBook(req);
			AddrBookDAO.insert(ab);
			List<AddrBookTO> list = AddrBookDAO.getList();
			req.setAttribute("data", list);
			page = "addrbook_list.jsp";
			//list.do 로 포워딩한다. or sendRedirect
			break;
		}
		default:
		}
		RequestDispatcher rd = req.getRequestDispatcher(page);
		rd.forward(req, resp);
	}

	private AddrBookTO makeAddrBook(HttpServletRequest req) {
		 AddrBookTO ab = new AddrBookTO();

		    ab.setAbName(req.getParameter("abName"));
		    ab.setAbEmail(req.getParameter("abEmail"));
		    ab.setAbComdept(req.getParameter("abComdept"));
		    ab.setAbBirth(req.getParameter("abBirth"));
		    ab.setAbTel(req.getParameter("abTel"));
		    ab.setAbMemo(req.getParameter("abMemo"));

		    return ab;
	}	
	
}