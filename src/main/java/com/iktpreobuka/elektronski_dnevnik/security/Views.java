package com.iktpreobuka.elektronski_dnevnik.security;


import com.iktpreobuka.elektronski_dnevnik.security.Views.Public;

public class Views {
	public static class Public {}
    public static class Student extends Public {}
    public static class Parent extends Public {}
    public static class Teacher extends Public {}
    public static class Admin extends Public {}
}
