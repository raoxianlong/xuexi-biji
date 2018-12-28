package com.xhh.javaApi.bio;


import org.junit.Test;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 *  实现压缩和解压缩
 */
public class ZipDemo {

    static File file = new File("D:" + File.separator + "raoxianlong");

    /**
     *  压缩
     */
    @Test
    public void compress() throws IOException {
        ZipOutputStream zipOutputStream =  new ZipOutputStream(new FileOutputStream(new File(file, "demo.zip")));
        compress(file, zipOutputStream, "");
        zipOutputStream.close();
    }

    /**
     *  递归压缩文件
     * @param f 压缩文件目录
     * @param zipOutputStream 压缩流
     * @param parentPath 压缩文件的父路径
     * @throws IOException
     */
    private void compress(File f, ZipOutputStream zipOutputStream, String parentPath) throws IOException {
        if (f.isDirectory()){
            parentPath += f.getName() + File.separator;
            for (File f1 : f.listFiles()){
                if (f1.getName().equals("demo.zip")){
                    continue;
                }
                compress(f1, zipOutputStream, parentPath);
            }
        }else{
            InputStream in = new FileInputStream(f);
            ZipEntry zipEntry = new ZipEntry(parentPath + f.getName());
            zipOutputStream.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1){
                zipOutputStream.write(buffer, 0, len);
            }
            in.close();
            zipOutputStream.closeEntry();
        }
    }

    /**
     *  解压缩
     */
    @Test
    public void decompression() throws IOException {
        File dcFile = new File(file, "demo.zip");  // 压缩文件
        File pFile = new File(dcFile.getParentFile(), "dDemo"); // 解压缩到该文件夹下
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(dcFile));
        decompression(zipInputStream, pFile);
    }

    /**
     *  解压缩文件
     * @param zipInputStream
     * @throws IOException
     */
    private void decompression(ZipInputStream zipInputStream, File pFile) throws IOException {
        ZipEntry zipEntry;
        while ((zipEntry = zipInputStream.getNextEntry()) != null){
            File outFile = new File(pFile, zipEntry.getName());
            if (!outFile.getParentFile().exists()){
                outFile.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024 * 100];
            int len;
            while ((len = zipInputStream.read(buffer)) != -1){
                out.write(buffer, 0, len);
            }
            zipInputStream.closeEntry();
            out.close();
            }
        zipInputStream.close();
    }


}
