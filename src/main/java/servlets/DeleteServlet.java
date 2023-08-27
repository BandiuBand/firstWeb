package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = URLDecoder.decode(req.getParameter("path"),"UTF-8");
        File file = new File(HtmlContentMaker.getRoot()+"/"+path);
        path = path.replace("\\","/");
        String pathWithoutTypeFile = path.substring(0,path.lastIndexOf("."));
        String directory = pathWithoutTypeFile.substring(0,pathWithoutTypeFile.lastIndexOf("/")+1);

        System.out.println("delete file: "+file.getName()+"  result: "+ file.delete());

        String root = HtmlContentMaker.getRoot();
        String servletPath = req.getContextPath() + "/dir";
        String fullPath = servletPath + "?path=/"+directory;
        System.out.println("return to directory: "+fullPath);
        resp.setCharacterEncoding("UTF-8");
        resp.sendRedirect(fullPath);
    }
}
