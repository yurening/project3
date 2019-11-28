package com.stylefeng.guns.rest.common.persistence.vo;

import com.stylefeng.guns.rest.common.persistence.model.MtimeActorT;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Actors implements Serializable {
    private static final long serialVersionUID = -7034559729691531528L;
    private MtimeActorT director;
    private List<MtimeActorT> actors;
}
