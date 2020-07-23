package cn.sm.entity;

public class SqlParameter {
    String SelectKey;
    String[] SelectMultKey;

    Object SelectValue;
    Object[] SelectAndValue;
    Object[] SelectOrValue;

    public String getSelectKey() {
        return SelectKey;
    }

    public void setSelectKey(String selectKey) {
        SelectKey = selectKey;
    }

    public String[] getSelectMultKey() {
        return SelectMultKey;
    }

    public void setSelectMultKey(String[] selectMultKey) {
        SelectMultKey = selectMultKey;
    }

    public Object getSelectValue() {
        return SelectValue;
    }

    public void setSelectValue(Object selectValue) {
        SelectValue = selectValue;
    }

    public Object[] getSelectAndValue() {
        return SelectAndValue;
    }

    public void setSelectAndValue(Object[] selectAndValue) {
        SelectAndValue = selectAndValue;
    }

    public Object[] getSelectOrValue() {
        return SelectOrValue;
    }

    public void setSelectOrValue(Object[] selectOrValue) {
        SelectOrValue = selectOrValue;
    }
}
