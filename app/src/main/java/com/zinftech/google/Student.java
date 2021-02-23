package com.zinftech.google;

import java.io.Serializable;

public class Student implements Serializable{
 String Name;
 int Age;
 String MobileNum;
 String College;
 String Type;

    public Student(String name, int age, String mobileNum, String college,String type) {
        this.Name = name;
        this.Age = age;
        this.MobileNum = mobileNum;
        this.College=college ;
        this.Type=type;

         //ArrayList<Student> students;
    }


    public Student(String name, int age, String mobileNum, String college) {
        this.Name = name;
        this.Age = age;
        this.MobileNum = mobileNum;
        this.College=college ;


        //ArrayList<Student> students;
    }

    public Student() {

    }

    /*public static ArrayList<String> getStudents() {
        return getStudents();
    }
*/
    public String getName()
    {
        return Name;
    }

    public int getAge()
    {
        return Age;
    }

    public String getMobileNum()
    {
        return MobileNum;
    }

    public String getCollege()
    {
        return College;
    }

    public String getType()
    {
        return Type;
    }


}
