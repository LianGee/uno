package com.bigdata.uno.common.model.base;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseEntity<T, K extends Comparable<K>>
        implements Serializable, Cloneable {

    @Column(name = "id", comment = "主键", isPK = true, searchable = true)
    protected K id;

    @Column(name = "created_at", comment = "创建时间")
    protected Long createdAt;

    @Column(name = "updated_at", comment = "修改时间")
    protected Long updatedAt;

    @Column(name = "is_delete", comment = "是否已删除")
    protected Boolean isDelete;

    /**
     * @return 复制对象
     */
    @SuppressWarnings("unchecked")
    @Override
    public T clone() {
        try {
            return (T) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("cannot clone object: " + getClass());
        }
    }
}
