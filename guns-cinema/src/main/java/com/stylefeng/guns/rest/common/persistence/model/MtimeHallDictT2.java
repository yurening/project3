package com.stylefeng.guns.rest.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 地域信息表
 * </p>
 *
 * @author stylefeng
 * @since 2019-11-29
 */
@TableName("mtime_hall_dict_t")
public class MtimeHallDictT2 extends Model<MtimeHallDictT2> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键编号
     */
    @TableId(value = "UUID", type = IdType.AUTO)
    private Integer uuid;
    /**
     * 显示名称
     */
    @TableField("show_name")
    private String hallName;
    /**
     * 座位文件存放地址
     */
    @TableField("seat_address")
    private String seatFile;

    private String soldSeats;

    private Integer price;

    private Integer hallFieldId;

    public Integer getHallFieldId() {
        return hallFieldId;
    }

    public void setHallFieldId(Integer hallFieldId) {
        this.hallFieldId = hallFieldId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getSoldSeats() {
        return soldSeats;
    }

    public void setSoldSeats(String soldSeats) {
        this.soldSeats = soldSeats;
    }

    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getSeatFile() {
        return seatFile;
    }

    public void setSeatFile(String seatFile) {
        this.seatFile = seatFile;
    }

    @Override
    protected Serializable pkVal() {
        return this.uuid;
    }

    @Override
    public String toString() {
        return "MtimeHallDictT{" +
        "uuid=" + uuid +
        ", hallName=" + hallName +
        ", seatFile=" + seatFile +
        "}";
    }
}
