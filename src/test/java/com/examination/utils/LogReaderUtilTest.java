package com.examination.utils;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.*;

@SpringBootTest
public class LogReaderUtilTest {

    @Test
    public void test() throws FileNotFoundException {
        File folder = new File("logs/");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                FileReader fileReader = new FileReader("logs/"+file.getName());
                System.out.println(file.getName());
            }
        }
    }


}