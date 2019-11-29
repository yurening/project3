package com.stylefeng.guns.rest.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 影厅电影信息表
 * </p>
 *
 * @author stylefeng
 * @since 2019-11-27
 */
@TableName("mtime_hall_film_info_t")
public class MtimeHallFilmInfoT2 extends Model<MtimeHallFilmInfoT2> {

    private static final long serialVersionUID = 2L;

    /**
     * 主键编号
     */
    @TableId(value = "UUID", type = IdType.AUTO)
    private Integer uuid;
    /**
     * 电影编号
     */
    @TableField("film_id")
    private Integer filmId;
    /**
     * 电影名称
     */
    @TableField("film_name")
    private String filmName;
    /**
     * 电影时长
     */
    @TableField("film_length")
    private String filmLength;
    /**
     * 电影类型
     */
    @TableField("film_cats")
    private String filmCats;
    /**
     * 电影语言
     */
    @TableField("film_language")
    private String filmType;
    /**
     * 演员列表
     */
    private String actors;
    /**
     * 图片地址
     */
    @TableField("img_address")
    private String imgAddress;

    private List<MtimeFieldT2> filmFields;

    public List<MtimeFieldT2> getFilmFields2() {
        return filmFields;
    }

    public void setFilmFields(List<MtimeFieldT2> filmFields2) {
        this.filmFields = filmFields2;
    }

    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getFilmLength() {
        return filmLength;
    }

    public void setFilmLength(String filmLength) {
        this.filmLength = filmLength;
    }

    public String getFilmCats() {
        return filmCats;
    }

    public void setFilmCats(String filmCats) {
        this.filmCats = filmCats;
    }

    public String getFilmType() {
        return filmType;
    }

    public void setFilmLanguage(String filmLanguage) {
        this.filmType = filmLanguage;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getImgAddress() {
        return imgAddress;
    }

    public void setImgAddress(String imgAddress) {
        this.imgAddress = imgAddress;
    }

    @Override
    protected Serializable pkVal() {
        return this.uuid;
    }

    @Override
    public String toString() {
        return "MtimeHallFilmInfoT{" +
        "uuid=" + uuid +
        ", filmId=" + filmId +
        ", filmName=" + filmName +
        ", filmLength=" + filmLength +
        ", filmCats=" + filmCats +
        ", filmLanguage=" + filmType +
        ", actors=" + actors +
        ", imgAddress=" + imgAddress +
        "}";
    }
}
