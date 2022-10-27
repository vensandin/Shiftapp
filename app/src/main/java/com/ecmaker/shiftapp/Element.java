package com.ecmaker.shiftapp;

public class Element {
    /** 文字內容 */
    private String contentText;
    /** 在tree中的層級 */
    private int level;
    /** 元素的id */
    private int id;
    /** 父元素的id */
    private int parendId;
    /** 是否有子元素 */
    private boolean hasChildren;
    /** item是否展開 */
    private boolean isExpanded;
    /** 表示該節點沒有父元素，也就是level為0的節點 */
    public static final int NO_PARENT = -1;
    /** 表示該元素位於最頂層的層級 */
    public static final int TOP_LEVEL = 0;
    /**圖片*/
    private int icon;
    public Element(int icon, String contentText, int level, int id, int parendId,
                   boolean hasChildren, boolean isExpanded) {
        super();
        this.icon = icon;
        this.contentText = contentText;
        this.level = level;
        this.id = id;
        this.parendId = parendId;
        this.hasChildren = hasChildren;
        this.isExpanded = isExpanded;
    }
    public int getIcon(){
        return icon;
    }
    public void setIcon(int icon){
        this.icon = icon;
    }
    public boolean isExpanded() {
        return isExpanded;
    }
    public void setExpanded(boolean isExpanded) {
        this.isExpanded = isExpanded;
    }
    public String getContentText() {
        return contentText;
    }
    public void setContentText(String contentText) {
        this.contentText = contentText;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getParendId() {
        return parendId;
    }
    public void setParendId(int parendId) {
        this.parendId = parendId;
    }
    public boolean isHasChildren() {
        return hasChildren;
    }
    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }
}
