package com.stylefeng.guns.rest.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

@Data
public class CinemaFieldsInfo extends Model<CinemaFields> {
    private static final long serialVersionUID = 1L;

    MtimeCinemaT2 cinemaInfo;

    MtimeHallFilmInfoT2 filmInfo;

    MtimeHallDictT2 hallInfo;

    @TableId(value = "UUID", type = IdType.AUTO)
    private Integer uuid;

    @Override
    protected Serializable pkVal() {
        return this.uuid;
    }

}
