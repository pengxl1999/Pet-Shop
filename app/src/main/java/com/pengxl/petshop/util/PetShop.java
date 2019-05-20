package com.pengxl.petshop.util;

import java.util.LinkedList;

public class PetShop {

    public static LinkedList<Pet> pets = new LinkedList<>();    //链表存储
    public static int petNumber;    //宠物数量

    //添加宠物
    public static void addPet(Pet pet) {
        pets.add(pet);
    }

    //删除宠物（+1重载方法）
    public static void removePet(Pet pet) {
        pets.remove(pet);
    }

    public static void removePet(int index) {
        pets.remove(index);
    }

    //寻找宠物
    public static Pet getPet(int index) {
        return pets.get(index);
    }
}
