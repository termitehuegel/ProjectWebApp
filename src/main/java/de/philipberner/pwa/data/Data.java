package de.philipberner.pwa.data;

import java.util.Date;

/**
 * @author Philip Berner
 */
public class Data {
    private int id;             //id of the object - primary key
    private String name;        //name of the DataObject
    private int money;          //money in Euro
    private String comment;     //comment to the object
    private long date;          //last change date-Timestamp

    public Data(int id, String name, int money, String comment, long date) {
        this.id = id;
        this.name = name;
        this.money = money;
        this.comment = comment;
        this.date = date;
    }
    public Data(int id, String name, int money, String comment) {
        this.id = id;
        this.name = name;
        this.money = money;
        this.comment = comment;
        this.date = new Date().getTime();
    }

    public Data(int id, String name, int money) {
        this.id = id;
        this.name = name;
        this.money = money;
        this.comment = "";
        this.date = new Date().getTime();
    }

    public Data(Data datum) {
        this.id = datum.getId();
        this.name = datum.getName();
        this.money = datum.getMoney();
        this.comment = datum.getComment();
        this.date = datum.getDate();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        setDate(new Date().getTime());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setDate(new Date().getTime());
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
        setDate(new Date().getTime());
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
        setDate(new Date().getTime());
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", money=" + money +
                ", comment='" + comment + '\'' +
                ", comment='" + date + '\'' +
                '}';
    }
}
