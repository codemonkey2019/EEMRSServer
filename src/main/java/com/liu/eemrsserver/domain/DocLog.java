package com.liu.eemrsserver.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author L
 * @date 2019-09-20 18:45
 * @desc 用于用户登录、注册、注销时接收数据库中的用户信息。
 * 用于判断用户提交的信息是否合法.
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocLog {
    @JSONField(serialize=false)
    private Integer id;

    private String idNumber;
    private String userName;
    private String department;

    @JSONField(serialize=false)
    private String password;
    @JSONField(serialize=false)
    private String idHashCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocLog docLog = (DocLog) o;
        return idNumber.equals(docLog.idNumber) &&
                department.equals(docLog.department) &&
                password.equals(docLog.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idNumber, password, userName);
    }
}
