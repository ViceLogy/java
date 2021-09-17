package com.company;


import com.company.entity.Order;
import com.company.entity.Test;
import com.company.entity.User;
import com.company.repository.filerepository.FileOrderUserRepositoryImpl;
import com.company.repository.filerepository.FileUserServiceImpl;
import com.company.repository.postgrerepository.PostgreOrderUserRepositoryImpl;
import com.company.repository.postgrerepository.PostgreUserUserRepositoryImpl;
import com.company.resources.GenerateId;
import com.company.resources.Parser;

public class Main {

    public static void main(String[] args) {
        FileOrderUserRepositoryImpl repository = new FileOrderUserRepositoryImpl();
        User user = new User(1302, "go", "hf");
        Order order = new Order(1302,944559261,"dfgdsssssssfg",5555555);
        String line = "id = 1,name = jonson,lastName = sf";
        String lineOrd = "[id = 7,userId = 1,description = weq,price = 5000]";
        //System.out.println(lineOrd.substring(1,lineOrd.length()-1));
        FileUserServiceImpl repository1 = new FileUserServiceImpl();
        //System.out.println(line.substring(1,line.length()-1));
        //System.out.println(repository.update(order));
        //944559261
        System.out.println((int) GenerateId.generateID(User.class));
        //repository.delete(931323219);
        //System.out.println(repository.getOne(119512384));
        //System.out.println(repository.getAllOrderByUser(944559261));
        //repository.delete(6);
        //repository1.create(1l,order);
        //System.out.println(repository1.getAllOrderByUser(1l));
    }
}
