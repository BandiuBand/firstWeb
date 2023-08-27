package servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Files;

@WebServlet("/openTxt")
public class OpenTxtServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        Writer writer = response.getWriter();
        String path = URLDecoder.decode(request.getParameter("path"),"UTF-8");
        File file = new File(HtmlContentMaker.getRoot()+"/"+path);
        writer.write(getHtmlContent(file));

    }
    private String getHtmlContent(File file) throws IOException {
        return new StringBuilder()
                .append(getContentBefore(file))
                .append(getText(file))
                .append(getContentAfter()).toString();
    }

    private String getContentAfter() {
        return "</p>\n" +
                "</body>\n" +
                "</html>";
    }

    private String getContentBefore(File file) {
        String fileName = file.getName();
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">" +
                "    <title>"+fileName+"</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <a href=\"javascript:history.back()\">Назад</a>\n" +
                "    <p>";
    }

    private String getText(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        if (file.exists()&&file.getName().endsWith(".txt")) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){
                String line = null;
                while ((line = bufferedReader.readLine())!=null) {
                    content.append(line).append("<br>");
                }
            }
        }
        return content.toString();
    }
}
