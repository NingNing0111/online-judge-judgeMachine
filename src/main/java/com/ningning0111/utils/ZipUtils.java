package com.ningning0111.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ZipUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * @Project: com.ningning0111.utils
 * @Author: pgthinker
 * @GitHub: https://github.com/ningning0111
 * @Date: 2024/3/2 17:59
 * @Description:
 */
public class ZipUtils {
    /**
     * 获取压缩包下指定结尾字段里的内容，不支持压缩包中存在目录
     *
     * @param zipPath
     * @param fileEndSuffix
     * @return
     */
    public static List<String> getContents(
            String zipPath,
            String fileEndSuffix
    ) throws IOException {
        if(!FileUtil.exist(zipPath)){
            System.out.println("err");
            return null;
        }
        File unzip = ZipUtil.unzip(zipPath);

        String caseRootPath = unzip.getAbsolutePath();
        List<String> fileNames = FileUtil.listFileNames(caseRootPath);
        fileNames.sort(String::compareTo);
        List<String> contentList = new ArrayList<>();
        for (String filename : fileNames) {
            if (filename.endsWith(fileEndSuffix)) {
                String content = FileUtil.readString(caseRootPath + File.separator + filename, CharsetUtil.CHARSET_UTF_8);
                contentList.add(content);
            }
        }
        FileUtil.del(unzip);
        return contentList;
    }

    /**
     * 将内容列表按照从1递增的顺序存储为文件，并压缩到指定路径。
     * 例如saveContents("test_case/1000.zip",["1 3","3 4"], ".in")。
     * 1000.zip下有两个文件，分别是1.in,和2.in，内部分别存储"1 3"和"3 4"
     *
     * @param zipPath
     * @param contents
     * @param childrenFileName
     * @param append 是否往zip中追加
     */
    public static void saveContents(
            String zipPath,
            String contents,
            String childrenFileName,
            boolean append
    ) throws IOException {
        System.out.println(zipPath);
        int end = zipPath.indexOf(".zip");
        System.out.println(end);
        String dirPath = zipPath.substring(0,zipPath.indexOf(".zip"));

        // 先获取 /test_case/questionId文件夹
        if(!FileUtil.exist(zipPath)){
            // 如果zip文件不存在，则创建一个文件夹
            FileUtil.mkdir(dirPath);
        }else{
            // 如果zip文件存在，则解压文件，并删除原zip文件
            ZipUtil.unzip(zipPath);
            FileUtil.del(zipPath);
        }
        // 如果不是追加文件则清空文件夹里的内容
        if(!append){
            FileUtil.clean(dirPath);
        }
        // 如果是追加内容
        // 1.创建文件
        File file = FileUtil.newFile(dirPath + File.separator + childrenFileName);
        // 2. 写入内容
        FileUtil.writeString(contents,file,CharsetUtil.CHARSET_UTF_8);
        // 压缩
        ZipUtil.zip(dirPath);
        // 删除文件夹
        FileUtil.del(dirPath);
    }

    public static void clearFile(
            String zipPath
    ){
        File unzip = ZipUtil.unzip(zipPath);
        FileUtil.del(zipPath);
        FileUtil.clean(unzip);
        ZipUtil.zip(unzip.getPath());
    }
}
