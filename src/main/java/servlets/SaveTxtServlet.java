package servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/save")
public class SaveTxtServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        String path = request.getParameter("path");
        String fileName = request.getParameter("fileName");
        String data = request.getParameter("contentTxt");

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        Writer writer = response.getWriter();

        boolean statusSave = saveFile(path,fileName,data);

        writer.write(getPreviousPageHtmlWithoutEndTe(path));

        if (statusSave)
            writer.write(getSuccessResponse());
        else
            writer.write(getUnSuccessResponse());
        writer.write("</body>\n" +
                            "</html>");

    }

    private String getPreviousPageHtmlWithoutEndTe(String path) {
        String content = HtmlContentMaker.getContent(path);
        return content.replace("</body>\n" +
                "</html>","");
    }

    private String getUnSuccessResponse() {

        String message = "Помилка збереження файлу";
        String htmlResponse = "<html><head><script>window.onload = function() { alert('" + message + "'); }</script></head><body></body></html>";


        return htmlResponse;
    }

    private String getSuccessResponse() {
        String message = "Файл завантажено";
        String htmlResponse = "<html><head><script>window.onload = function() { alert('" + message + "'); }</script></head><body></body></html>";


        return htmlResponse;
    }

    private boolean saveFile (String path, String fileName,String data) throws IOException {
        File file = new File(pathOfFile(path,fileName));
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))){
            bufferedWriter.write(data);
            bufferedWriter.flush();
            return true;
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    private String pathOfFile(String path, String fileName) {
    return HtmlContentMaker.getRoot()+path+"/"+fileName+".txt";
    }


}
