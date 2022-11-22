package top.wang3.staffmanagement.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    /**
     * 实体类字段映射的数据库字段
     * 只支持简单的实体类映射
     * @return 映射的列名 例如：@Column("user_id")
     */
    String value();
}
