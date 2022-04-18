package com.xbh.politemic.common.util;

import cn.hutool.dfa.WordTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @SensitiveKeyWordFilter: 敏感词过滤
 * @author: ZBoHang
 * @time: 2021/10/15 16:56
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SensitiveWordFilter {

    private static final Logger log = LoggerFactory.getLogger(SensitiveWordFilter.class);

    public static final WordTree tree;

    static {

        tree = new WordTree();

        init();
    }

    /**
     * 初始化
     */
    private static void init() {
        try (
                InputStream is = ClassLoader.getSystemResourceAsStream("sensitive-words.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ) {
            String keyword;
            while ((keyword = reader.readLine()) != null) {
                // 添加到前缀树
                tree.addWord(keyword);
            }
        } catch (IOException e) {
            log.error("@@@@@敏感词文件加载失败");
        }
    }

    /**
     * 指定文本是否包含树中的词
     */
    public static boolean isContainsSensitiveWord(String text) {
        return tree.isMatch(text);
    }

}
