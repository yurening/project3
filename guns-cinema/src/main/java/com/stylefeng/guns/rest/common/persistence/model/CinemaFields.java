package com.stylefeng.guns.rest.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CinemaFields extends Model<CinemaFields> {

    private static final long serialVersionUID = 2L;

    MtimeCinemaT2 cinemaInfo;

    List<MtimeHallFilmInfoT2> filmList;

    @TableId(value = "UUID", type = IdType.AUTO)
    private Integer uuid;

    @Override
    protected Serializable pkVal() {
        return this.uuid;
    }
}
