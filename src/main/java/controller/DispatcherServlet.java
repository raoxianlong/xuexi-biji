package controller;

import annotation.Controller;
import annotation.RequestMapping;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;
import utils.BeanHandle;
import utils.ReflectUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *  主控制器
 */
@WebServlet(urlPatterns = "/admin/*")
public class DispatcherServlet extends HttpServlet {

    private ResourceBundle pageResources;
    private ResourceBundle messageResources;
    private ResourceBundle applicationResources;

    private static final  String PAGES_PATH = "pages";
    private static final  String MESSAGES_PATH = "messages";
    private static final  String APPLICATION_PATH = "application";
    private static final Map<String, Action> ACTION_MAP = new HashMap<>();
    private static String pagesPrefix;
    private static String pagesSuffix ;
    private static ServletContext context;

    public HttpServletRequest request;
    public HttpServletResponse response;
    public SmartUpload smart;


    @Override
    public void init(ServletConfig config) throws ServletException {
        //资源文件初始化
        super.init(config);
        this.context = config.getServletContext();
        Locale locale = Locale.getDefault();
        this.messageResources = ResourceBundle.getBundle(MESSAGES_PATH, locale);
        this.pageResources = ResourceBundle.getBundle(PAGES_PATH, locale);
        this.applicationResources = ResourceBundle.getBundle(APPLICATION_PATH, locale);

        // 初始化接口
        initActionMapping();

        // 初始化前缀和后缀
        pagesPrefix =  this.applicationResources.getString("page.prefix");
        pagesSuffix =  this.applicationResources.getString("page.suffix");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        this.request = request;
        this.response = response;
        String path = "404";
        String uri = request.getRequestURI();
        uri = uri.replaceAll("/{2,}", "/");

        Action action = ACTION_MAP.get(uri);

        if (action != null){
            Method method = action.getMethod();


            Enumeration<String> enu =  null;
            String contentType = this.request.getContentType();
            List<String> pararNames = new ArrayList<>();
            if (contentType != null && contentType.contains("multipart/form-data")){
                try {
                    this.smart = new SmartUpload();
                    this.smart.initialize(super.getServletConfig(), request, response);
                    this.smart.upload();
                    enu = this.smart.getRequest().getParameterNames();
                } catch (SmartUploadException e) {
                    e.printStackTrace();
                }
            }else{
                enu = this.request.getParameterNames();
            }
            Object[] paramValues = null;
            if (method.getParameterTypes().length > 0 ){
                while(enu.hasMoreElements()){
                    pararNames.add(enu.nextElement());
                }
                paramValues = new Object[method.getParameterTypes().length];
                Parameter[] parameters = method.getParameters();
                for (int k=0; k < method.getParameterTypes().length; k++){
                    if (method.getParameterTypes()[k].getSimpleName().contains("ServletRequest")){
                        paramValues[k] = request;
                    }else if (method.getParameterTypes()[k].getSimpleName().contains("ServletResponse")){
                        paramValues[k] = response;
                    }else{

                       for (String paramName : pararNames){
                            Object vlaue = null;
                            if (paramName.contains(".")){
                                if (parameters[k].getName().contains(paramName.substring(0, paramName.indexOf(".")))){
                                    try {
                                        if (paramValues[k] == null){
                                            paramValues[k] = parameters[k].getType().newInstance();
                                        }
                                        if (method.getParameterTypes()[k].getSimpleName().contains("[]")){
                                            vlaue = getValues(paramName);
                                        }else{
                                            vlaue = getValue(paramName);
                                        }
                                        new BeanHandle(paramValues[k], paramName, vlaue);
                                    } catch (InstantiationException e) {
                                        e.printStackTrace();
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }else{
                                if (parameters[k].getName().contains(paramName)){
                                    if (method.getParameterTypes()[k].getSimpleName().contains("[]")){
                                        paramValues[k] = getValues(paramName);
                                    }else{
                                        paramValues[k] = getValue(paramName);
                                    }
                                    String value =  (String) paramValues[k];
                                    if (parameters[k].getType().getSimpleName().equals("Double") || parameters[k].getType().getSimpleName().equals("double")){
                                        paramValues[k] =  Double.parseDouble(value);
                                    }else if (parameters[k].getType().getSimpleName().equals("Integer") || parameters[k].getType().getSimpleName().equals("int")){
                                        paramValues[k] =  Integer.parseInt(value);
                                    }else if (parameters[k].getType().getSimpleName().equals("Short") || parameters[k].getType().getSimpleName().equals("short")){
                                        paramValues[k] =   Short.parseShort(value);
                                    }else if (parameters[k].getType().getSimpleName().equals("Float") || parameters[k].getType().getSimpleName().equals("float")){
                                        paramValues[k] =   Float.parseFloat(value);
                                    }else if (parameters[k].getType().getSimpleName().equals("Byte") || parameters[k].getType().getSimpleName().equals("byte")){
                                        paramValues[k] =  Byte.parseByte(value);
                                    }else if (parameters[k].getType().getSimpleName().equals("Long") || parameters[k].getType().getSimpleName().equals("long")){
                                        paramValues[k] =  Long.parseLong(value);
                                    }else if (parameters[k].getType().getSimpleName().equals("Boolean") || parameters[k].getType().getSimpleName().equals("boolean")){
                                        paramValues[k] =  Boolean.parseBoolean(value);
                                    }else if (parameters[k].getType().getSimpleName().equals("Date")){
                                        try {
                                            paramValues[k] = new SimpleDateFormat("yyyy-MM-dd").parse(value);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
            try {
                path = (String)action.getMethod().invoke(action.getActionClass().newInstance(), paramValues);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }

        }
        request.getRequestDispatcher(this.initUrl(path)).forward(request, response);

    }




    /**
     *  获取参数值
     * @param paramName 参数名称
     * @return  参数值
     */
    public String getValue(String paramName){
        String contentType = this.request.getContentType();
        if (contentType != null && contentType.contains("multipart/form-data")){
            return this.smart.getRequest().getParameter(paramName);
        }else{
            return this.request.getParameter(paramName);
        }
    }

    /**
     *  获取参数值
     * @param paramName 参数名称
     * @return  参数值
     */
    public String[] getValues(String paramName){
        String contentType = this.request.getContentType();
        if (contentType != null && contentType.contains("multipart/form-data")){
            return this.smart.getRequest().getParameterValues(paramName);
        }else{
            return this.request.getParameterValues(paramName);
        }
    }

    /**
     *  初始化接口mapper
     */
    private void initActionMapping(){
        String basePackage = this.applicationResources.getString("scan.basePackage");
        String baseDir = context.getRealPath("/");
        File file = new File(baseDir + File.separator +"WEB-INF" + File.separator
                        + "classes" + File.separator + basePackage.replaceAll("\\.", File.separator));
        iteratorFiles(1, file, basePackage);
    }

    /**
     *  迭代文件
     * @param file
     */
    private void iteratorFiles(int count, File file, String packagePath){
        if (file.isDirectory()){
            File[] files = file.listFiles();
            if(count > 1){
                packagePath += "." + file.getName();
            }
            count ++;
            for (File f : files){
                iteratorFiles(count,f, packagePath);
            }
        }else{
            try {
                if (file.getName().contains(".class")){
                    packagePath += "." + file.getName().substring(0, file.getName().lastIndexOf("."));
                    Class<?> clazz = Class.forName(packagePath);
                    Controller controller = clazz.getAnnotation(Controller.class);
                    if (controller != null){
                        String baseUrl= "";
                        RequestMapping classReqMapping = clazz.getAnnotation(RequestMapping.class);
                        if (classReqMapping != null){
                            baseUrl = classReqMapping.value();
                        }
                       Method[] methods = clazz.getDeclaredMethods();
                       for (Method method : methods){
                            String actionUrl = baseUrl;
                            RequestMapping methodReq = method.getAnnotation(RequestMapping.class);
                            if (methodReq != null){
                                Action action = new Action(clazz, method);
                                actionUrl += "/" +  methodReq.value();
                                actionUrl =  actionUrl.replaceAll("/{2,}","/") + ".action";
                                System.out.println("[info] 初始化接口：" + actionUrl);
                                ACTION_MAP.put(actionUrl, action);
                            }
                       }
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  构造返回值
     * @return
     */
    public String initUrl(String path){
        return DispatcherServlet.pagesPrefix + path + DispatcherServlet.pagesSuffix;
    }

}
