package com.xhh.javaApi.bio;

import annotation.Controller;
import annotation.RequestMapping;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

public class FileDemo {

    /**
     * 创建文件
     */
    @Test
    public void creatFile() throws IOException {
        File file = new File("D:" + File.separator + "test.txt");
        if (file.exists()){
            file.delete();
        }else{
            file.createNewFile();
        }
    }


    /**
     * File基础方法
     */
    @Test
    public void fileBaseMethod() throws IOException {
        File file = new File("D:" + File.separator +
                    "raoxianlong" + File.separator + "304318.jpg");
        if (!file.exists()){
            // 所以在创建文件之前需要判断父路径是否存在
            if (! file.getParentFile().exists()){
                // 因为该目录不存在，所以直接创建文件会抛 java.bio.IOException: 系统找不到指定的路径。
                // 不存在则需要创建父目录，注意这里如果用 mkdir()方法也是不会抛异常，
                // 如果目录有多级则只能用mkdirs()，然后再创建文件
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
        System.out.println("文件路径：" + file.getPath());
        System.out.println("是否是文件：" + file.isFile()); // 如果文件不存在，那么也会返回false
        System.out.println("是否是文件夹：" + file.isDirectory());
        System.out.println("是否是隐藏文件：" + file.isHidden());
        System.out.println("文件/目录名称：" + file.getName());
        System.out.println("文件大小:" + new BigDecimal((double) file.length() / (1024 * 1024)).
                            divide(new BigDecimal(1), 2, BigDecimal.ROUND_HALF_UP) + "M");
        System.out.println("文件最后一次修改时间:" +
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(file.lastModified()));
    }

    @Test
    public void fileList(){
        File file = new File("d:" + File.separator);
        listFiles(file);
    }
    /**
     *  递归文件
     */
    private void listFiles(File file){
        if (file.isDirectory()){
            File[] files = file.listFiles();
            if (files != null){
                System.out.printf("%1$-60s%2$-30s%3$-10s", "文件名称", "最后修改时间", "文件大小");
                System.out.println();
                for (File f : files){
                    System.out.printf("%1$-60s%2$-30s%3$-10s", f.getName(),
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(file.lastModified())
                                    , new BigDecimal((double) file.length() / (1024 * 1024)).
                                    divide(new BigDecimal(1), 2, BigDecimal.ROUND_HALF_UP) + "M");
                    System.out.println();
                }
            }
        }
    }


    @Test
    public void testDir(){
        String baseDir = System.getProperty("user.dir");
        File file = new File(baseDir+ File.separator +  "src\\main\\java\\com\\xhh\\javaApi");
        iteratorFiles(1, file, "com.xhh.javaApi");
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
                if (file.getName().contains(".java")){
                    packagePath += "." + file.getName().substring(0, file.getName().lastIndexOf("."));
                    Class<?> clazz = Class.forName(packagePath);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testActioinMapping(){
        String baseDir = System.getProperty("user.dir");
        File file = new File(baseDir+ File.separator +  "src\\main\\java\\com\\xhh\\javaApi\\controller");
        initActionMapping(1, file, "com.xhh.javaApi.controller");
    }

    /**
     *  初始化接口
     * @param file
     */
    private void initActionMapping(int count, File file, String packagePath){
        if (file.isDirectory()){
            File[] files = file.listFiles();
            if(count > 1){
                packagePath += "." + file.getName();
            }
            count ++;
            for (File f : files){
                initActionMapping(count,f, packagePath);
            }
        }else{
            try {
                if (file.getName().contains(".java")){
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
                                actionUrl += "/" +  methodReq.value();
                                actionUrl =  actionUrl.replaceAll("/{2,}","/");
                                System.out.println(actionUrl);
                            }
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
