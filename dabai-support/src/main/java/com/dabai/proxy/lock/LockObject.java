package com.dabai.proxy.lock;

import jdk.nashorn.internal.ir.annotations.Immutable;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * 锁对象<br>
 *
 * @author zhujinshan
 */
@Immutable
public class LockObject implements Comparable<LockObject>, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 对象类型
     */
    private final String type;

    /**
     * 对象ID
     */
    private final String id;

    public LockObject(String type, String id) {
        super();
        Assert.notNull(type, "type is required");
        Assert.notNull(id, "id is required");
        if (type.contains("/") || type.contains("\\")) {
            throw new IllegalArgumentException("invalid type, cannot contains '/' or '\\'");
        }
        this.type = type;
        this.id = id;
    }

    public static LockObject of(String type, String id) {
        return new LockObject(type, id);
    }

    public static LockObject of(String type, Number id) {
        Assert.notNull(id, "id is required");
        return new LockObject(type, id.toString());
    }

    /**
     * @return 对象类型
     */
    public String getType() {
        return type;
    }

    /**
     * @return 对象ID
     */
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return type + '/' + id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof LockObject)) {
            return false;
        }
        LockObject other = (LockObject) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (type == null) {
            return other.type == null;
        } else {
            return type.equals(other.type);
        }
    }

    @Override
    public int compareTo(LockObject o) {
        int compareType = this.type.compareTo(o.type);
        if (compareType != 0) {
            return compareType;
        }
        return this.id.compareTo(o.id);
    }

}
