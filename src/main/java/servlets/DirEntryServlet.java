package servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/dir")
public class DirEntryServlet extends HttpServlet {
    //toDo
    //with name dirEntry.jsp
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        String path = URLDecoder.decode(request.getParameter("path"), StandardCharsets.UTF_8);
        PrintWriter printWriter = response.getWriter();
        printWriter.println(HtmlContentMaker.getContent(path));
    }

}
