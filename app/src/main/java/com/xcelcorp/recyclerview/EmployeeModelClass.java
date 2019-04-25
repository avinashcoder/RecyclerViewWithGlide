package com.xcelcorp.recyclerview;

public class EmployeeModelClass {
    private String profilePicUrl,name,id,age,salary;

    public EmployeeModelClass(String profilePicUrl, String name, String id, String age, String salary) {
        this.profilePicUrl = profilePicUrl;
        this.name = name;
        this.id = id;
        this.age = age;
        this.salary = salary;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getAge() {
        return age;
    }

    public String getSalary() {
        return salary;
    }
}
