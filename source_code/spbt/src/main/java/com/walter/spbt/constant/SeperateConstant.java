package com.walter.spbt.constant;

/**
 * SeperateConstant
 *
 * @author sunwenhao
 * @date 2020/2/26 10:25
 */
public enum SeperateConstant {
    /**
     * 分号
     */
    SEPERATE_FH(";"),
    /**
     * 英文点
     */
    SEPERATE_DOC("."),
    /**
     * 中文逗号
     */
    SEPERATE_ZWDH("，"),
    /**
     * 英文逗号
     */
    SEPERATE_YWDH(","),
    /**
     * 中文冒号
     */
    SEPERATE_ZWMH("："),
    /**
     * 英文冒号
     */
    SEPERATE_YWMH(":"),
    /**
     * 横杠
     */
    SEPERATE_HG("-"),
    /**
     * 下划线
     */
    SEPERATE_XHX("_"),
    /**
     * 斜杠
     */
    SEPERATE_XG("/"),
    /**
     * 反斜杠
     */
    SEPERATE_FXG("\\"),
    /**
     * 顿号
     */
    SEPERATE_DH("、");
    private final String seperate;

    /**
     * 构造
     *
     * @param seperate
     */
    SeperateConstant(String seperate) {
        this.seperate = seperate;
    }

    /**
     * 获取分割符号
     *
     * @return string
     */
    public String getSeperate() {
        return this.seperate;
    }

}
