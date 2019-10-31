package ocm.zhangfeng.mybatis;

/**
 * @author zhangfeng
 * @create 2019-10-31-9:46
 **/
public class PageQuery {

    protected int pageSize;

    protected int pageNO;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNO() {
        return pageNO;
    }

    public void setPageNO(int pageNO) {
        this.pageNO = pageNO;
    }
}
