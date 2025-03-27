package com.example.mynote;

public class item_class {
    public int id;
    public   String title;
    public  String msg;
    public  String time;

    public int getId(){
        return id;
    }

    public void setId(int ID){
        this.id = ID;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title_list) {
        this.title = title_list;
    }

    public void setMsg(String msg_list) {
        this.msg = msg_list;
    }

    public void setTime(String time_list) {
        this.time = time_list;
    }

    public String getMsg() {
        return msg;
    }

    public String getTime() {
        return time;
    }

    public item_class() {
    }


}
