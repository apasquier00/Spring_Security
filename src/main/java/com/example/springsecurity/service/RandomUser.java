package com.example.springsecurity.service;

public class RandomUser {
public String randomPassword() {
    StringBuilder name = new StringBuilder();
    for (int i = 0; i < randomInt(10); i++){
        name.append(convertIntToString(randomInt(25)));
    }
    return name.toString();
}

public String randomEmail() {
    return randomPassword() +
            "@" +
            randomPassword() +
            randomExtension();
}

public int randomInt(int i) {
    return (int) (1+(Math.random())*i);
}
public String convertIntToString(int i) {
    String[] alphabet = {"da","bi","ce","doo","er","fa","go","he","ib","je","ka","lo","mi","ne","or","pi","qa","ry","se","ta","bu","vy","wo","xy","yo","za"};
    return alphabet[i];
}
public String randomExtension(){
    String[] extension = {".fr", ".org", ".ru", ".en", ".com"};
    return extension[randomInt(4)];
}
}
