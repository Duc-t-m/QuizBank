package Models;

public class Paging {

    private int n;
    private int nrpp;
    private int index;

    public Paging() {
    }

    public Paging(int n, int nrpp, int index) {
        this.n = n;
        this.nrpp = nrpp;
        this.index = index < 0 ? 0 : index;
    }
    private int totalPage;
    private int begin;
    private int end;

    public int getPageStart() {
        return pageStart;
    }

    public int getPageEnd() {
        return pageEnd;
    }

    public void setPageStart(int pageStart) {
        this.pageStart = pageStart;
    }

    public void setPageEnd(int pageEnd) {
        this.pageEnd = pageEnd;
    }
    private int pageStart;
    private int pageEnd;

    public void calculate() {
        totalPage = (n + nrpp - 1) / nrpp;
        begin = index * nrpp;
        end = begin + nrpp - 1 > n ? n - 1 : begin + nrpp - 1; //ket thuc tai end
        pageStart = index - 2 < 0 ? 0 : index - 2;
        pageEnd = index + 2 >= totalPage ? totalPage - 1 : index + 2;
        if(n==0)
            end=pageEnd=0;
    }

    public int getN() {
        return n;
    }

    public int getNrpp() {
        return nrpp;
    }

    public int getIndex() {
        return index;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getBegin() {
        return begin;
    }

    public int getEnd() {
        return end;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setNrpp(int nrpp) {
        this.nrpp = nrpp;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public void setEnd(int end) {
        this.end = end;
    }

}
