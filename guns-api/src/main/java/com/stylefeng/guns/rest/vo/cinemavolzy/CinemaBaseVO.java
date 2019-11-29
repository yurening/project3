package com.stylefeng.guns.rest.vo.cinemavolzy;

import lombok.Data;

import java.io.Serializable;

@Data
public class CinemaBaseVO<T> implements Serializable {

    private static final long serialVersionUID = -5811962248701501727L;

    Integer status;
    Integer nowPage;
    Integer totalPage;
    T data;
    String msg;

    public static CinemaBaseVO defaultOk(Object resp){
        CinemaBaseVO<Object> objectCinemaBaseVO = new CinemaBaseVO<>();
        objectCinemaBaseVO.setStatus(0);
        objectCinemaBaseVO.setNowPage(99);
        objectCinemaBaseVO.setTotalPage(99);
        objectCinemaBaseVO.setData(resp);
        objectCinemaBaseVO.setMsg(null);
        return objectCinemaBaseVO;
    }

    public static CinemaBaseVO pageOk(Object resp, Integer nowPage, Integer totalPage){
        CinemaBaseVO<Object> objectCinemaBaseVO = new CinemaBaseVO<>();
        objectCinemaBaseVO.setStatus(0);
        objectCinemaBaseVO.setNowPage(nowPage);
        objectCinemaBaseVO.setTotalPage(totalPage);
        objectCinemaBaseVO.setData(resp);
        objectCinemaBaseVO.setMsg(null);
        return objectCinemaBaseVO;
    }

    public static CinemaBaseVO queryFailed(){
        CinemaBaseVO<Object> objectCinemaBaseVO = new CinemaBaseVO<>();
        objectCinemaBaseVO.setStatus(1);
        objectCinemaBaseVO.setNowPage(null);
        objectCinemaBaseVO.setTotalPage(null);
        objectCinemaBaseVO.setData(null);
        objectCinemaBaseVO.setMsg("影院信息查询失败！");
        return objectCinemaBaseVO;
    }

}
