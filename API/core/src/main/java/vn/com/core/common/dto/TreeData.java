package vn.com.core.common.dto;

import vn.com.core.common.utils.ValidationUtil;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
public abstract class TreeData<T extends TreeData> {

    @Transient
    protected Long level;

    @Transient
    private boolean hasChild;

    @Transient
    private List<T> children;

    public abstract Long getLevel();

    public abstract Long getNodeId();

    public abstract Long getParentNodeId();

    public abstract boolean isRoot();

    public boolean isHasChild() {
        if (!ValidationUtil.isNullOrEmpty(children)) {

            return true;
        }
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    public List<T> getChildren() {
//		if (isHasChild()){
//			children = new ArrayList<TreeData>();
//		}
        return children;
    }

    public void addChild(T child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }

    public void setLevel(Long level) {
        this.level = level;
    }
}
