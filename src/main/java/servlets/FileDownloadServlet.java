package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;

@WebServlet("/downloadFile")
public class FileDownloadServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filePath = URLDecoder.decode(request.getParameter("path"),"UTF-8");
        String filePathFull = HtmlContentMaker.getRoot()+"/"+filePath;
        File file = new File(filePathFull);

        response.setContentType("application/octet-stream");
        System.out.println(filePathFull);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

        try (InputStream inputStream = new FileInputStream(file);
             OutputStream outputStream = response.getOutputStream()) {

            System.out.println("Start download");
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);

            }
        }
    }
}