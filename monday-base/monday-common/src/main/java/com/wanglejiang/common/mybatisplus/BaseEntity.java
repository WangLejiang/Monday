package com.wanglejiang.common.mybatisplus;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.groups.Default;
import java.io.Serializable;
import java.util.Date;

@ToString
@Getter
@Setter
public abstract class BaseEntity<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String CREATED_TIME = "createdTime";
    public static final String LAST_UPDATED_TIME = "modifiedTime";
    public static final String MODIFIED_BY = "modifiedBy";
    public static final String CREATED_BY = "createdBy";
    public static final String IS_DELETED = "isDeleted";
    public static final String TENANT_ID = "tenantId";


    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键ID")
    protected T id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间【后台】")
    @TableField(fill = FieldFill.INSERT)
    protected Date createdTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "最后修改时间【后台】")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected Date modifiedTime;

    @ApiModelProperty(value = "创建人【后台】")
    @TableField(fill = FieldFill.INSERT)
    protected String createdBy;

    @ApiModelProperty(value = "修改人【后台】")
    @TableField(fill = FieldFill.UPDATE)
    protected String modifiedBy;

    @ApiModelProperty(value = "租户ID")
    @TableField(fill = FieldFill.INSERT)
    protected Long tenantId;

    @TableLogic
    @TableField(value = "IS_DELETED", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "是否删除【后台】")
    protected Boolean isDeleted;

}