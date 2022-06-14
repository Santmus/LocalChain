package com.student_projetct.diplom_project;

import com.student_projetct.diplom_project.BlockChain.Block.Block;
import com.student_projetct.diplom_project.BlockChain.Block.iBlock;

import java.util.Date;

public class Class {

    public static void main(String[] args) {

        iBlock genesisBlock = new Block(new String[]{"""
        "status:" "initializaded_block",
        "code:" "01 - success",
        Programm start working
        """});
        iBlock firstBlock = new Block(new String[]{
                """
                "name:" "Yauheni"
                "surname:" "Kazachenka"
                """
        }, genesisBlock);
        iBlock secondBlock = new Block(new String[]{
                """
                        "name:" "Yauheni"
                        "surname:" "Kazachenka"
                        "login:" "santmusLive"
                        """}, firstBlock);
        iBlock thirdBlock = new Block(new String[]{"""
                        "name:" "Yauheni"
                        "surname:" "Kazachenka"
                        "login:" "santmusLive"
                        "password": "45698702Qw"
                        """}, secondBlock);
        iBlock fourthBlock = new Block(new String[]{
                """
                        "name:" "Yauheni"
                        "surname:" "Kazachenka"
                        "login:" "santmusLive"
                        "password": "45698702Qw"
                        status: user
                        """
        }, thirdBlock);
        System.out.println(genesisBlock + "\n" + firstBlock + "\n" + secondBlock + "\n" + thirdBlock + "\n" + fourthBlock);

    }
}
