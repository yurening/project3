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


    /*public static BaseResVO ok(Object data){
        BaseResVO BaseResVO = new BaseResVO();
        BaseResVO.setErrmsg("成功");
        BaseResVO.setData(data);
        BaseResVO.setErrno(0);
        return BaseResVO;
    }

    public static BaseResVO fail(int errno,String errmsg){
        BaseResVO BaseResVO = new BaseResVO();
        BaseResVO.setErrno(errno);
        BaseResVO.setErrmsg(errmsg);
        return BaseResVO;
    }*/
}
