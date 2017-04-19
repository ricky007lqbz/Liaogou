package com.common.bean;

import com.common.Common;
import com.common.util.C_ArrayUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ricky on 2016/08/18.
 * <p/>
 * 最基本的数据bean
 */
public class C_BaseBean implements Serializable {

    //item的视图类型（用于recycleView)
    protected int _view_type;
    //item对应的位置（用于recycleView)
    protected int _position_type;


    public int get_view_type() {
        return _view_type;
    }

    /**
     * 数据绑定 对应不同itemUI类型标志
     *
     * @param _view_type
     */
    public void set_view_type(int _view_type) {
        this._view_type = _view_type;
    }


    public int get_position_type() {
        return _position_type;
    }

    /**
     * 卡片式：上中下 处理圆角。
     *
     * @param _position_type
     */
    public void set_position_type(int _position_type) {
        this._position_type = _position_type;
    }

    /**
     * 分组列表固定头部与子项的关联id;
     * 所属Group下的数据都有相同的 relativeHeaderId值，每个group只对应唯一的 relativeHeaderId值。
     * <p/>
     * 某一固定头部找不到关联的对应子项滑动后将不能显示。
     */
    protected int relativeHeaderId; /* 所属分组固定头部的key */

    public int getRelativeHeaderId() {
        return relativeHeaderId;
    }

    public void setRelativeHeaderId(int relativeHeaderId) {
        this.relativeHeaderId = relativeHeaderId;
    }

    public static <T extends C_BaseBean> void setPositionType(List<T> list) {
        if (!C_ArrayUtil.isEmpty(list)) {
            int size = list.size();
            if (size == 1) {
                list.get(0).set_position_type(Common.position_type.SINGLE);
            } else {
                for (T t : list) {
                    t.set_position_type(Common.position_type.CENTER);
                }
                list.get(0).set_position_type(Common.position_type.TOP);
                C_ArrayUtil.getLastItem(list).set_position_type(Common.position_type.BOTTOM);
            }
        }
    }
}
