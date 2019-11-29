package com.stylefeng.guns.rest.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResVO<T> implements Serializable {

    private static final long serialVersionUID = -4851107103209624408L;

    T data;
    String msg;
    Integer status;
    String imgPre;
    Integer nowPage;
    Integer totalPage;


    public static <T> BaseResVO ok(T data){
        BaseResVO<T> baseResVO = new BaseResVO<>();
        baseResVO.setStatus(0);
        baseResVO.setData(data);
        return baseResVO;
    }

    public static BaseResVO fail(int errno,String msg){
        BaseResVO baseResVO = new BaseResVO();
        baseResVO.setStatus(errno);
        baseResVO.setMsg(msg);
        return baseResVO;
    }
}
