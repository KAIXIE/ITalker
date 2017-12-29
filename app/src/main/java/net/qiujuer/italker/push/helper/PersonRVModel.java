package net.qiujuer.italker.push.helper;

/**
 * Created by Administrator on 2017/12/8.
 */

public class PersonRVModel {
    public PersonRVModel(String name, int imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }

    private String name;
    private int imagePath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImagePath() {
        return imagePath;
    }

    public void setImagePath(int imagePath) {
        this.imagePath = imagePath;
    }
}
