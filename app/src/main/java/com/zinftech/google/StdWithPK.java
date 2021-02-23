package com.zinftech.google;

import java.io.Serializable;

public class StdWithPK implements Serializable{



    public String Pk;
    public Student Std;

    public StdWithPK()
    {

    }


    public StdWithPK(String pk,Student std)
    {
        this.Pk=pk;
        this.Std=std;
    }



    public void setPk(String pk) {
        this.Pk=pk;

    }

    public void setStd(Student std)
    {
        this.Std=std;
    }

}