package cn.nnnight.vo;

import java.util.List;

public class PhotoOperateVo {

    private String operate;
    private List<String> ids;

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
