package com.jiagouedu.core.oss;

import java.io.File;

/**
 * Created by dylan on 15-5-20.
 * TODO
 */
public interface OssManager {
    public String getOssType();

    /**
     * 保存文件
     * @param filePath 保存的文件相对路径
     * @param file 要保存的文件
     * @return
     */
    public boolean storeFile(String filePath, File file);
}
