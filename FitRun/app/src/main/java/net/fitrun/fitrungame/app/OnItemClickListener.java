package net.fitrun.fitrungame.app;

/**
 * Created by Aspsine on 2015/7/22.
 * recyleview的点击事件
 */
public interface OnItemClickListener<T> {
    void onItemClick(int id, int position, T t);
    void onClick(int id);
}
