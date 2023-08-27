package servlets;

import jdk.jfr.StackTrace;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.util.Objects;

public class HtmlContentMaker {
    private static final String parentDirectory = "d:/tomkaWeb/Dir/";
    private HtmlContentMaker()
    {}

    public static String getContent(String path){
        System.out.println("get content from - "+ path);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(contentBeforeTable());
        stringBuilder.append(getTable(path));
        stringBuilder.append(contentAfterTable());
        return stringBuilder.toString();
    }

    private static String getTable(String path){
        StringBuilder stringBuilder = new StringBuilder();//toDo
        File[] listOfFiles = getFileList(path);
        stringBuilder.append("<table>").append("\n");
        for (File file:listOfFiles) {
            if(file==null)
                System.out.println("fill is null");
            else {
                System.out.println(file.getName());
                stringBuilder.append(makeRow(file)).append("\n");
            }
        }
        stringBuilder.append("<table/>").append("\n");
        return stringBuilder.toString();
    }

    private static String contentAfterTable() {
        return  "</body>\n" +
                "</html>";
    }

    private static String contentBeforeTable() {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">" +
                "    <title>"+"Файловий Менеджер"+"</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <a href=\"/firstWeb/index\">Додому</a>\n";
    }

    private static File[] getFileList(String path){
        File[] filesList = new File[1];
        File directory = new File(parentDirectory+path);
        if (directory.exists()&&directory.isDirectory()) {
            filesList= (Objects.requireNonNull(directory.listFiles()));
        }
        else if(!directory.exists()) {
            System.out.println("isnt exist");
        }
        return filesList;
    }
    private static String makeRow(File file) {



        String name = file.getName();
        String pathOfFile = getPathOfFile(file);
        String href = getHrefToFile(file);
        String hrefOfDelete = getHrefDelete(file);//toDo
        return String.format("<tr><td>%s<td/><td>%s<td/><tr/>",href,hrefOfDelete);
    }
    private static String getPathOfFile(File file){
        File baseDir = new File(parentDirectory);
        Path base = baseDir.toPath();
        Path filePath = file.toPath();

        Path result = base.relativize(filePath);

        return result.toString();
    }
    private static String getHrefToFile(File file){

        if(file.isDirectory())
            return getHrefToDirectory(file);
        else if (isTxt(file))
            return getHrefToOpenTxt(file);
        else
            return getHrefToDownload(file);
    }

    private static String getHrefToDirectory(File file){
        String encodedFilePath = URLEncoder.encode(getPathOfFile(file)+"/");
        String result = "<a href=\"dir?path=" + encodedFilePath + "\">"+file.getName()+"</a>";
        return result;
    }

    private static String getHrefToOpenTxt(File file){
        String encodedFilePath = URLEncoder.encode(getPathOfFile(file)+"/");

        return "<a href=\"openTxt?path=" + encodedFilePath + "\">"+file.getName()+"</a>";
    }

    private static String getHrefToDownload(File file){
        String encodedFilePath = URLEncoder.encode(getPathOfFile(file));
        return "<a href=\"downloadFile?path=" + encodedFilePath + "\">"+file.getName()+"</a>";
    }

    private static String getHrefDelete(File file){
        String encodedFilePath = URLEncoder.encode(getPathOfFile(file)+"/");
        if(file.isDirectory())
            return "";
        return "<a href=\"delete?path=" + encodedFilePath + "\">"+"DELETE"+"</a>";
    }

    private static boolean isTxt(File file){

        return file.getName().endsWith(".txt");

    }
    public static String getRoot(){
        return parentDirectory;
    }
}
