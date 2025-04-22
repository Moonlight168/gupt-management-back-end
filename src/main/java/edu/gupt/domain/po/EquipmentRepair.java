package edu.gupt.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName equipment_repair
 */
@TableName(value ="equipment_repair")
@Data
public class EquipmentRepair implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 宿舍ID
     */
    @TableField(value = "dormitory_id")
    private Integer dormitoryId;

    /**
     * 宿舍楼
     */
    @TableField(value = "building")
    private String building;

    /**
     * 房间号
     */
    @TableField(value = "room")
    private String room;

    /**
     * 报修类型
     */
    @TableField(value = "repair_type")
    private String repairType;

    /**
     * 报修内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 状态
     */
    @TableField(value = "status")
    private String status;

    /**
     * 提交时间
     */
    @TableField(value = "submit_time")
    private Date submitTime;

    /**
     * 完成时间
     */
    @TableField(value = "finish_time")
    private Date finishTime;

    /**
     * 备注
     */
    @TableField(value = "comment")
    private String comment;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}